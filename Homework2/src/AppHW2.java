public class AppHW2 {
    public static void main(String[] args){
        LN node1 = new LN(1, new LN(3, new LN(5, null)));
        LN node2 = new LN(2, new LN(4, new LN(6, null)));
        System.out.println(node1);
        System.out.println(node2);
        System.out.println(merge(node1, node2));
    }

    public static int sum(LN l){
        if(l == null)
            return 0;
        else
            return l.value + sum(l.next);
    }

    public static LN getNodeWithValue(LN l, int value){
        if(l == null || l.value == value)
            return l;
        else
            return getNodeWithValue(l.next, value);
    }

    public static LN append(LN l, int value){
        if(l == null)
            l = new LN(value, null);
        else 
            l.next = append(l.next, value);
        return l;
    }

    public static LN merge(LN A, LN B) {
        if(A == null) return B;
        if(B == null) return A;

        if(A.value < B.value) {
            A.next = merge(A.next, B);
            return A;
        } else {
            B.next = merge(A, B.next);
            return B;
        }
    }
}
