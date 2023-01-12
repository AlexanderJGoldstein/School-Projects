//Written by Alex Goldstein

import java.io.FileInputStream;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class FirstPass {
    static String filePath = "";

    public static void main(String[] args) throws Exception {
        // Start file reading
        String data = "";
        FileInputStream fis = new FileInputStream(getFilePath());
        byte[] bytes = fis.readAllBytes();
        for(byte a : bytes){
            data += (char) a;
        }
        fis.close();
        System.out.println(data);
        writeToFile(parseASM(data));
    }

    // Gets the file path from the user's input
    public static String getFilePath() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the file path for the asm file you would like to convert: ");
        filePath = sc.nextLine();
        sc.close();
        return filePath;
    }

    // Separates each line of the ASM file and puts it all into a list, so each
    // instruction is an individual item
    public static String[] parseASM(String data) {
        List<String> out = new LinkedList<String>();
        while (data.contains("\n")) {
            String temp = data.substring(0, data.indexOf("\n")-1);
            if (temp.contains("//"))
                temp = temp.substring(0, temp.indexOf("//"));
            if (!temp.isBlank())
                out.add(new String(temp));
            data = data.substring(data.indexOf("\n") + 1);
        }
        if (!data.isBlank())
            out.add(data);
        String[] ArrayOut = new String[out.size()];
        out.toArray(ArrayOut);
        return ArrayOut;
    }

    public static void writeToFile(String[] line) {
        try {
            FileWriter writer = new FileWriter(filePath.substring(0, filePath.length() - 3) + "txt");
            String output = "";
            for (int x = 0; x < line.length; x++) {
                output += line[x];
                output += "\n";
            }
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            System.out.println(
                    "The output file exists but is a directory, doesn't exist but can't be created, or cannot be opened for any other reason.");
            e.printStackTrace();
        }
    }
}
