import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {

    @Test 
    public void binarySearchTest(){
        String sampleList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int i = (int) (java.lang.Math.random() * 26);
        assertEquals(BinarySearch.search(sampleList, sampleList.charAt(i)), (char) i);
    }

    @Test
    public void fibonacciTest(){
        assertEquals(144, Fibonacci.getNthElement(12));
    }

    @Test
    public void factorialTest(){
        assertEquals(120, Factorial.getFactorial(5));
    }
}
