import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Iterator;
import java.lang.Math;

public class AppTest{

    public static void main(String[] args){

    }
    
    @Test
    public void reverseTest(){
        DoubleList<Character> originList = new DoubleList<Character>();
        DoubleList<Character> reverseList = new DoubleList<Character>();
        for(int i = 0; i < 26; i++){
            originList.append((char)(i+65));
            reverseList.append((char)(90-i));
        }
        originList.reverse();
        compare(originList, reverseList);
    }

    @Test
    public void testRemoveArbitraryLocation(){
        int randomPos = (int) (Math.random()*26);
        DoubleList<Character> userList = new DoubleList<Character>();
        DoubleList<Character> compareList = new DoubleList<Character>();
        for(int i = 0; i < 26; i++){
            userList.append((char) (i+65));
            if(i != randomPos){
                compareList.append((char) (i+65));
            }
        }
        userList.remove(randomPos);
        compare(userList, compareList);
    }

    @Test
    public void testAddArbitraryLocation(){

    }

    @Test
    public void testAllLettersLoop(){

    }

    @Test
    public void testAddAllLetters(){

    }

    @Test
    public void testEmptySizeZero(){

    }

    @Test
    public void testAppendAfterIteratorRemove(){

    }

    @Test
    public void testNextAfterEnd(){

    }

    @Test
    public void testRemoveWithoutNextThrowsException(){

    }

    @Test
    public void testRemoveLessThanM(){

    }

    @Test
    public void testIteratorRemoveAll(){

    }

    @Test
    public void testAddAllLettersForEach(){

    }

    private void compare(DoubleList<Character> UserList, DoubleList<Character> CompareList){
        Iterator<Character> originIter = UserList.iterator();
        Iterator<Character> compareIter = CompareList.iterator();
        while(originIter.hasNext()){
            assertEquals(originIter.next(), compareIter.next());
        }
    }
}
