import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Tokenizer {
    private static String filePath;
    private static String outputFile;
    private static final List<String> keywords = Arrays.asList("class", "int", "var", "let", "true", "method", "boolean", "static", "do", "false", "function", "char", "field", "if", "null", "constructor", "void", "else", "this", "while", "return");
    private static final List<String> symbols = Arrays.asList("{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-", "*", "/", "&", "|", "<", ">", "=", "~");

    public static void main(String[] args) throws Exception {
        StringBuilder data = read();
        StringBuilder output = new StringBuilder("<tokens>\n");

        while(data.toString().contains("//")){
            data.delete(data.indexOf("//"), data.indexOf("\n", data.indexOf("//"))-1);
        }

        while(data.toString().contains("/*"))
            data.delete(data.indexOf("/*"), data.indexOf("*/", data.indexOf("/*"))+2);

        while(data.indexOf("\n") != -1){
            int returnIndex = data.indexOf("\n");
            data.replace(returnIndex, returnIndex+1, " ");
            if(data.charAt(returnIndex-1) == 13) data.deleteCharAt(returnIndex-1);
        }

        while(data.indexOf("\t") != -1){
            int tabIndex = data.indexOf("\t");
            data.deleteCharAt(tabIndex);
        }

        while(data.indexOf("  ") != -1){
            int doubleSpaceIndex = data.indexOf("  ");
            data.replace(doubleSpaceIndex, doubleSpaceIndex+2, " ");
        }

        while(data.charAt(0) == ' ')
            data.deleteCharAt(0);
        
        List<StringBuilder> tokenList = new ArrayList<StringBuilder>();
        tokenList.add(new StringBuilder());
        //Symbols should end the last StringBuilder, start their own StringBuilder, immediately end it, and then start the next
        //Make sure there is a conditional that adds the symbol to the first StringBuilder if i = 0.
        //Make sure there is a conditional that doesn't start a new StringBuilder if the last character is a symbol
        //Make sure there is a conditional that keeps everything that is part of a stringConstant in one string
        //stringConstants are essentially multicharacter symbols

        for (int i = 0;  i < data.toString().length(); i++){
            if(symbols.contains(data.substring(i, i+1))){
                tokenList.add(new StringBuilder());
                tokenList.get(tokenList.size() - 1).append(data.charAt(i));
                tokenList.add(new StringBuilder());
            } else 

            if(data.charAt(i) == '\"'){
                int endStringConstantIndex = data.indexOf("\"", i+1);
                tokenList.add(new StringBuilder());
                tokenList.get(tokenList.size() - 1).append("\"" + data.substring(i+1, endStringConstantIndex));
                tokenList.add(new StringBuilder());
                i = endStringConstantIndex;
            } else 

            if(data.charAt(i) != ' '){
                tokenList.get(tokenList.size()-1).append(data.charAt(i));
            } else 

            {
                tokenList.add(new StringBuilder());
            }
        }

        int x = 0;
        while(x < tokenList.size()){
            if(tokenList.get(x).isEmpty())
                tokenList.remove(x);
            else
                x++;
        }

        //At this point TokenList is a list of each token. stringConstants start with a double quote

        for (StringBuilder e : tokenList) {
            if(symbols.contains(e.toString())){
                output.append("<symbol> " + e + " </symbol>\n");
            } else if (keywords.contains(e.toString())){
                output.append("<keyword> " + e + " </keyword>\n");
            } else if (e.charAt(0) == '"') {
                output.append("<stringConstant> " + e.substring(1) + " </stringConstant>\n");
            } else try {
                output.append("<integerConstant> " + Integer.parseInt(e.toString()) + " </integerConstant>\n");
            } catch (NumberFormatException exc) {
                output.append("<identifier> " + e + " </identifier>\n");
            }
        }
        
        output.append("</tokens>");
        write(output.toString());
    }

    public static StringBuilder read() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        FileFilter ff = new FileFilter() {
            public boolean accept(File f) {
                String extension = getExtension(f);
                if (extension != null)
                    switch (extension) {
                        case "jack":
                            return true;
                        default:
                            return false;
                    }
                return f.isDirectory();
            }

            private String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                return ext;
            }

            public String getDescription() {
                return null;
            }
        };
        fileChooser.setFileFilter(ff);
        fileChooser.showDialog(null, "Select");
        File file = fileChooser.getSelectedFile();
        filePath = file.getAbsolutePath();
        FileInputStream fis = new FileInputStream(file);
        StringBuilder builder = new StringBuilder();
        for (byte c : fis.readAllBytes())
            builder.append((char) c);
        fis.close();
        outputFile = filePath.substring(0, filePath.length() - 5) + "TTest.xml";
        return builder;
    }

    public static void write(String content) throws Exception {
        FileWriter writer = new FileWriter(outputFile);
        System.out.println("File written to: " + outputFile);
        writer.write(content);
        writer.close();
    }

    // We have four types, keyword, symbol, identifier, & constant
}