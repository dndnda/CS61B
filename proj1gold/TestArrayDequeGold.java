import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudentArray() {

        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> right = new ArrayDequeSolution<>();
        String message = "";
        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad1.addFirst(i);
                right.addFirst(i);
                message += "addFirst(" + i + ")\n";
            } else if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                right.addLast(i);
                message += "addLast(" + i + ")\n";
            } else if (numberBetweenZeroAndOne < 0.75) {
                if (sad1.size() <= 1) {
                    sad1.addFirst(i);
                    right.addFirst(i);
                    message += "addFirst(" + i + ")\n";
                } else {
                    Integer actual = sad1.removeFirst();
                    Integer expected = right.removeFirst();
                    message += "removeFirst()\n";
                    assertEquals(message, expected, actual);
                }
            } else {
                if (sad1.size() <= 1) {
                    sad1.addLast(i);
                    right.addLast(i);
                    message += "addLast(" + i + ")\n";
                } else {
                    Integer actual = sad1.removeLast();
                    Integer expected = right.removeLast();
                    message += "removeLast()\n";
                    assertEquals(message, expected, actual);
                }
            }
        }
    }
}
