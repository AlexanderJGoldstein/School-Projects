import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Doubly-linked list class (UNFINISHED).
 * 
 * @author ??
 *
 */
public class DoubleList<E> implements Iterable<E> {

    private Link<E> head; // Pointer to list header
    private Link<E> tail; // Pointer to last node
    private int listSize; // Size of list

    /**
     * Create an empty LList.
     */
    DoubleList() {
        clear();
    }

    /**
     * Remove all elements.
     */
    public void clear() {
        tail = new Link<E>(null, null); // Create trailer
        head = new Link<E>(null, tail); // Create header
        tail.setPrev(head);
        listSize = 0;
    }

    /**
     * Append item to the end of the list.
     */
    public void append(E item) {
        Link<E> newLink = new Link<>(item, tail.prev(), tail);
        tail.prev().setNext(newLink);
        tail.setPrev(newLink);
        listSize++;
    }

    /**
     * Return the element at the provided index. This method will iterate from the
     * head or the tail depending on which will require fewer steps.
     */
    public E get(int pos) {
        if (pos < 0 || pos >= listSize) {
            throw new IndexOutOfBoundsException();
        }

        if (pos < listSize / 2) {
            return forward(pos).element();
        } else {
            return backward(pos).element();
        }
    }

    /**
     * Helper method for iterating forward from the head.
     */
    private Link<E> forward(int pos) {
        Link<E> current = head.next();
        for (int i = 0; i < pos; i++) {
            current = current.next();
        }
        return current;
    }

    /**
     * Helper method for iterating backward from the tail.
     */
    private Link<E> backward(int pos) {
        Link<E> current = tail.prev();
        for (int i = 0; i < (listSize - 1) - pos; i++) {
            current = current.prev();
        }
        return current;
    }

    /**
     * Return the number of elements stored in the list.
     */
    public int size() {
        return listSize;
    }

    /**
     * Iterates forward through the list. Remove operation is supported.
     */
    @Override
    public Iterator<E> iterator() {
        return new DoubleIterator();
    }

    private class DoubleIterator implements Iterator<E> {
        Link<E> current = head.next();
        boolean nextCalled = false;
        @Override
        public boolean hasNext() {
            if(current.next() == null)
                return false;
            else 
                return true;
        }

        @Override
        public E next() {
            if(hasNext() == false) throw new NoSuchElementException();
            else {
                Link<E> prev = current;
                current = current.next();
                nextCalled = true;
                return prev.element();
            }
        }

        @Override
        public void remove() {
            if(nextCalled == true){
                
            } else
                throw new IllegalStateException();
        }

    }

    /**
     * Add the item at the specified index.
     */
    public void add(int index, E item) {
        if(index > listSize){
            throw new IndexOutOfBoundsException();
        }
        Link<E> insert = new Link<E>(item, null, null);
        if(index == 0)
            head = insert;
        if(index == listSize){
            tail = insert;
        }
        else {
            Link<E> current = head;
            for(int i = 0; i < index; i++){
                current = current.next();
            }
            if(current != null)
                current.setPrev(insert);
                insert.setNext(current);
            if(current.prev() != null)
                current.prev().setNext(insert);
                insert.setPrev(current.prev());
        }
    }

    /**
     * Remove and return the item at the specified index.
     */
    public E remove(int index) {
        if(index >= listSize)
            throw new IndexOutOfBoundsException();
        if(head == null)
            throw new NoSuchElementException();
        Iterator<E> linkAtIndex = iterator();
        E returnVal = null;
        for(int i = 0; i < index-1; i++)
            returnVal = linkAtIndex.next();
        linkAtIndex.remove();
        return returnVal;
    }

    /**
     * Reverse the list (in place)
     */
    public void reverse() {

    }

}
