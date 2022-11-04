public class Fibonacci {
    public static int getNthElement(int n){
        if(n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else
            return getNthElement(n-1) + getNthElement(n-2);
    }
}
//The fibonacci sequence follows the simple rule of f(n) = f(n-1) + f(n-2), with n being the nth term in the sequence
//there is the exception of f(0) being 0, and f(1) being 1,
//Base cases: n == 0, n == 1
//Recursive cases: n != 0 && n != 1