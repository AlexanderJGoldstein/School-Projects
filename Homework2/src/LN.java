public class LN {
    public LN next;
    public int value;
    
    public LN(int value, LN next){
        this.value = value;
        this.next = next;
    }

    public String toString(){
        if(this.next == null){
            return Integer.toString(this.value);
        } else {
            return this.value + ":" + this.next;
        }
    }
}
