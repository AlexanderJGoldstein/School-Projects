import static org.junit.Assert.assertEquals;
import java.util.Iterator;

import org.junit.Test;

public class AppTest{
    @Test
    public void reverseTest(){
        DoubleList<Character> originList = new DoubleList<Character>();
        DoubleList<Character> reverseList = new DoubleList<Character>();
        for(int i = 0; i < 26; i++){
            originList.append((char)(i+65));
            reverseList.append((char)(90-i));
        }
        originList.reverse();
        Iterator<Character> originIter = originList.iterator();
        Iterator<Character> reverseIter = reverseList.iterator();
        while(originIter.hasNext()){
            assertEquals(originIter.next(), reverseIter.next());
        }
    }
}
