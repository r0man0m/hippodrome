import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void exceptionTest(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            new Horse(null, 2.3, 3.0);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    static Stream<String> argsProviderFactory(){
        List<String>blanckList = new ArrayList<>();
        for (int i = 0; i <= 32; i++) {
            String s = "" + (char)i;
            if(s.isBlank()){
                blanckList.add(s);
            }
        }
        return blanckList.stream();
    }
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void isBlankTest(String argument){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            new Horse(argument, 2.3, 3.0);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());

    }
    @ParameterizedTest
    @ValueSource(doubles = {-1.0})
    public void isLessZeroSpeedTest( double speed){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            new Horse("Bucefalus", speed, 3.0);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());

    }
    @ParameterizedTest
    @ValueSource(doubles = {-100.0})
    public void isLessZeroDistanceTest(double distance){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            new Horse("Bucefalus", 2.3, distance);
        });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }
    @Test
    public void getName(){
        assertEquals("Bucefalus", new Horse("Bucefalus", 2.3, 3.0).getName());
    }

    @Test
    public void getSpeed(){
        assertEquals(2.3, new Horse("Bucefalus", 2.3, 3.0).getSpeed());
    }
    @Test
    public void getDistance(){
        assertEquals(100.0, new Horse("Bucefalus", 2.3, 100.0).getDistance());
    }

    @Test
    public void move_Calls_GetRandom(){
        try(MockedStatic<Horse>mockedStaticHorse = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("Bucefalus", 3.2, 100.0);
            horse.move();
            mockedStaticHorse.verify(()-> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9})
    public void calc_Distance(double value){
        double min = 0.2;
        double max = 0.9;
        double speed = 2.3;
        double distance = 100.0;
        String name  = "Bucefalus";
        double expectedDistance = distance + speed * value;
        Horse horse = new Horse(name, speed, distance);
        try(MockedStatic<Horse>horseMockedStatic = Mockito.mockStatic(Horse.class)){
            horseMockedStatic.when(()->Horse.getRandomDouble(min, max)).thenReturn(value);
            horse.move();
        }
        assertEquals(expectedDistance, horse.getDistance());
    }

}
