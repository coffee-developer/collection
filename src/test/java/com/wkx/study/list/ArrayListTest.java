package com.wkx.study.list;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListTest {

    private List<String> list = new LinkedList<>();

    private static final String[] STR_ARR = new String[100];

    @Before
    public void before() {
        System.out.println("start");
        for (int i = 0; i < STR_ARR.length; i++) {
            STR_ARR[i] = i + "";
        }
        for (int i = 0; i < 10; i++) {
            list.add((STR_ARR[i]));
        }
    }

    @After
    public void after() {
        list.forEach(System.out::println);
        System.out.println("end");
    }

    @Test
    public void isEmpty() {
        assertFalse(list.isEmpty());
    }

    @Test
    public void contains() {
        for (int i = 0; i < 10; i++) {
            assertTrue(list.contains(STR_ARR[i]));
        }
    }

    @Test
    public void iterator() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void toArray() {
        Object[] objects = list.toArray();
        for (int i = 0; i < objects.length; i++) {
            assertEquals(STR_ARR[i], objects[i]);
        }
    }

    @Test
    public void toArray1() {
        String[] integers1 = new String[10];
        String[] integers2 = list.toArray(integers1);
        assertArrayEquals(integers1, integers2);
        integers1 = new String[3];
        integers2 = list.toArray(integers1);
        assertArrayEquals(integers1, integers2);
    }

    @Test
    public void add() {
        list.add(STR_ARR[11]);
        assertEquals(list.get(list.size() - 1) , STR_ARR[11]);
    }

    @Test
    public void remove() {
        list.remove(STR_ARR[3]);
        assertFalse(list.contains(STR_ARR[3]));
    }

    @Test
    public void containsAll() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 0; i < 9; i++) {
            temp.add(STR_ARR[i]);
        }
        assertTrue(list.containsAll(temp));
        temp.add(STR_ARR[20]);
        assertFalse(list.containsAll(temp));
    }

    @Test
    public void addAll() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 10; i < 20; i++) {
            temp.add(STR_ARR[i]);
        }
        list.addAll(temp);
        assertTrue(list.containsAll(temp));
    }

    @Test
    public void addAll1() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 10; i < 20; i++) {
            temp.add((STR_ARR[i]));
        }
        list.addAll(2, temp);
        assertFalse(list.containsAll(temp));
    }

    @Test
    public void removeAll() {
        int i;
        List<String> temp = new ArrayList<>(10);
        for (i = 0; i < 5; i++) {
            temp.add(STR_ARR[i]);
        }
        list.removeAll(temp);
        assertFalse(list.containsAll(temp));
        for (; i < 10; i++) {
            temp.add(STR_ARR[i]);
        }
        list.removeAll(temp);
        assertFalse(list.containsAll(temp));
        assertTrue(list.isEmpty());
    }

    @Test
    public void retainAll() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 0; i < 5; i++) {
            temp.add(STR_ARR[i]);
        }
        list.retainAll(temp);
        assertArrayEquals(temp.toArray(), list.toArray());
    }

    @Test
    public void clear() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void get() {
        for (int i = 0; i < 10; i++) {
            assertEquals(STR_ARR[i], STR_ARR[i]);
        }
    }

    @Test
    public void set() {
        list.set(3,STR_ARR[99]);
        assertTrue(list.get(3).equals(STR_ARR[99]));
    }

    @Test
    public void add1() {
        list.add(3,STR_ARR[99]);
        assertEquals(list.get(3), STR_ARR[99]);
    }

    @Test
    public void remove1() {
        list.remove(STR_ARR[4]);
        assertFalse(list.contains(STR_ARR[4]));
    }

    @Test
    public void indexOf() {
        for (int i = 0; i < 10; i++) {
            assertEquals(list.indexOf(STR_ARR[i]) , i);
        }
    }

    @Test
    public void lastIndexOf() {
        for (int i = 0; i < 10; i++) {
            list.add(STR_ARR[i]);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(list.lastIndexOf(STR_ARR[i]) , i + 10);
        }
    }
}