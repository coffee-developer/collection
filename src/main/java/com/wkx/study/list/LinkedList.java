package com.wkx.study.list;


import java.util.*;

public class LinkedList<E> extends AbstractList<E> implements List<E> {

    private Node<E> first;

    private Node<E> end;

    @Override
    public Object[] toArray() {
        Object[] objectArr = new Object[size];
        Node<E> current = first ;
        for (int i = 0; i < size; i++) {
            objectArr[i] = current .e;
            current = current.next;
        }
        return objectArr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Node<E> current = first ;
        for (int i = 0; i < a.length; i++) {
            @SuppressWarnings("unchecked")
            T t = (T) current.e;
            a[i] = t;
            current = current.next;

        }
        return a;
    }

    @Override
    public boolean add(E e) {
        if (first == null && end == null) {
            Node<E> node =new Node<>();
            node.e = e;

            first = end = node;
            size = 1;
            return true;
        }

        end = end.insertNext(e);
        size ++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            E e = iterator.next();
            if (Objects.equals(e, o)) {
                iterator.remove();
                size --;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index == 0 && isEmpty()) {
            addAll(c);
            return true;
        }

        Node<E> node = getNode(index);
        boolean ifFirst = true;
        for (E e : c) {
            if (ifFirst) {
                ifFirst = false;
                node.insertPrevious(e);
            } else {
                node.insertNext(e);
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        batchRemove(c, false);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        batchRemove(c, true);
        return true;
    }

    private void batchRemove(Collection<?> c, boolean complement) {
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            E e = iterator.next();
            boolean ifContain = c.contains(e);
            if (ifContain == !complement) {
                iterator.remove();
            }
        }
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        Node<E> current = first;
        while (current != null) {
            current.removeSelf();
            current = current.next;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        return getNode(index).e;
    }

    @Override
    public E set(int index, E element) {
        Node<E> tmp = getNode(index);
        E e = tmp.e;
        tmp.e = element;
        return e;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0 && first == end && end == null) {
            add(element);
            return;
        }
        if (index == size) {
            end.insertNext(element);
            return;
        }

        getNode(index).insertPrevious(element);
        size ++;
    }

    @Override
    public E remove(int index) {

        Node<E> temp = getNode(index);
        temp.removeSelf();
        size --;

        if (first == temp) {
            first = first.next;
        }
        if (end == temp) {
            end = end.previous;
        }

        return temp.e;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new LinkedItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    private Node<E> getNode(int index) {
        checkRange(index);

        if (index == size) {
            return end;
        }

        Node<E> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private static class Node<E> {
        private E e;

        private Node<E> previous;

        private Node<E> next;


        Node<E> insertNext(E e) {
            Node<E> newNode = new Node<>();

            newNode.e = e;
            newNode.previous = this;
            newNode.next = this.next;

            if (this.next != null) {
                this.next.previous = newNode;
            }

            this.next = newNode;


            return newNode;
        }


        Node<E> insertPrevious(E e) {
            Node<E> newNode = new Node<>();

            newNode.e = e;
            newNode.previous = this.previous;
            newNode.next = this;

            if (this.previous != null) {
                this.previous.next = newNode;
            }

            this.previous = newNode;

            return newNode;
        }

        void removeSelf() {
            if (this.previous != null) {
                this.previous.next = this.next;
            }
            if (this.next != null) {
                this.next.previous = this.previous;
            }
        }
    }

    public class LinkedItr implements ListIterator<E> {

        private Node<E> current;
        private int lastRef;
        private int cursor;

        public LinkedItr(int index) {
            current = getNode(index);
            cursor = index == size && !isEmpty() ? index - 1 : index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            E e = current.e;
            current = current.next;
            lastRef = cursor;
            cursor ++;
            return e;
        }

        @Override
        public boolean hasPrevious() {
            return cursor >= 0;
        }

        @Override
        public E previous() {
            E e = current.e;
            current = current.previous;
            lastRef = cursor;
            cursor--;

            return e;
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
            LinkedList.this.remove(lastRef);
            lastRef = cursor;
            cursor --;
        }

        @Override
        public void set(E e) {
            LinkedList.this.set(lastRef, e);
        }

        @Override
        public void add(E e) {
            LinkedList.this.add(e);
        }
    }
}
