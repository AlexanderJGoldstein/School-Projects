public class DoublyLinkedList {
    public DoublyLinkedListNode head;
    public DoublyLinkedListNode tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    //Appends a node to the tail of the list
    public void append(int val) {
        DoublyLinkedListNode node = new DoublyLinkedListNode(val);
        if(this.head == null) this.head = node;
        else {
            this.tail.next = node;
            node.prev = this.tail;
        }
        this.tail = node;
    }

    //Adds a node to the beginning of the list
    public void precede(int val){
        DoublyLinkedListNode node = new DoublyLinkedListNode(val);
        if(this.tail == null) this.tail = node;
        else {
            this.head.prev = node;
            node.next = this.head;
        }
        this.head = node;
    }

    //reverses the entire list
    public void reverse() {
        DoublyLinkedListNode current, behind, forward;
        current = head;
        tail = head;

        while(current != null) {
            forward = current.next;
            behind = current.prev;
            current.next = behind;
            current.prev = forward;
            head = current;
            current = forward;
        }
    }

    public String toString(){
        if(head == null) return "[]";
        String out = "[" + String.valueOf(head.data);
        DoublyLinkedListNode current = head.next;
        while(current != null){
            out = out + ", " + String.valueOf(current.data);
            current = current.next;
        }
        out = out + "]";
        return out;
    }
}
