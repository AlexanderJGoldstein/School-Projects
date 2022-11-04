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
//Binary search algorithm for a character in a sorted string
//Start in the middle of the string, if the string is empty, the element can't be in the string, so throw an exception
//If the string is not empty, check if the character in the center of the string is equivalent to the character we are looking for.
//If it is, return the index to the calling function
//If not, check if the character value is greater than the element being searched for
//If it is, we know that the element must be in the first half of the given string, so we start another binary search in that substring
//Otherwise, the element is in the latter half of the string, so we perform a binary search on the latter half of the string, adding half the length of the current string to the index to correct for the fact that this is the second half of the string, and not the first.

//Base case: character at index string.length()/2 == element
//Recursive case: character at index string.length()/2 != element