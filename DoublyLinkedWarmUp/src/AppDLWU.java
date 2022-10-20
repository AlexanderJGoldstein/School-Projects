public class AppDLWU {
    public static void main(String[] args){
        DoublyLinkedList list = new DoublyLinkedList();
        for(int i = 1; i <= 20; i++){
            list.append(i);
        }
        System.out.println(list);
        list.reverse();
        System.out.println(list);
    }

}
