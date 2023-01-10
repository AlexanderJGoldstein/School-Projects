import java.util.Scanner;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
public class App {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> counter = new HashMap<String, Integer>();
        File newfile = new File("C:\\Users\\AceJG\\OneDrive\\Documents\\GitHub\\School-Projects\\FileReading\\src\\App.java");
        Scanner scan = new Scanner(newfile);
        while(scan.hasNext()){
            System.out.println(scan.next());
        } 
        Set<String> set = counter.keySet();
    }
}
