public class Factorial {
    public static int getFactorial(int n){
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else 
            return n*getFactorial(n-1);
    }
}
//Given any positive integer n, n * (n-1) * (n-1) * ... * (n-x) * ... until n-x <= 1
//Once n-x = 1, end the chain and start multiplying back up.
//If n happens to be 0, return 0