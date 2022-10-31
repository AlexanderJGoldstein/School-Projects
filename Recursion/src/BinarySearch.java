import java.util.*;
public class BinarySearch {
    public static int search(String string, char element){
        int i = string.length()/2;
        if(string.isEmpty())
            throw new NoSuchElementException();
        else if(string.charAt(i) == element)
            return i;
        else{
            if(string.charAt(i) > element)
                return search(string.substring(0, i), element);
            else
                return search(string.substring(i), element)+string.length()/2;
        }
    }
}
