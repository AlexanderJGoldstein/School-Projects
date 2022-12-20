import java.lang.Math;
public class App {
    private static double logOf2 = Math.log(2);
    public static void main(String[] args) {
        System.out.println(calcRows(0));
    }

    public static int calcRows(int listSize){
        int rows = 1 + (int) Math.ceil(Math.log(listSize)/logOf2);
        if(rows <= 0) rows = 0;
        return rows;
    }
}
