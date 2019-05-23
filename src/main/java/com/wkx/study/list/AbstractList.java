package com.wkx.study.list;

import java.util.*;

public abstract class AbstractList<E> implements List<E> {

    protected int size;

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
        return listIterator();
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int indexOf(Object o) {
        ListIterator<E> listIterator = listIterator(0);
        while (listIterator.hasNext()) {
            E e = listIterator.next();
            if (Objects.equals(e, o)) {
                return listIterator.nextIndex();
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        ListIterator<E> listIterator = listIterator(size);
        while (listIterator.hasPrevious()) {
            E e = listIterator.previous();
            if (Objects.equals(e, o)) {
                return listIterator.nextIndex();
            }
        }

        return -1;
    }

    protected void checkRange(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    public class ListItr implements ListIterator<E> {

        public ListItr(int index) {
            this.cursor = index == size && !isEmpty() ? index -1 : index;

        }

        private int cursor;

        private int lastRef;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            lastRef = cursor;
            return get(cursor ++);
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public E previous() {
            lastRef = cursor;
            return get(cursor--);
        }

        @Override
        public int nextIndex() {
            return lastRef;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            AbstractList.this.remove(lastRef);
            lastRef = cursor;
            cursor --;
        }

        @Override
        public void set(E e) {
            AbstractList.this.set(lastRef, e);
        }

        @Override
        public void add(E e) {
            AbstractList.this.add(e);
            cursor ++;
        }
    }
}
