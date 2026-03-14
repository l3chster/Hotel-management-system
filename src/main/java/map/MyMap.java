package map;
import java.util.*;

/**
 * The MyMap class is a custom implementation of the Java Map interface
 * using two parallel lists to store keys and values. It is a simplified
 * data structure designed for educational purposes and for use in the
 * hotel management system, where room numbers (keys) are mapped to
 * Room objects (values).
 *
 * Main characteristics:
 * - Stores keys and values in separate ArrayLists, maintaining their
 *   association by index.
 * - Provides basic Map operations such as put(), get(), remove(),
 *   size(), isEmpty(), containsKey(), and containsValue().
 * - Includes a custom keys() method that returns a copy of the list
 *   of keys, which is useful for iteration in the hotel system.
 * - Does not support advanced Map operations such as entrySet() or
 *   putAll(), throwing UnsupportedOperationException when called.
 *
 * Behavior:
 * - put(): Adds a new key-value pair or replaces the value if the key
 *   already exists, returning the old value if replaced.
 * - get(): Retrieves the value associated with a given key, or null
 *   if the key does not exist.
 * - remove(): Deletes a key-value pair and returns the removed value.
 * - clear(): Removes all keys and values from the map.
 * - keySet() and values(): Return copies of the keys and values for
 *   safe iteration without modifying the internal lists.
 *
 * This class demonstrates the use of generics in Java, allowing it to
 * store any type of key and value. While not as efficient or feature-rich
 * as HashMap or TreeMap, it provides a simple and transparent way to
 * manage mappings, making it suitable for small-scale applications or
 * learning scenarios.
 */

public class MyMap<K, V> implements Map<K, V> {    // generics (parameters)
    private final List<K> keys = new ArrayList<>();    // keys will be numbers of rooms
    private final List<V> values = new ArrayList<>();   // values will be the type of Room

    @Override
    public V put(K key, V value) {    // if it finds key, it replaces value, if not it adds new value
        for (int i=0; i<keys.size(); i++)
        {
            if (keys.get(i).equals(key))
            {
                V old_value = values.get(i);
                values.set(i, value);
                return old_value;
            }
        }
        keys.add(key);
        values.add(value);
        return null;
    }

    @Override
    public V get(Object key) {
        int index = keys.indexOf(key);   // return first appearance of key in map (if not found then returns -1)
        if (index != -1) {
            return values.get(index);
        }
        return null;
    }

    public List<K> keys() {
        return new ArrayList<>(keys);
    }

    @Override
    public V remove(Object key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            V removedValue = values.remove(index);
            keys.remove(index);
            return removedValue;
        }
        return null;
    }

    // something more to make everything easier

    @Override public int size()
    {
        return keys.size();
    }

    @Override public boolean isEmpty() // checks if map is empty
    {
        return keys.isEmpty();
    }

    @Override public boolean containsKey(Object key) // if key exists in map, argument is an object
    {
        return keys.contains(key);
    }
    @Override public boolean containsValue(Object value)
    {
        return values.contains(value);
    }
    @Override public void putAll(Map<? extends K, ? extends V> m) // random type which extends K or is K
    {
        throw new UnsupportedOperationException();    // do nothing (we do not need this method)
    }
    @Override public void clear()
    {
        keys.clear();    // clears all the keys in list
        values.clear();  // clears all the values in list
    }
    @Override public Set<K> keySet()
    {
        return new HashSet<>(keys);
    }
    @Override public Collection<V> values()
    {
        return new ArrayList<>(values);
    }
    @Override public Set<Entry<K, V>> entrySet()
    {
        throw new UnsupportedOperationException();   // it will not be used
    }
}


