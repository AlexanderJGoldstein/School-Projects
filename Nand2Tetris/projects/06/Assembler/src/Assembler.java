//This program takes in a file path from the user that points to a .asm file, and then converts this file into a .hack file, which will be saved in the same directory with the same file prefix

import java.io.*;
import org.apache.commons.io.IOUtils;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.Hashtable;
public class Assembler {
    static String filePath = "";

    //Initialize Hashtable for variables and constant aliases for registers
    static Hashtable<String, Integer> addressTable = new Hashtable<String, Integer>() {{
        for(int i = 0; i < 16; i++){
            String key = "R" + i; 
            put(key, i);
        } 
        put("SP", 0);
        put("LCL", 1);
        put("ARG", 2);
        put("THIS", 3);
        put("THAT", 4);
        put("SCREEN", 16384);
        put("KBD", 24576);
    }};

    public static void main(String[] args) throws Exception {
        //Start file reading
        String data;
        FileInputStream fis = new FileInputStream(getFilePath());
        data = IOUtils.toString(fis, "UTF-8");
        writeOutBinary(data);
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

    //Computes the arguments from the file and calls the appropriate parser
    public static void writeOutBinary(String inputFile) {
        String[] arr = parseASM(inputFile);
        int programCount = 0;
        int regNo = 16;
        int[][] Instruction = new int[arr.length][16];
        for(int x = 0; x < arr.length; x++) {
            int spaceCount = 0;
            while(arr[x].charAt(spaceCount) == ' ') spaceCount++;
            if((Arrays.asList("0", "1", "-", "A", "D", "M", "!", "@").contains(arr[x].substring(spaceCount, spaceCount+1)))) programCount++;
            if(arr[x].charAt(spaceCount) == '('){
                //This will run before anything else is compiled. It should add all labels to the global hashtable, which are assigned with their corresponding programCount, which is their address
                String name = arr[x].substring(spaceCount+1, arr[x].length()-1);
                addressTable.put(name, programCount);
            }
        }
        programCount = 0;
        for(int i = 0; i < arr.length; i++) {
            int spaceCount = 0;
            while(arr[i].charAt(spaceCount) == ' ') spaceCount++;
            if(arr[i].charAt(spaceCount) == '@'){
                String address = new String(arr[i].substring(arr[i].indexOf("@") + 1));
                while(address.contains(" ")){
                    address = address.substring(0, address.indexOf(" ")) + address.substring(address.indexOf(" ")+1);
                }
                try {
                    int value = Integer.parseInt(address);
                    Instruction[programCount] = parseAddress(value);
                } catch (NumberFormatException e){
                    try {
                        Instruction[programCount] = parseAddress((addressTable.get(address)));
                    } catch (java.lang.NullPointerException e2) {
                        addressTable.put(address, regNo);
                        regNo++;
                        Instruction[programCount] = parseAddress((addressTable.get(address)));
                    }
                }
                programCount++;
            } else if(Arrays.asList("0", "1", "-", "A", "D", "M", "!").contains(arr[i].substring(spaceCount, spaceCount+1))) {
                Instruction[programCount] = parseInstruction(arr[i].substring(spaceCount));
                programCount++;
            } 
        }
        writeToFile(Instruction, programCount);
    }

    //Parses an A-Instruction
    public static int[] parseAddress(int address){
        int[] out = new int[16];
        out[0] = 0;
        for(int x = 15; x > 0; x--) {
           out[x] = address%2;
           address /= 2;
        }
        return out;
    }

    //Parses a C-Instruction
    public static int[] parseInstruction(String input){

        //This loop is placed here to remove white spaces from the instruction
        while(input.contains(" ")){
            input = input.substring(0, input.indexOf(" ")) + input.substring(input.indexOf(" ") + 1);
        }

        //Configures the ixx bits, these are the same for any C-Instruction
        int[] out = new int[16];
        out[0] = 1;
        out[1] = 1;
        out[2] = 1;
        for(int i = 3; i < 16; i++) out[i] = 0;

        //Configure instructions for where to save results
        if(input.contains("=")){
            String mems = input.substring(0, input.indexOf("="));
            if(mems.contains("A")) out[10] = 1;
            if(mems.contains("D")) out[11] = 1;
            if(mems.contains("M")) out[12] = 1;
            input = input.substring(input.indexOf("=")+1);
        }

        //Configure instructions for a jump
        if(input.contains(";")){
            String jump = input.substring(input.indexOf(";") + 1);
            switch (jump) {
                case "JGT":
                out[15] = 1;
                break;
    
                case "JEQ":
                out[14] = 1;
                break;
    
                case "JGE":
                out[14] = 1;
                out[15] = 1;
                break;
    
                case "JLT":
                out[13] = 1;
                break;
    
                case "JNE":
                out[13] = 1;
                out[15] = 1;
                break;
    
                case "JLE":
                out[13] = 1;
                out[14] = 1;
                break;
    
                case "JMP":
                out[13] = 1;
                out[14] = 1;
                out[15] = 1;
                break;
    
                default:
                break;
            }
            input = input.substring(0, input.indexOf(";"));
        } 

        //Configure instructions for comp
        if(input.contains("M")) {
            out[3] = 1;
        }
        switch (input) {
            case "0":
            out[4] = 1;
            out[6] = 1;
            out[8] = 1;
            break;

            case "1":
            out[4] = 1;
            out[5] = 1;
            out[6] = 1;
            out[7] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "-1":
            out[4] = 1;
            out[5] = 1;
            out[6] = 1;
            out[8] = 1;
            break;

            case "D":
            out[6] = 1;
            out[7] = 1;
            break;

            case "A":
            case "M":
            out[4] = 1;
            out[5] = 1;
            break;

            case "!D":
            out[6] = 1;
            out[7] = 1;
            out[9] = 1;
            break;

            case "!A":
            case "!M":
            out[4] = 1;
            out[5] = 1;
            out[9] = 1;
            break;

            case "-D":
            out[6] = 1;
            out[7] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "-A":
            case "-M":
            out[4] = 1;
            out[5] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "D+1":
            out[5] = 1;
            out[6] = 1;
            out[7] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "A+1":
            case "M+1":
            out[4] = 1;
            out[5] = 1;
            out[7] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "D-1":
            out[6] = 1;
            out[7] = 1;
            out[8] = 1;
            break;

            case "A-1":
            case "M-1":
            out[4] = 1;
            out[5] = 1;
            out[8] = 1;
            break;

            case "D+A":
            case "D+M":
            case "A+D":
            case "M+D":
            out[8] = 1;
            break;

            case "D-A":
            case "D-M":
            out[5] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "A-D":
            case "M-D":
            out[7] = 1;
            out[8] = 1;
            out[9] = 1;
            break;

            case "D&A":
            case "D&M":
            case "A&D":
            case "M&D":
            break;

            case "D|A":
            case "D|M":
            case "M|D":
            case "A|D":
            out[5] = 1;
            out[7] = 1;
            out[9] = 1;
            break;

            default:
            break;
        }

        return out;
    }

    //Writes the resulting binary to a .hack file with the same file prefix
    public static void writeToFile(int[][] Instruction, int len){
        try {
            FileWriter writer = new FileWriter(filePath.substring(0, filePath.length()-3) + "hack");
            String output = "";
            for(int x = 0; x < len; x++){
                for(int y = 0; y < Instruction[x].length; y++){
                    output += Integer.toString(Instruction[x][y]);
                }
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
