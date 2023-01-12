//This takes in VM code from a file designated by the user, and outputs its interpreted hack code in the same path
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;

public class VMTranslator {
    static String filePath = "";
    static String newFileName;
    static FileWriter writer;
    static int NumCompare = 1;
    static List<String> functions = new LinkedList<String>();
    static List<String> instruction = new LinkedList<String>();
    static String fileDirectory;
    static int returnIter = 0;
    static boolean sysMainReturn = true;
    static String currentFunction = "";
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(getFilePath());
        createFile();
        StringBuilder dataBuilder = new StringBuilder();
        String data = "";
        for(byte e : fis.readAllBytes())
            dataBuilder.append((char) e);
        data = dataBuilder.toString();
        //Creates a list of loaded files
        List<String> files = new LinkedList<String>();
        String origFileName = new String(filePath);
        while (origFileName.contains("\\"))
            origFileName = origFileName.substring(origFileName.indexOf("\\")+1);
        fileDirectory = filePath.substring(0, filePath.indexOf(origFileName));
        files.add(origFileName.substring(0, origFileName.indexOf(".")));

        //Test if Sys.init has a return command
        if(data.contains("\nfunction")){
            String test = new String(data.substring(data.indexOf("\nfunction")+10));
            if(test.contains("\nfunction")){
                test = test.substring(0, test.indexOf("\nfunction"));
            }
            if(!test.contains("\nreturn"))
                sysMainReturn = false;
        }


        //Load calls
        String temp = new String(data);
        while(temp.contains("\ncall")){
            temp = temp.substring(temp.indexOf("\ncall") + 6);
            String depName = temp.substring(0, temp.indexOf("."));
            if(!files.contains(depName)){
                fis = new FileInputStream(fileDirectory + depName + ".vm");
                dataBuilder.append("\n");
                for(byte e : fis.readAllBytes())
                    dataBuilder.append((char) e);
                data = dataBuilder.toString();
                files.add(depName);
            }
        }
        
        instruction.addAll(parseVM(data));
        writeOut("@256\nD=A\n@SP\nM=D\n");
        if(data.contains("function Sys.init 0"))
            writeOut(parseLine("call Sys.init 0"));
        for (String string : instruction) {
            writeOut(parseLine(string));
        }
        for (String string : functions) {
            writeOut(string);
        }

