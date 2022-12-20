//Written by Alex Goldstein

import java.io.*;
import org.apache.commons.io.IOUtils;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
public class FirstPass {
    static String filePath = "";
    public static void main(String[] args) throws Exception {
        //Start file reading
        String data;
        FileInputStream fis = new FileInputStream(getFilePath());
        data = IOUtils.toString(fis, "UTF-8");
        writeToFile(parseASM(data));
    }

    //Gets the file path from the user's input
    public static String getFilePath(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the file path for the asm file you would like to convert: ");
        filePath = sc.nextLine();
        sc.close();
        return filePath;
    }

    //Separates each line of the ASM file and puts it all into a list, so each instruction is an individual item
    public static String[] parseASM(String data) {
        List<String> out = new LinkedList<String>();
        while(data.contains("\n")){
            String temp = data.substring(0, data.indexOf("\n"));
            if(temp.contains("//"))
                temp = temp.substring(0, temp.indexOf("//"));
            out.add(new String(temp));
            data = data.substring(data.indexOf("\n")+1);
        }
        out.add(data);
        List<String> copy = new LinkedList<String>();
        for(String item : out){
            if(!item.isBlank())
                copy.add(item);
        }
        String[] ArrayOut = new String[copy.size()];
        for (int i = 0; i < copy.size(); i++){
            //There ends up being an extra whitespace bit at the end of each line, this exists to remove it
            if(copy.get(i).charAt(copy.get(i).length()-1) == 13)
                ArrayOut[i] = copy.get(i).substring(0, copy.get(i).length()-1);
            else
                ArrayOut[i] = copy.get(i);
        }

        return ArrayOut;
    }

        public static void writeToFile(String[] line){
        try {
            FileWriter writer = new FileWriter(filePath.substring(0, filePath.length()-3) + "txt");
            String output = "";
            for(int x = 0; x < line.length; x++){
                output += line[x];
                output += "\n";
            }
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            System.out.println("The output file exists but is a directory, doesn't exist but can't be created, or cannot be opened for any other reason.");
            e.printStackTrace();
        }
    }
}
