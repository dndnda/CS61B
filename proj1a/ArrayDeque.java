import java.net.SocketOption;

public class ArrayDeque<T>{

    private T[] item;
    private int nextFirst;
    private int preLast;
    private int size;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 0;
    }
    public ArrayDeque(T val) {
        item[1] = val;
        nextFirst = 0;
        preLast = 2;

        size++;
    }

    /** Resize the array when nextFirst + 1 == preLast. */
    private void resize() {
        T[] a = (T[]) new Object[item.length * 2];
        System.arraycopy(item,nextFirst+1, a, 1, item.length - 1 - nextFirst);
        System.arraycopy(item, 0, a, item.length - nextFirst + 1, preLast);
        nextFirst = 0;
        preLast = size + 1;
        item = a;
    }

    /** Judge if need resize. */
    private boolean needResize() {
        if(nextFirst + 1 == preLast) {
            return true;
        }
        return false;
    }

    /** Inserts X into the first of the list. */
    public void addFirst(T x) {
        if(needResize()) {
            resize();
        }
        item[nextFirst] = x;
        if(nextFirst == 0) {
            nextFirst = item.length;
        }
        else {
            nextFirst -= 1;
        }
        size--;
    }

    /** Inserts X into the back of the list. */
    public void addLast(T x) {
        if(needResize()) {
            resize();
        }
        item[preLast] = x;
        if(preLast == item.length - 1){
            preLast = 0;
        }
        else {
            preLast++;
        }
        size++;
    }

    /** Returns the item from the back of the list. */
    public T getLast() {
        if(preLast == 0) {
            return item[item.length - 1];
        }
        else {
            return item[preLast - 1];
        }
    }
    /** Gets the ith item in the list (0 is the front). */
    public T get(int i) {
        int index = nextFirst + i + 1;
        if(index >= item.length) {
            index = index - item.length;
        }
        return item[index];

    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public T removeLast() {
        size--;
        T temp;
        if(preLast == 0) {
            temp = item[item.length];
            item[item.length] = null;
            preLast = item.length;
        }
        else{
            temp = item[preLast - 1];
            item[preLast - 1] = null;
            preLast -= 1;
        }
        return temp;
    }

    /** Return if the list is empty */
    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        int temp = nextFirst;
        int size_ = size();
        if(size_ == 0) {
            System.out.print("");
        }
        else {
            while(size_ > 0) {
                if(temp == item.length - 1) {
                    System.out.print(item[0] + " ");
                    temp = 0;
                }
                else {
                    System.out.print(item[nextFirst + 1] + " ");
                    temp += 1;
                }
                size_ --;
            }
        }

    }
}