        writer.close();
    }

    public static List<String> parseVM(String data){
        List<String> out = new LinkedList<String>();
        while(data.contains("\n")){
            int index = data.indexOf("\n")-1;
            if(index < 0)
                index = 0;
            String temp = data.substring(0, index);
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

        return copy;
    }

    public static String getFilePath(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the file path for the VM file you would like to convert: ");
        filePath = sc.nextLine();
        sc.close();
        return filePath;
    }

    public static String parseLine(String in){
        List<String> args = new LinkedList<String>();
        while(in.contains(" ")){
            String temp = in.substring(0, in.indexOf(" "));
            if(temp.contains("\t"))
                temp = temp.substring(0, temp.indexOf("\t"));
            if(temp != "")
                args.add(temp);
            in = in.substring(in.indexOf(" ")+1);
        }
        if(in.contains("\t"))
                in = in.substring(0, in.indexOf("\t"));
        if(in != "")
            args.add(in);
        StringBuilder outputInstruction = new StringBuilder();
        boolean moveF = true;
        switch (args.size()){
            case 1:
                switch (args.get(0)){
                    case "add":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=D+M");
                        break;
                    
                    case "sub":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=M-D");
                        break;

                    case "and":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=D&M");
                        break;
                    
                    case "or":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nM=D|M");
                        break;

                    case "not":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("M=!M");
                        break;

                    case "neg":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("M=-M");
                        break;

                    case "lt":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JLT\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")");
                        NumCompare++;
                        break;

                    case "gt":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JGT\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")");
                        NumCompare++;
                        break;

                    case "eq":
                        outputInstruction.append("@SP\nM=M-1\nA=M\n");
                        outputInstruction.append("D=M\n@SP\nM=M-1\nA=M\nD=M-D\nM=-1\n@COMP" + NumCompare + "\nD;JEQ\n@SP\nA=M\nM=0\n(COMP" + NumCompare + ")");
                        NumCompare++;
                        break;

                    case "return":
                    //FRAME = LCL
                    outputInstruction.append("@LCL\nD=M\n@R13\nM=D");
                    //RET = *(FRAME-5)
                    outputInstruction.append("\n@5\nD=A\n@R13\nA=M-D\nD=M\n@R14\nM=D");
                    //*ARG = pop()
                    outputInstruction.append("\n@SP\nM=M-1\nA=M\nD=M\n@ARG\nA=M\nM=D");
                    //SP = ARG+1
                    outputInstruction.append("\n@ARG\nD=M+1\n@SP\nM=D");
                    //THAT = *(FRAME-1)
                    outputInstruction.append("\n@1\nD=A\n@R13\nA=M-D\nD=M\n@THAT\nM=D");
                    //THIS = *(FRAME-2)
                    outputInstruction.append("\n@2\nD=A\n@R13\nA=M-D\nD=M\n@THIS\nM=D");
                    //ARG = *(FRAME-3)
                    outputInstruction.append("\n@3\nD=A\n@R13\nA=M-D\nD=M\n@ARG\nM=D");
                    //LCL = *(FRAME-4)
                    outputInstruction.append("\n@4\nD=A\n@R13\nA=M-D\nD=M\n@LCL\nM=D");
                    //goto RET
                    if(sysMainReturn){
                        sysMainReturn = false;
                        outputInstruction.append("\n(END_PRGM)\n@END_PRGM\n0;JMP");
                    } else
                        outputInstruction.append("\n@R14\nA=M\n0;JMP");
                    currentFunction = "";
                    moveF = false;
                    break;

                    default:
                        return null;
                }
                break;
            
            case 2:
                switch (args.get(0)){
                    case "label":
                        outputInstruction.append("(" + args.get(1) + ")");
                        moveF = false;
                        break;

                    case "goto":
                        outputInstruction.append("@" + args.get(1) + "\n0;JMP");
                        break;
                    
                    case "if-goto":
                        //False in VM is 0
                        outputInstruction.append("@SP\nM=M-1\nA=M\nD=M\n@" + args.get(1) + "\nD;JNE");
                        moveF = false;
                        break;

    
                    default:
                        return null;
                }
                break;
                
            case 3:
                switch (args.get(0)){
                    case "pop":
                        moveF = false;
                        outputInstruction.append(location(args.get(1), args.get(2)));
                        outputInstruction.append("D=A\n@SP\nA=M\nM=D\n");
                        outputInstruction.append("@SP\nM=M-1\nA=M\nD=M\nA=A+1\nA=M\n");
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
                    
                    case "function":
                        //(f)
                        outputInstruction.append("(" + args.get(1) + ")\n@SP\nA=M\nM=0");
                        
                        //repeat n times {push constant 0}
                        for(int i = 0; i < Integer.parseInt(args.get(2)); i++)
                            outputInstruction.append("\n@SP\nM=M+1\nA=M\nM=0");
                        
                        moveF = false;
                        currentFunction = args.get(1).substring(0, args.get(1).indexOf("."));
                        break;

                    case "call":
                        //push return-address
                        outputInstruction.append("@RET" + returnIter + "\nD=A\n@SP\nA=M\nM=D");
                        //push LCL
                        outputInstruction.append("\n@LCL\nD=M\n@SP\nM=M+1\nA=M\nM=D");
                        //push ARG
                        outputInstruction.append("\n@ARG\nD=M\n@SP\nM=M+1\nA=M\nM=D");
                        //push THIS
                        outputInstruction.append("\n@THIS\nD=M\n@SP\nM=M+1\nA=M\nM=D");
                        //push THAT
                        outputInstruction.append("\n@THAT\nD=M\n@SP\nM=M+1\nA=M\nM=D");
                        //ARG = SP-n-5
                        outputInstruction.append("\n@SP\nM=M+1\nD=M\n@5\nD=D-A\n@" + Integer.parseInt(args.get(2)) + "\nD=D-A\n@ARG\nM=D");
                        //LCL = SP
                        outputInstruction.append("\n@SP\nD=M\n@LCL\nM=D");
                        //goto f
                        outputInstruction.append("\n@" + args.get(1) + "\n0;JMP");
                        //(return-address)
                        outputInstruction.append("\n(RET" + returnIter + ")");
                        returnIter++;
                        moveF = false;
                        break;

                    default:
                        return null;
                }
                break;
            
            default: 
                return null;
        }
        outputInstruction.append("\n@SP");
        if(moveF)
            outputInstruction.append("\nM=M+1");
        outputInstruction.append("\nA=M\n\n");
        return outputInstruction.toString();
    }

    public static String location(String arg1, String arg2){
        StringBuilder out = new StringBuilder();
        switch(arg1){
            case "local":
                out.append("@" + Integer.parseInt(arg2) + "\nD=A\n");
                out.append("@LCL\nA=M\nA=A+D");
                break;
            
            case "constant":
                out.append("@" + Integer.parseInt(arg2));
                break;

            case "pointer":
                out.append("@" + Integer.parseInt(arg2) + "\nD=A\n");
                out.append("@THIS\nA=A+D");
                break;

            case "this":
                out.append("@" + Integer.parseInt(arg2) + "\nD=A\n");
                out.append("@THIS\nA=M\nA=A+D");
                break;

            case "that":
                out.append("@" + Integer.parseInt(arg2) + "\nD=A\n");
                out.append("@THAT\nA=M\nA=A+D");
                break;

            case "static":
                out.append("@" + currentFunction + "_static" + arg2 + ".j");
                break;

            case "argument":
                out.append("@" + Integer.parseInt(arg2) + "\nD=A\n");
                out.append("@ARG\nA=M\nA=A+D");
                break;

            case "temp" :
                switch(Integer.parseInt(arg2)){
                    case 0:
                        out.append("@R5");
                        break;
                    case 1:
                        out.append("@R6");
                        break;
                    case 2:
                        out.append("@R7");
                        break;
                    case 3:
                        out.append("@R8");
                        break;
                    case 4:
                        out.append("@R9");
                        break;
                    case 5:
                        out.append("@R10");
                        break;
                    case 6:
                        out.append("@R11");
                        break;
                    case 7:
                        out.append("@R12");
                        break;
                }
                break;

            default:
                return null;
        }
        
        out.append("\n");
        return out.toString();
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
        } catch (NullPointerException e) {
            return;
        }

    }
}