package com.wkx.study.list;

import java.util.*;

public class ArrayList<E> extends AbstractList<E> implements List<E> {

    private int size;

    private Object[] data;

    private static final Object[] EMPTY_ARRAY = {};

    private static final int INIT_CAPACITY = 10;

    public ArrayList() {
        this.data =  EMPTY_ARRAY;
    }

    public ArrayList(int capacity) {
        if (capacity == 0) {
            data = EMPTY_ARRAY;
        } else {
            data = new Object[capacity];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        Object[] objectArray = new Object[size];
        System.arraycopy(data, 0, objectArray, 0, size);
        return objectArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length > size) {
            for (int i = size; i < a.length; i++) {
                a[i] = null;
            }
        }
        System.arraycopy(data, 0, a, 0, a.length);
        return a;
    }

    @Override
    public boolean add(E e) {
        ensureLength(size + 1);
        data[size ++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index;
        if ((index = indexOf(o)) >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (size < c.size()) {
            return false;
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        ensureLength(c.size() + size);
        for (E e : c) {
            data[size ++ ] = e;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        ensureLength(c.size() + size);
        System.arraycopy(data, index, data, size, c.size());
        Object[] objects = c.toArray();
        for (int i = index; i < objects.length; i++) {
            data[index++] = objects[i];
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int index;
        for (Object o : c) {
            if ((index = indexOf(o)) > 0) {
                remove(index);
            }
        }
        size -= c.size();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Object[] tempArr = new Object[c.size()];
        int i = 0;
        for (Object o : c) {
            int index = indexOf(o);
            if (index != -1) {
                tempArr[i ++] = data[index];
            }
        }
        data = tempArr;
        size = tempArr.length;
        return true;
    }

    @Override
    public void clear() {
        for (int i = data.length - 1; i >= 0; i--) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        checkRange(index);
        @SuppressWarnings("unchecked")
        E e = (E) data[index];
        return e;
    }

    @Override
    public E set(int index, E element) {
        checkRange(index);
        @SuppressWarnings("unchecked")
        E e = (E) data[index];
        data[index] = element;
        return e;
    }

    @Override
    public void add(int index, E element) {
        ensureLength(size + 1);
        System.arraycopy(data, index, data, index+1, size - index + 1);
        data[index] = element;
        size ++;
    }

    @Override
    public E remove(int index) {
        checkRange(index);
        @SuppressWarnings("unchecked")
        E e = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size- index - 1);
        size--;
        data[size] = null;
        return e;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < data.length; i++) {
            if (Objects.equals(data[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = data.length - 1; i >= 0; i--) {
            if (Objects.equals(data[i], o)) {
                return i;
            }
        }
        return -1;
    }




    private void checkRange(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void ensureLength(int miniCapacity) {
        if (miniCapacity > data.length) {
            int nextCapacity = data == EMPTY_ARRAY ? INIT_CAPACITY : size + (size >> 1);
            nextCapacity =  Math.max(nextCapacity, miniCapacity);
            Object[] tempArray = new Object[nextCapacity];
            System.arraycopy(data, 0, tempArray, 0, size);
            data = tempArray;
        }
    }

    public  class Itr implements Iterator<E> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            @SuppressWarnings("unchecked")
            E e = (E) data[cursor++];
            return e;
        }
    }
}
