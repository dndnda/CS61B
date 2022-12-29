import java.util.TreeMap;

public class LinkedListDeque<T> {

    /** Node class for Linked list. */
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T val, Node prev, Node next) {
            item = val;
            this.next = next;
            this.prev = prev;
        }

        public Node(Node prev, Node next) {
            this.prev = prev;
            this.next = next;
        }
    }

    private Node first;
    private Node last;
    private int size;

    /** Create an empty linked list deque. */
    public LinkedListDeque() {
        first = new Node( null, null);
        last = new Node( first, null);
        first.next = last;
        size = 0;
    }

    public void addFirst(T item) {
        Node temp = first.next;
        first.next = new Node(item, first, first.next);
        temp.prev = first.next;

        size ++;
    }

    public void addLast(T item) {
        Node temp = last.prev;
        last.prev = new Node(item, temp, last);
        temp.next = last.prev;

        size ++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node temp = first.next;
        while(temp.next != last) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return
     */
    public T removeFirst() {
        if(size == 0) {
            return null;
        }

        size --;

        Node temp = first.next;
        first.next = first.next.next;
        return temp.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * @return
     */
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        size --;
        Node temp = last.prev;
        last.prev = last.prev.prev;
        return temp.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return
     */
    public T get(int index) {
        if(index >= size) {
            return null;
        }
        Node temp = first.next;
        while(index > 0) {
            temp = temp.next;
            index --;
        }
        return temp.item;
    }

    public T getRecursiveHelper(int index, Node start) {

        if(index == 0) {
            return start.item;
        }
        return getRecursiveHelper(index - 1, start.next);
    }

    public T getRecursive(int index) {

        if(index >= size) {
            return null;
        }

        return getRecursiveHelper(index, first.next);
    }


}
