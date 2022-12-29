public class ArrayDeque<T> implements Deque<T> {

    private T[] arr;
    private int size;
    private int length;
    private int front;
    private int last;

    /** Constructor for ArrayDeque. */
    public ArrayDeque() {
        arr = (T[]) new Object[8];
        length = 8;
        size = 0;
        front = 4;
        last = 4;
    }

    /**
     * return if the list is empty.
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the size of the list.
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * return index - 1
     * @param index
     * @return
     */
    private int minusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index, int module) {
        if (index == module - 1) {
            return 0;
        }
        return index + 1;
    }

    private void grow() {
        T[] newArr = (T[]) new Object[length * 2];

        int ptr1 = front;
        int ptr2 = length;

        while (ptr1 != last) {
            newArr[ptr2] = arr[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length * 2);
        }

        front = length;
        last = ptr2;
        arr = newArr;
        length *= 2;
    }

    private void shrink() {
        T[] newArr = (T[]) new Object[length / 2];

        int ptr1 = front;
        int ptr2 = length / 4;

        while (ptr1 != last) {
            newArr[ptr2] = arr[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length / 2);
        }

        front = length / 4;
        last = ptr2;
        arr = newArr;
        length /= 2;
    }

    @Override
    public void addFirst(T item) {
        if (size == length - 1) {
            grow();
        }

        front = minusOne(front);
        arr[front] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == length - 1) {
            grow();
        }
        arr[last] = item;
        last = plusOne(last, length);
        size++;
    }

    @Override
    public T removeFirst() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        T temp = arr[front];
        arr[front] = null;
        front = plusOne(front, length);
        size--;
        return temp;
    }

    @Override
    public T removeLast() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }

        last = minusOne(last);
        T temp = arr[last];
        arr[last] = null;
        size--;
        return temp;
    }

    @Override
    public T get(int index) {
        int ptr = front;

        while (ptr != last) {
            if (index == 0) {
                return arr[ptr];
            }
            index--;
            ptr = plusOne(ptr, length);
        }
        return null;
    }

    /** print the entire deque from front to end. */
    @Override
    public void printDeque() {
        int ptr = front;
        while (ptr != last) {
            System.out.print(arr[ptr] + " ");
            ptr = plusOne(ptr, length);
        }
    }
}
