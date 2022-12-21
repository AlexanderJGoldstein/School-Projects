/*
 * THIS VERSION SHOULD DO NOTHING BUT TRANSLATE THE COMMANDS
 * MEMORY MAPPINGS WILL BE IMPLEMENTED IN THE FULL VERSION IN PROJECT 8
 */

import java.io.*;
import org.apache.commons.io.*;
import java.util.*;


import java.io.FileWriter;

public class VMTranslator {
    static String filePath = "";
    static String newFileName;
    static FileWriter writer;
    static int NumCompare = 1;
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(getFilePath());
        createFile();
        String data = IOUtils.toString(fis, "UTF-8");
        String[] instruction = parseVM(data);
        String out = "";
        for (String string : instruction) {
            parseLine(string);
        }
        writeOut(out);
        writer.close();
    }
    public static String[] parseVM(String data){
        List<String> out = new LinkedList<String>();
        while(data.contains("\n")){
            String temp = data.substring(0, data.indexOf("\n")-1);
            if(temp.contains("//"))
                temp = temp.substring(0, temp.indexOf("//"));
            if(!temp.isBlank())
                out.add(new String(temp));
            data = data.substring(data.indexOf("\n")+1);
        }
        String[] ArrayOut = new String[out.size()];
        out.toArray(ArrayOut);
        return ArrayOut;
    }

    public static String getFilePath(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the file path for the VM file you would like to convert: ");
        filePath = sc.nextLine();
        sc.close();
        return filePath;
    }

    public static void parseLine(String in){
        List<String> args = new LinkedList<String>();
        while(in.contains(" ")){
            args.add(in.substring(0, in.indexOf(" ")));
            in = in.substring(in.indexOf(" ")+1);
        }
        args.add(in);
        System.out.println(args);
        String outputInstruction = "";
        boolean moveF = true;
        switch (args.size()){
            case 1:
                outputInstruction += "@SP\nM=M-1\nA=M\n";
                String inst = new String(args.get(0));
                switch (inst){
                    case "add":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nM=D+M";
                        break;
                    
                    case "sub":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nM=M-D";
                        break;

                    case "and":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nM=D&M";
                        break;
                    
                    case "or":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nM=D|M";
                        break;

                    case "not":
                        outputInstruction += "M=!M";
                        break;

                    case "neg":
                        outputInstruction += "M=-M";
                        break;

                    case "lt":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JLT\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")";
                        NumCompare++;
                        break;

                    case "gt":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JGT\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")";
                        NumCompare++;
                        break;

                    case "eq":
                        outputInstruction += "D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JEQ\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")";
                        NumCompare++;
                        break;

                    default:
                        return;
                }
                break;
            
            case 2:
                break;
                
            case 3:
                switch (args.get(0)){
                    case "pop":
                        moveF = false;
                        outputInstruction += "@SP\nM=M-1\nA=M\nD=M\n";
                        outputInstruction += location(args.get(1), args.get(2));
                        outputInstruction += "M=D";
                        break;
                    
                    case "push":
                        outputInstruction += location(args.get(1), args.get(2));
                        if(args.get(1).contains("constant") )
                            outputInstruction += "D=A\n";
                        else
                            outputInstruction += "D=M\n";
                        outputInstruction += "@SP\nA=M\nM=D";
                        break;
                    
                    default:
                        return;
                }
                break;
            
            default: 
                return;
            }
        outputInstruction += "\n@SP";
        if(moveF)
            outputInstruction += "\nM=M+1";
        outputInstruction += "\nA=M\n\n";
        writeOut(outputInstruction);
    }

    public static String location(String arg1, String arg2){
        String out = "";
        switch(arg1){
            case "local":
                out += "@LCL\nA=M\n";
                for(int i = 0; i < Integer.parseInt(arg2); i++)
                    out += "A=A+1\n";
                break;
            
            case "constant":
                out += "@" + Integer.parseInt(arg2) + "\n";
                break;

            case "pointer":
                out += "@THIS\n";
                for(int i = 0; i < Integer.parseInt(arg2); i++)
                    out += "A=A+1\n";
                break;

            case "this":
                out += "@THIS\nA=M\n";
                for(int i = 0; i < Integer.parseInt(arg2); i++)
                    out += "A=A+1\n";
                break;

            case "that":
                out += "@THAT\nA=M\n";
                for(int i = 0; i < Integer.parseInt(arg2); i++)
                    out += "A=A+1\n";
                break;

            case "static":
                out += "@" + (16 + Integer.parseInt(arg2)) + "\n";
                break;

            case "argument":
                out += "@ARG\nA=M\n";
                for(int i = 0; i < Integer.parseInt(arg2); i++)
                    out += "A=A+1\n";
                break;

            case "temp" :
                switch(Integer.parseInt(arg2)){
                    case 0:
                        out += "@R5";
                        break;
                    case 1:
                        out += "@R6";
                        break;
                    case 2:
                        out += "@R7";
                        break;
                    case 3:
                        out += "@R8";
                        break;
                    case 4:
                        out += "@R9";
                        break;
                    case 5:
                        out += "@R10";
                        break;
                    case 6:
                        out += "@R11";
                        break;
                    case 7:
                        out += "@R12";
                        break;
                }
                out += "\n";
                break;

            default:
                return null;
        }
        return out;
    }

    public static void createFile(){
        try{
            newFileName = filePath.substring(0, filePath.length()-2) + "asm";
            writer = new FileWriter(newFileName);
            writer.write("");
        } catch (IOException e) {
            System.out.println("The output file exists but is a directory, doesn't exist but can't be created, or cannot be opened for any other reason.");
            e.printStackTrace();
        }
    }

    public static void writeOut(String text){
        try{
            writer.write(text);
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }
}


