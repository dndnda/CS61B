//public class ArrayDeque<T>{
//
//    private T[] item;
//    private int nextFirst;
//    private int preLast;
//    private int size;
//
//    public ArrayDeque() {
//        item = (T[]) new Object[8];
//        size = 0;
//    }
//    public ArrayDeque(T val) {
//        item[1] = val;
//        nextFirst = 0;
//        preLast = 2;
//
//        size++;
//    }
//
//    /** Resize the array when nextFirst + 1 == preLast. */
//    private void resize() {
//        T[] a = (T[]) new Object[item.length * 2];
//        System.arraycopy(item,nextFirst+1, a, 1, item.length - 1 - nextFirst);
//        System.arraycopy(item, 0, a, item.length - nextFirst + 1, preLast);
//        nextFirst = 0;
//        preLast = size + 1;
//        item = a;
//    }
//
//    /** Judge if need resize. */
//    private boolean needResize() {
//        if(nextFirst + 1 == preLast) {
//            return true;
//        }
//        return false;
//    }
//
//    /** Inserts X into the first of the list. */
//    public void addFirst(T x) {
//        if(needResize()) {
//            resize();
//        }
//        item[nextFirst] = x;
//        if(nextFirst == 0) {
//            nextFirst = item.length;
//        }
//        else {
//            nextFirst -= 1;
//        }
//        size--;
//    }
//
//    /** Inserts X into the back of the list. */
//    public void addLast(T x) {
//        if(needResize()) {
//            resize();
//        }
//        item[preLast] = x;
//        if(preLast == item.length - 1){
//            preLast = 0;
//        }
//        else {
//            preLast++;
//        }
//        size++;
//    }
//
//    /** Returns the item from the back of the list. */
//    public T getLast() {
//        if(preLast == 0) {
//            return item[item.length - 1];
//        }
//        else {
//            return item[preLast - 1];
//        }
//    }
//    /** Gets the ith item in the list (0 is the front). */
//    public T get(int i) {
//        int index = nextFirst + i + 1;
//        if(index >= item.length) {
//            index = index - item.length;
//        }
//        return item[index];
//
//    }
//
//    /** Returns the number of items in the list. */
//    public int size() {
//        return size;
//    }
//
//    /** Deletes item from back of the list and
//     * returns deleted item. */
//    public T removeLast() {
//        size--;
//        T temp;
//        if(preLast == 0) {
//            temp = item[item.length];
//            item[item.length] = null;
//            preLast = item.length;
//        }
//        else{
//            temp = item[preLast - 1];
//            item[preLast - 1] = null;
//            preLast -= 1;
//        }
//        return temp;
//    }
//
//    /** Return if the list is empty */
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    public void printDeque() {
//        int temp = nextFirst;
//        int size_ = size();
//        if(size_ == 0) {
//            System.out.print("");
//        }
//        else {
//            while(size_ > 0) {
//                if(temp == item.length - 1) {
//                    System.out.print(item[0] + " ");
//                    temp = 0;
//                }
//                else {
//                    System.out.print(item[nextFirst + 1] + " ");
//                    temp += 1;
//                }
//                size_ --;
//            }
//        }
//
//    }
//}
/** second part of project1A.
 * deque implemented by array
 * @author FlyingPig
 */
public class ArrayDeque<T> {

    /** array to save data.*/
    private T[] array;
    /** size of the deque. */
    private int size;

    /** size of the array. */
    private int length;

    /** front index. */
    private int front;

    /** last index. */
    private int last;

    /** constructor for ArrayDeque. */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        length = 8;
        front = 4;
        last = 4;
    }

    /** decide if the deque is empty.
     * @return true if the deque is empty, vice versa.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return the size of the deque. */
    public int size() {
        return size;
    }

    /** return the "index - 1".
     * @param index index
     */
    private int minusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }

    /** return the "index + 1".
     * @param index index
     */
    private int plusOne(int index, int module) {
        index %= module;
        if (index == module - 1) {
            return 0;
        }
        return index + 1;
    }

    private void grow() {
        T[] newArray = (T[]) new Object[length * 2];
        int ptr1 = front;
        int ptr2 = length;
        while (ptr1 != last) {
            newArray[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length * 2);
        }
        front = length;
        last = ptr2;
        array = newArray;
        length *= 2;
    }

    private void shrink() {
        T[] newArray = (T[]) new Object[length / 2];
        int ptr1 = front;
        int ptr2 = length / 4;
        while (ptr1 != last) {
            newArray[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length / 2);
        }
        front = length / 4;
        last = ptr2;
        array = newArray;
        length /= 2;
    }

    /** add one item at the front of the deque.
     * @param item the item we want to add
     */
    public void addFirst(T item) {
        if (size == length - 1) {
            grow();
        }
        front = minusOne(front);
        array[front] = item;
        size++;
    }

    /** add one item at the end of the deque.
     * @param item item we want to add
     */
    public void addLast(T item) {
        if (size == length - 1) {
            grow();
        }
        array[last] = item;
        last = plusOne(last, length);
        size++;
    }

    /** remove the first item.
     * @return the removed first item
     */
    public T removeFirst() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        T ret = array[front];
        front = plusOne(front, length);
        size--;
        return ret;
    }

    /** remove the last item.
     * @return the removed last item
     */
    public T removeLast() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        last = minusOne(last);
        size--;
        return array[last];
    }

    /** return the item indexed at index.
     * @param index index
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = front;
        for (int i = 0; i < index; i++) {
            ptr = plusOne(ptr, length);
        }
        return array[ptr];
    }

    /** print the entire deque from front to end. */
    public void printDeque() {
        int ptr = front;
        while (ptr != last) {
            System.out.print(array[ptr] + " ");
            ptr = plusOne(ptr, length);
        }
    }

}