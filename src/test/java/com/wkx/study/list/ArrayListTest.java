package com.wkx.study.list;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListTest {

    private ArrayList<String> arrayList = new ArrayList<>();

    private static final String[] STR_ARR = new String[100];

    @Before
    public void before() {
        System.out.println("start");
        for (int i = 0; i < STR_ARR.length; i++) {
            STR_ARR[i] = i + "";
        }
        for (int i = 0; i < 10; i++) {
            arrayList.add((STR_ARR[i]));
        }
    }

    @After
    public void after() {
        arrayList.forEach(System.out::println);
        System.out.println("end");
    }

    @Test
    public void isEmpty() {
        assertFalse(arrayList.isEmpty());
    }

    @Test
    public void contains() {
        for (int i = 0; i < 10; i++) {
            assertTrue(arrayList.contains(STR_ARR[i]));
        }
    }

    @Test
    public void iterator() {
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void toArray() {
        Object[] objects = arrayList.toArray();
        for (int i = 0; i < objects.length; i++) {
            assertEquals(STR_ARR[i], objects[i]);
        }
    }

    @Test
    public void toArray1() {
        String[] integers1 = new String[10];
        String[] integers2 = arrayList.toArray(integers1);
        assertArrayEquals(integers1, integers2);
        integers1 = new String[3];
        integers2 = arrayList.toArray(integers1);
        assertArrayEquals(integers1, integers2);
    }

    @Test
    public void add() {
        arrayList.add(STR_ARR[11]);
        assertEquals(arrayList.get(arrayList.size() - 1) , STR_ARR[11]);
    }

    @Test
    public void remove() {
        arrayList.remove(STR_ARR[3]);
        assertFalse(arrayList.contains(STR_ARR[3]));
    }

    @Test
    public void containsAll() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 0; i < 9; i++) {
            temp.add(STR_ARR[i]);
        }
        assertTrue(arrayList.containsAll(temp));
        temp.add(STR_ARR[20]);
        assertFalse(arrayList.containsAll(temp));
    }

    @Test
    public void addAll() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 10; i < 20; i++) {
            temp.add(STR_ARR[i]);
        }
        arrayList.addAll(temp);
        assertTrue(arrayList.containsAll(temp));
    }

    @Test
    public void addAll1() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 10; i < 20; i++) {
            temp.add((STR_ARR[i]));
        }
        arrayList.addAll(2, temp);
        assertFalse(arrayList.containsAll(temp));
    }

    @Test
    public void removeAll() {
        int i;
        List<String> temp = new ArrayList<>(10);
        for (i = 0; i < 5; i++) {
            arrayList.remove(STR_ARR[i]);
            temp.add(STR_ARR[i]);
        }
        assertFalse(arrayList.containsAll(temp));
        for (; i < 10; i++) {
            arrayList.remove(STR_ARR[i]);
            temp.add(STR_ARR[i]);
        }
        assertFalse(arrayList.containsAll(temp));
        assertTrue(arrayList.isEmpty());
    }

    @Test
    public void retainAll() {
        List<String> temp = new ArrayList<>(10);
        for (int i = 0; i < 5; i++) {
            temp.add(STR_ARR[i]);
        }
        arrayList.retainAll(temp);
        assertArrayEquals(temp.toArray(), arrayList.toArray());
    }

    @Test
    public void clear() {
        arrayList.clear();
        assertFalse(arrayList.isEmpty());
    }

    @Test
    public void get() {
        for (int i = 0; i < 10; i++) {
            assertEquals(STR_ARR[i], STR_ARR[i]);
        }
    }

    @Test
    public void set() {
        arrayList.set(3,STR_ARR[99]);
        assertTrue(arrayList.get(3).equals(STR_ARR[99]));
    }

    @Test
    public void add1() {
        arrayList.add(3,STR_ARR[99]);
        assertEquals(arrayList.get(3), STR_ARR[99]);
    }

    @Test
    public void remove1() {
        arrayList.remove(STR_ARR[4]);
        assertFalse(arrayList.contains(STR_ARR[4]));
    }

    @Test
    public void indexOf() {
        for (int i = 0; i < 10; i++) {
            assertEquals(arrayList.indexOf(STR_ARR[i]) , i);
        }
    }

    @Test
    public void lastIndexOf() {
        for (int i = 0; i < 10; i++) {
            arrayList.add(STR_ARR[i]);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(arrayList.lastIndexOf(STR_ARR[i]) == i + 10);
        }
    }
}