import java.io.FileWriter;
import java.io.FileInputStream;
import java.util.Hashtable;

public class Tokenizer{
    public static void main(String[] args) {
        
    }

    public static String read(String fileName)throws Exception{
        FileInputStream fis = new FileInputStream(fileName);
        StringBuilder builder = new StringBuilder();
        for(byte c : fis.readAllBytes())
            builder.append((char) c);
        fis.close();
        return builder.toString();
    }

    public static void write(String fileName, String content) throws Exception{   
        FileWriter writer = new FileWriter(fileName);
        writer.write(content);
        writer.close();
    }

    //We have four types, keyword, symbol, identifier, & constant
}