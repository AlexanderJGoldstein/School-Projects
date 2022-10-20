//This Edition is able to successfully parse the input file

import java.io.*;
import org.apache.commons.io.*;
import java.util.*;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.lang.NumberFormatException;

public class Parse {
    static String filePath = "";
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(getFilePath());
        String data = IOUtils.toString(fis, "UTF-8");
        String[] instruction = parseVM(data);
        for (String string : instruction) {
            System.out.println(string);
        }
    }

    public static String[] parseVM(String data){
        List<String> out = new LinkedList<String>();
        while(data.contains("\n")){
            String temp = data.substring(0, data.indexOf("\n")-1);
            if(temp.contains("//"))
                temp = temp.substring(0, temp.indexOf("//"));
            out.add(new String(temp));
            data = data.substring(data.indexOf("\n")+1);
        }
        List<String> copy = new LinkedList<String>();
        for(String item : out){
            if(!item.isBlank())
                copy.add(item);
        }
        String[] ArrayOut = new String[copy.size()];
        for (int i = 0; i < copy.size(); i++){
            ArrayOut[i] = copy.get(i);
        }

        return ArrayOut;
    }

    public static String getFilePath(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the file path for the VM file you would like to convert: ");
        filePath = sc.nextLine();
        sc.close();
        return filePath;
    }
}