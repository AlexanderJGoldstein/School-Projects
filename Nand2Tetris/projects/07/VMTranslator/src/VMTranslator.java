/*
 * THIS VERSION SHOULD DO NOTHING BUT TRANSLATE THE COMMANDS
 * MEMORY MAPPINGS WILL BE IMPLEMENTED IN THE FULL VERSION IN PROJECT 8
 */

import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


import java.io.FileWriter;

public class VMTranslator {
    static String filePath = "";
    static String newFileName;
    static FileWriter writer;
    static int NumCompare = 1;
    public static void main(String[] args) throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        FileFilter ff = new FileFilter() {
            public boolean accept(File f){
                String extension = getExtension(f);
                if(extension != null && extension.equals("asm"))
                    return true;
                return f.isDirectory();
            }

            public String getDescription(){
                return null;
            }
        };
        fileChooser.setFileFilter(ff);
        fileChooser.showDialog(null, "Select");

        FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
        createFile();
        String data;
        StringBuilder dataBuilder = new StringBuilder();
        for(byte b : fis.readAllBytes())
            dataBuilder.append((char) b);
        data = dataBuilder.toString();
        String[] instruction = parseVM(data);
        String out = "";
        for (String string : instruction) {
            parseLine(string);
        }
        writeOut(out);
        writer.close();
    }
    
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
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

    public static void parseLine(String in){
        List<String> args = new LinkedList<String>();
        while(in.contains(" ")){
            args.add(in.substring(0, in.indexOf(" ")));
            in = in.substring(in.indexOf(" ")+1);
        }
        args.add(in);
        System.out.println(args);
        StringBuilder outputInstruction = new StringBuilder();
        boolean moveF = true;
        switch (args.size()){
            case 1:
                outputInstruction.append("@SP\nM=M-1\nA=M\n");
                String inst = new String(args.get(0));
                switch (inst){
                    case "add":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=D+M");
                        break;
                    
                    case "sub":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=M-D");
                        break;

                    case "and":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=D&M");
                        break;
                    
                    case "or":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=D|M");
                        break;

                    case "not":
                        outputInstruction.append("M=!M");
                        break;

                    case "neg":
                        outputInstruction.append("M=-M");
                        break;

                    case "lt":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JLT\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")");
                        NumCompare++;
                        break;

                    case "gt":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JGT\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")");
                        NumCompare++;
                        break;

                    case "eq":
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JEQ\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")");
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
                        outputInstruction.append("@SP\nM=M-1\nA=M\nD=M\n");
                        outputInstruction.append(location(args.get(1), args.get(2)));
                        outputInstruction.append("M=D");
                        break;
                    
                    case "push":
                        outputInstruction.append(location(args.get(1), args.get(2)));
                        if(args.get(1).contains("constant") )
                            outputInstruction.append("D=A\n");
                        else
                            outputInstruction.append("D=M\n");
                        outputInstruction.append("@SP\nA=M\nM=D");
                        break;
                    
                    default:
                        return;
                }
                break;
            
            default: 
                return;
            }
        outputInstruction.append("\n@SP");
        if(moveF)
            outputInstruction.append("\nM=M+1");
        outputInstruction.append("\nA=M\n\n");
        writeOut(outputInstruction.toString());
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
            writer.close();
        } catch (IOException e) {
            System.out.println("The output file exists but is a directory, doesn't exist but can't be created, or cannot be opened for any other reason.");
            e.printStackTrace();
        }
    }

    public static void writeOut(String text){
        try{
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }
}


