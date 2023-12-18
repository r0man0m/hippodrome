import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    @Test
    public void nullException_Test(){
    Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
        new Hippodrome(null);
    });
    assertEquals("Horses cannot be null.", exception.getMessage());
    }
    @Test
    public void isEmpty_List_Test(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            new Hippodrome(new ArrayList<>());
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    public static Stream<List<Horse>>getListHorse(){
        Random random = new Random();
        double speed = 2.0;
        double distance = 10.0;
        String name = "Horse ";
        ArrayList<Horse>list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            name = name + i;
            speed  += (double) random.nextInt(1, 10)/10;
            distance  += (double) random.nextInt(1, 10)/10;
            list.add(new Horse(name, speed, distance));
            speed = 2.0;
            distance = 10.0;
            name = "Horse ";
        }
        return Stream.of(list);
    }

    @ParameterizedTest
    @MethodSource("getListHorse")
    public void test_List_Of_Horses(List<Horse>list){
        Hippodrome hippodrome = new Hippodrome(list);
        assertNotNull(hippodrome.getHorses());
        assertEquals(30, hippodrome.getHorses().size());
        assertEquals("Horse 1", hippodrome.getHorses().get(0).getName());
        assertEquals("Horse 10", hippodrome.getHorses().get(9).getName());
        assertEquals("Horse 30", hippodrome.getHorses().get(29).getName());

    }
    @Test
    public void test_move_in_Horse(){
        List<Horse>list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();

        for (Horse H: list){
            Mockito.verify(H, Mockito.times(1)).move();
        }

    }
    @Test
    public void test_Get_Winner(){
        List<Horse>list = new ArrayList<>();
        list.add(new Horse("Loser", 1.0, 2.0));
        list.add(new Horse("Winner", 1.2, 2.2));
        Hippodrome hippodrome = new Hippodrome(list);
        assertEquals("Winner", hippodrome.getWinner().getName());
    }
}
