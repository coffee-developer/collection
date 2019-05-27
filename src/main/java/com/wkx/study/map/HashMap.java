package com.wkx.study.map;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HashMap<K,V> implements Map<K,V> {

    private int size;

    private double loadFactor;

    private int threshold;

    private Node<K,V>[] nodeArr;


    private static final int INIT_NODE_ARR_LENGTH = 16;

    private static final double DEFAULT_LOADFACTOR = 0.75;

    public HashMap() {
        loadFactor = DEFAULT_LOADFACTOR;
        threshold = (int) (loadFactor * DEFAULT_LOADFACTOR);
        @SuppressWarnings("unchecked")
        Node<K, V>[] nodes = new Node[INIT_NODE_ARR_LENGTH];
        nodeArr = nodes;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size != 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node node1 : nodeArr) {
            for (Node node2 = node1; node2 != null; node2 = node2.next) {
                if (Objects.equals(value, node2.v)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Node<K,V> node = getNode(key);
        return  node != null ? node.v : null;
    }

    @Override
    public V put(K key, V value) {
        return putNode(key, value, false);
    }

    @Override
    public V remove(Object key) {
        Node<K, V> node = removeNode(key, null, false);
        return node != null ? node.v : null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<? extends Entry<? extends K, ? extends V>> entries = m.entrySet();
        for (Entry<? extends K, ? extends V> entry : entries) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < nodeArr.length; i++) {
            nodeArr[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V v = get(key);
        return v != null ? v : defaultValue;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        for (Node<K, V> node1 : nodeArr) {
            for (Node<K, V> node2 = node1;node2 != null; node2 = node2.next) {
                node2.v = function.apply(node2.k, node2.v);
            }
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return putNode(key, value, true);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return removeNode(key, value, true) != null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return replaceValue(key, oldValue, newValue, true) != null;
    }

    @Override
    public V replace(K key, V value) {
        Node<K, V> node = replaceValue(key, value, null, false);
        return node != null ? node.v : null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        Node<K, V> node = getNode(key);
        if (node == null) {
            V v = mappingFunction.apply(key);
            putNode(key, v, false);
            return v;
        }
        return node.v;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            V v = remappingFunction.apply(key, node.v);
            node.v = v;
            return v;
        }
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Node<K, V> node = getNode(key);
        V oldValue = node != null ? node.v : null;
        V newValue = remappingFunction.apply(key, oldValue);
        if (node != null) {
            node.v = newValue;
        } else {
            putNode(key, newValue, false);
        }
        return oldValue;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            V apply;
            if (node.v != null) {
               apply  = remappingFunction.apply(node.v, value);
            } else {
                apply = value;
            }

            if (apply != null) {
                node.v = apply;
            } else {
                removeNode(key, null, false);
            }
            return apply;
        }
        if (value != null) {
            putNode(key, value, false);
        }
        return null;
    }

    private int calculationKeyIndex(int hash) {
        return hash & nodeArr.length - 1;
    }

    private Node<K, V> getNode(Object key) {
        int hash = Objects.hashCode(key);
        int index = calculationKeyIndex(hash);
        for (Node<K,V> node = nodeArr[index]; node != null; node = node.next) {
            if ( hash == node.hashKey && Objects.equals(key, node.k)) {
                return node;
            }
        }
        return null;
    }

    private V putNode(K key, V value, boolean ifAbsent) {
        int hash = Objects.hash(key);
        int index = calculationKeyIndex(hash);
        Node<K, V> node = nodeArr[index];
        if (node == null) {
            Node<K,V> newNode = new Node(key, value);
            nodeArr[index] = newNode;
            size ++;
            return null;
        }else {
            for (; ; node = node.next) {
                if (hash == node.hashKey && Objects.equals(key, node.k) ) {
                    if (!ifAbsent) {
                        V v = node.v;
                        node.v = value;
                        return v;
                    }
                    return null;
                } else if (node.next == null) {
                    node.insertNext(key, value);
                    size ++;
                    return null;
                }

            }
        }
    }

    private Node<K, V> removeNode(Object key, Object V, boolean ifCheckValue) {
        int index = calculationKeyIndex(Objects.hashCode(key));
        Node<K, V> current = nodeArr[index];
        if (current == null) {
            return null;
        } else if (current.equalKey(key)) {
            boolean flag =  !ifCheckValue || Objects.equals(V, current.v);
            if (flag) {
                nodeArr[index] = current.next;
                size --;
                return current;
            }
            return null;
        }
        Node<K, V> previous = current;
        for (current = current.next;current != null; previous = current, current = current.next) {
            if (current.equalKey(key)) {
                previous.next = current.next;
                return current;
            }
        }
        return null;
    }

    private Node<K, V> replaceValue(K key, V newValue, V oldValue, boolean ifCheckValue) {
        Node<K, V> node = getNode(key);
        if (node == null) {
            return null;
        }
        boolean flag = !ifCheckValue || Objects.equals(node.v, oldValue);
        if (flag) {
            node.v = newValue;
            return node;
        }
        return null;
    }

    public static class Node<K, V> {
        private int hashKey;
        private K k;
        private V v;

        private Node<K, V> next;

        boolean equalKey(Object key) {
            return Objects.hash(key) == this.hashKey && Objects.equals(key, this.k);
        }

        Node(K k, V v) {
            this.hashKey = Objects.hashCode(k);
            this.k = k;
            this.v = v;
        }

        Node insertNext(K k, V v) {
            Node newNode = new Node<>(k, v);
            this.next = newNode;

            return newNode;
        }
    }

    final class KeySet extends AbstractSet<K> {
        @Override
        public boolean remove(Object o) {
            return HashMap.this.remove(o) != null;
        }

        @Override
        public boolean contains(Object o) {
            return HashMap.this.containsKey(o);
        }

        @Override
        public Iterator<K> iterator() {
            return null;
        }

        @Override
        public void clear() {
            HashMap.this.clear();
        }

        @Override
        public int size() {
            return HashMap.this.size();
        }
    }
}
