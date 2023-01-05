package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Li Ming
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private void validKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null.");
        }
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp == 0) return p.value;
        if (cmp > 0) return getHelper(key, p.right);
        else return getHelper(key, p.left);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        validKey(key);
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {

        if (p == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
            return p;
        }
        if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
            return p;
        }

        else {
            p.left = putHelper(key, value ,p.left);
            return p;
        }

    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        validKey(key);
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySetHelper(root, set);
        return set;
    }

    private void keySetHelper(Node node, Set<K> set) {
        if (node == null) return;

        keySetHelper(node.left, set);
        set.add(node.key);
        keySetHelper(node.right, set);
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        validKey(key);
        return (V)removeHelper(key, root)[0];
    }

    private Object[] removeHelper(K key, Node node) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        Object[] o = new Object[2];
        if (cmp == 0) {
            o[0] = node.value;
            if (node.left == null) {
                node = node.right;
            }
            else if (node.right == null) {
                node = node.left;
            }
            else {
                Node t = node;
                node = min(t.right);
                node.left = t.left;
                node.right = deleteMin(t.right);
            }
            size--;
            o[1] = node;
            return o;
        }
        else if (cmp > 0) {
            o = removeHelper(key, node.right);
            node.right = (Node) o[1];
            o[1] = node;
            return o;
        }
        else {
            o = removeHelper(key, node.left);
            node.left= (Node) o[1];
            o[1] = node;
            return o;
        }
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            node = node.right;
            return node;
        }
        return deleteMin(node.left);
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        else return min(node.left);
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        validKey(key);
        if (!get(key).equals(value)) return null;
        return (V)removeHelper(key, root)[0];
    }

    @Override
    public Iterator<K> iterator() {
        return new MyIterator<>();
    }

    private class MyIterator<K> implements Iterator<K> {

        Set<K> set = (Set<K>) keySet();
        Iterator<K> iterator = set.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public K next() {
            return iterator.next();
        }
    }
}
