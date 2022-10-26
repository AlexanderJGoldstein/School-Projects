import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Simple test driver for the DoubleList class.
 * 
 * @author Nathan Sprague and Michael S. Kirkpatrick
 * @version 1.1, 1/2019
 *
 */
public class DoubleListTest {

  private String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public <T> DoubleList<T> makeDoubleList() {
    return new DoubleList<T>();
  }

  public DoubleList<String> makeLetterList() {
    DoubleList<String> list = makeDoubleList();
    for (int i = 0; i < letters.length(); i++) {
      list.append(letters.substring(i, i + 1));
    }
    return list;
  }


  // --------------------------------------------
  // ITERATOR TESTS
  // --------------------------------------------

  @Test
  public void testAddAllLettersForEach() {
    DoubleList<String> list = makeLetterList();
    int i = 0;
    for (String s : list) {
      assertEquals(letters.substring(i, i + 1), s);
      i++;
    }
    assertEquals(i, letters.length());
  }

  @Test
  public void testIteratorRemoveAll() {
    DoubleList<String> list = makeLetterList();
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      it.next();
      it.remove();
    }
    assertEquals(0, list.size());
  }

  @Test
  public void testIteratorRemoveLessThanM() {
    DoubleList<String> list = makeLetterList();
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      if (it.next().compareTo("M") < 0)
        it.remove();
    }
    assertEquals(14, list.size());
  }

  @Test
  public void testRemoveWithoutNextThrowsException() {
    DoubleList<String> list = makeLetterList();
    Iterator<String> it = list.iterator();
    assertThrows(IllegalStateException.class, () -> it.remove());
  }

  @Test
  public void testNextAfterEnd() {
    DoubleList<String> list = makeLetterList();
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      it.next();
    }
    assertThrows(NoSuchElementException.class, () -> it.next());
  }

  @Test
  public void testAppendAfterIteratorRemove() {
    DoubleList<String> list = makeLetterList();
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      if (it.next().compareTo("M") < 0)
        it.remove();
    }
    assertEquals(14, list.size());
    list.append("?");
    assertEquals(15, list.size());
    int i = 12;
    for (String s : list) {
      if (i < letters.length()) {
        assertEquals(letters.substring(i, i + 1), s);
        i++;
      } else {
        assertEquals("?", s);
      }
    }
    assertEquals(i, letters.length());
  }



  // --------------------------------------------
  // TESTS FOR SIZE
  // --------------------------------------------
  @Test
  public void testEmptySizeZero() {
    DoubleList<String> list = makeDoubleList();
    assertEquals(0, list.size());
  }

  @Test
  public void testAddAllLetters() {
    DoubleList<String> list = makeLetterList();
    assertEquals(26, list.size());
  }


  // --------------------------------------------
  // TESTS FOR GET
  // --------------------------------------------
  @Test
  public void testAllLettersLoop() {
    DoubleList<String> list = makeLetterList();
    for (int i = 0; i < list.size(); i++) {
      assertEquals(letters.substring(i, i + 1), list.get(i));
    }
  }


  // --------------------------------------------
  // TESTS FOR ADD
  // --------------------------------------------

  @Test
  public void testAddArbitraryLocation() {
    DoubleList<String> list = makeLetterList();
    list.add(0, "A");
    list.add(list.size(), "A");
    list.add(list.size() / 2, "A");
    int i = 0;
    for (String s : list) {
      if (i == 0 || i == 14 || i == 28)
        assertEquals("A", s);
      else if (i < 14)
        assertEquals(letters.substring(i - 1, i), s);
      else
        assertEquals(letters.substring(i - 2, i - 1), s);
      i++;
    }

  }

  // --------------------------------------------
  // TESTS FOR REMOVE
  // --------------------------------------------

  @Test
  public void testRemoveArbitraryLocation() {
    DoubleList<String> list = makeLetterList();
    list.remove(0);
    list.remove(list.size() / 2);
    list.remove(list.size() - 1);
    int i = 0;
    for (String s : list) {
      if (i == 0 || i == 13)
        i++;
      assertEquals(letters.substring(i, i + 1), s);
      i++;
    }
    assertEquals(i + 1, letters.length());
  }

  // --------------------------------------------
  // TESTS FOR REVERSE
  // --------------------------------------------

  @Test
  public void testReverse() {
    DoubleList<String> list = makeLetterList();
    list.reverse();
    int i = letters.length() - 1;
    for (String s : list) {
      assertEquals(letters.substring(i, i + 1), s);
      i--;
    }
  }


}
