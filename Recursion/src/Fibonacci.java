public class Fibonacci {
    public static int getNthElement(int n){
        if(n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else
            return getNthElement(n-1) + getNthElement(n-2);
    }

    public static void main(String[] args){
        System.out.println(getNthElement(5));
    }
}
