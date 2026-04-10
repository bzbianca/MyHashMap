
/**
 * Bianca Baccay
 * Course: ADVANCED JAVA
 * Date: 04/09/2026
 *
 * Program that implements a hash map using separate chaining
 */

import java.util.Iterator;
import java.util.LinkedList;

public class MyHashMap<K, V> {

    // ── Inner entry class ─────────────────────────────────────────────────
    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 11;

    // ── Constructor ───────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size  = 0;
    }

    // ── Hash helper ───────────────────────────────────────────────────────
    private int hash(K key) {
        // Keys must not be null
        return Math.abs(key.hashCode()) % table.length;
    }

    // ── put ───────────────────────────────────────────────────────────────
    /**
     * Ensures there are no collisions or duplicates
     *
     * @param key key of hashmap
     * @param value value of hashmap
     * @return old value of the same key that already exists
     * @return null if no existing entry found
     */
    public V put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) { // Used AI to find the correct for condition
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        table[index].addFirst(new Entry<>(key, value));
        size++;
        return null;
    }
    public V get(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    // ── containsKey ───────────────────────────────────────────────────────
    /**
     * Checks if a key exists in a map
     *
     * @param key key of hashmap
     * @return true if key finds a match
     * @return false if key does not find a match or is null
     */
    public boolean containsKey(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return false;
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    // ── remove ────────────────────────────────────────────────────────────
    /**
     * Removes value from list if key matches
     *
     * @param key key of hashmap
     * @return oldValue if matching key was found
     * @return null if no existing entry found
     */
    public V remove(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        Iterator<Entry<K, V>> it = table[index].iterator();

        while (it.hasNext()) {
            Entry<K, V> entry = it.next(); // Used AI to figure out how to get from list

            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                it.remove();
                size--;
                return oldValue;
            }
        }

        return null;
    }

    // ── size ──────────────────────────────────────────────────────────────
    public int size() {
        return size;
    }

    // ── isEmpty ───────────────────────────────────────────────────────────
    public boolean isEmpty() {
        return size == 0;
    }

    // ── Test driver ───────────────────────────────────────────────────────
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // put -- basic insertions
        map.put("cat", 1);
        map.put("dog", 2);
        map.put("rat", 3);
        map.put("bat", 4);
        map.put("ant", 5);
        System.out.println("Size after 5 insertions: " + map.size());   // 5

        // get -- existing keys
        System.out.println("get(cat): " + map.get("cat"));              // 1
        System.out.println("get(bat): " + map.get("bat"));              // 4

        // get -- missing key
        System.out.println("get(owl): " + map.get("owl"));              // null

        // put -- duplicate key (update)
        map.put("cat", 99);
        System.out.println("get(cat) after update: " + map.get("cat")); // 99
        System.out.println("Size after update: " + map.size());         // 5

        // containsKey
        System.out.println("containsKey(dog): " + map.containsKey("dog")); // true
        System.out.println("containsKey(elk): " + map.containsKey("elk")); // false

        // remove -- existing key
        map.remove("dog");
        System.out.println("get(dog) after remove: " + map.get("dog")); // null
        System.out.println("Size after remove: " + map.size());         // 4

        // remove -- missing key
        map.remove("owl");
        System.out.println("Size after removing missing key: " + map.size()); // 4

        // null value -- a key can legitimately map to null
        map.put("fox", null);
        System.out.println("get(fox): " + map.get("fox"));              // null
        System.out.println("containsKey(fox): " + map.containsKey("fox")); // true
        System.out.println("Size with null value: " + map.size());      // 5

        // forced collision -- "AaAaAa" and "BBBaaa" have the same hashCode in Java
        MyHashMap<String, Integer> collisionMap = new MyHashMap<>();
        collisionMap.put("AaAaAa", 1);
        collisionMap.put("BBBaaa", 2);
        System.out.println("Collision get(AaAaAa): " + collisionMap.get("AaAaAa")); // 1
        System.out.println("Collision get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        collisionMap.remove("AaAaAa");
        System.out.println("After remove, get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        System.out.println("After remove, size: " + collisionMap.size()); // 1
    }
}
