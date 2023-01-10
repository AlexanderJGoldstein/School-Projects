import java.util.Hashtable;
public class AnagramTester {
    public static void main(String[] args) throws Exception {
        System.out.println(runTest("Hello", "olleH"));
    }

    public static boolean runTest(String str1, String str2){
        int lowerAsciiBound = 33;
        int upperAsciiBound = 126;
        Hashtable<Character, Integer> str1CharCount = new Hashtable<Character, Integer>(){{
            for(int i = lowerAsciiBound; i <= 122; i++)
                put((char) i, 0);
        }};
        for(int i = 0; i < str1.length(); i++){
            if(lowerAsciiBound <= (int) str1.charAt(i) && (int) str1.charAt(i) <= upperAsciiBound)
                str1CharCount.replace(str1.charAt(i), str1CharCount.get(str1.charAt(i)) + 1);
        }   
        Hashtable<Character, Integer> str2CharCount = new Hashtable<Character, Integer>(){{
            for(int i = lowerAsciiBound; i <= 122; i++)
                put((char) i, 0);
        }};
        for(int i = 0; i < str2.length(); i++){
            if(lowerAsciiBound <= (int) str2.charAt(i) && (int) str2.charAt(i) <= upperAsciiBound)
                str2CharCount.replace(str2.charAt(i), str2CharCount.get(str2.charAt(i)) + 1);
        }
        for(int i = lowerAsciiBound; i <= upperAsciiBound; i++){
            if(str1CharCount.get((char) i) != str2CharCount.get((char) i))
                return false;
        }
        return true;
    }
}
