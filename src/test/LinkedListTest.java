package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import client.linkedList.*;

import org.junit.Before;
import org.junit.Test;

import common.*;

public class LinkedListTest {

    ListADT<Reservation> list;
    FakeGuest dataGenrator;

    @Before
    public void setUp() throws Exception {

        list = new client.linkedList.LinkedList<>();
        dataGenrator = new FakeGuest();

    }

    @Test
    public void addOneElement() {

        Reservation res = dataGenrator.makeNewReservation();

        list.add(res);

        assertEquals(1, list.size());

        assertEquals(res, list.get(0));

    }

    @Test
    public void addTenElements() {
        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        assertEquals(reservations.size(), list.size());

        for (Reservation res : list)
            if (!reservations.contains(res))
                fail("mismatch in reservations");

    }

    @Test
    public void addHundredElements() {
        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        assertEquals(reservations.size(), list.size());

        for (Reservation res : list)
            if (!reservations.contains(res))
                fail("mismatch in reservations");

    }

    @Test
    public void addThousandElements() {
        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        assertEquals(reservations.size(), list.size());

        for (Reservation res : list)
            if (!reservations.contains(res))
                fail("mismatch in reservations");

    }

    @Test
    public void addElementZeroWhenEmpty() {
        Reservation res = dataGenrator.makeNewReservation();

        list.add(0, res);

        assertEquals(1, list.size());

        assertEquals(res, list.get(0));

    }

    @Test
    public void addElementAtTopIndexWithTen() {
        int testNumber = 10;

        Reservation res = dataGenrator.makeNewReservation();

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        list.add(testNumber, res);

        assertEquals(testNumber + 1, list.size());

        assertEquals(res, list.get(testNumber));

    }

    @Test
    public void addElementAtTopIndexWithHundred() {
        int testNumber = 100;

        Reservation res = dataGenrator.makeNewReservation();

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        list.add(testNumber, res);

        assertEquals(testNumber + 1, list.size());

        assertEquals(res, list.get(testNumber));

    }

    @Test
    public void addElementAtTopIndexWithThousand() {
        int testNumber = 1000;

        Reservation res = dataGenrator.makeNewReservation();

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        list.add(testNumber, res);

        assertEquals(testNumber + 1, list.size());

        assertEquals(res, list.get(testNumber));

    }

    @Test
    public void addElementAtMiddleIndexWithTen() {
        int testNumber = 10;
        int index = testNumber / 2;

        Reservation res = dataGenrator.makeNewReservation();

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        list.add(index, res);

        assertEquals(testNumber + 1, list.size());

        assertEquals(res, list.get(index));

    }

    @Test
    public void addElementAtMiddleIndexWithHundred() {
        int testNumber = 100;
        int index = testNumber / 2;

        Reservation res = dataGenrator.makeNewReservation();

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        list.add(index, res);

        assertEquals(testNumber + 1, list.size());

        assertEquals(res, list.get(index));

    }

    @Test
    public void addElementAtMiddleIndexWithThousand() {
        int testNumber = 1000;
        int index = testNumber / 2;

        Reservation res = dataGenrator.makeNewReservation();

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        list.add(index, res);

        assertEquals(testNumber + 1, list.size());

        assertEquals(res, list.get(index));

    }

    @Test(expected = IllegalArgumentException.class)
    public void addNull() {
        list.add(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullAtIndex() {
        for (int i = 0; i < 10; i++)
            list.add(dataGenrator.makeNewReservation());

        try {
            list.add(0, null);

        } catch (IllegalArgumentException e) {
            try {
                list.add(5, null);
            } catch (IllegalArgumentException g) {
                list.add(10, null);

            }

        }

    }

    @Test
    public void containsWithOneElement() {
        Reservation res = dataGenrator.makeNewReservation();

        list.add(res);

        assertTrue(list.contains(res));

    }

    @Test
    public void containsWithTenElement() {
        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (Reservation res : reservations)
            if (!list.contains(res))
                fail("list assert contained element false");
    }

    @Test
    public void containsWithHundredElement() {
        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (Reservation res : reservations)
            if (!list.contains(res))
                fail("list assert contained element false");
    }

    @Test
    public void containsWithThousandElement() {
        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (Reservation res : reservations)
            if (!list.contains(res))
                fail("list assert contained element false");
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsWithNull() {
        list.contains(null);

    }

    @Test
    public void getWithTenElements() {
        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber; i++)
            assertTrue(reservations.get(i).equals(list.get(i)));

    }

    @Test
    public void getWithHundredElements() {
        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber; i++)
            assertTrue(reservations.get(i).equals(list.get(i)));

    }

    @Test
    public void getWithThousandElements() {
        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber; i++)
            assertTrue(reservations.get(i).equals(list.get(i)));

    }

    @Test
    public void indexOfWithTenElements() {

        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber; i++)
            assertEquals(i, list.indexOf(reservations.get(i)));
    }

    @Test
    public void indexOfWithHundredElements() {

        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber; i++)
            assertEquals(i, list.indexOf(reservations.get(i)));
    }

    @Test
    public void indexOfWithThousandElements() {

        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber; i++)
            assertEquals(i, list.indexOf(reservations.get(i)));
    }

    @Test
    public void indexOfWithTenElementsRemovehalf() {

        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber / 2; i++) {
            list.remove(i);
            reservations.remove(i);
        }
        assertEquals(5, list.size());

        for (int i = 0; i < reservations.size(); i++)
            assertEquals(reservations.indexOf(reservations.get(i)),
                    list.indexOf(reservations.get(i)));

    }

    @Test
    public void indexOfWithHundredElementsRemovehalf() {

        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber / 2; i++) {
            list.remove(i);
            reservations.remove(i);
        }
        assertEquals(50, list.size());

        for (int i = 0; i < reservations.size(); i++)
            assertEquals(reservations.indexOf(reservations.get(i)),
                    list.indexOf(reservations.get(i)));

    }

    @Test
    public void indexOfWithThousandElementsRemovehalf() {

        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        for (int i = 0; i < testNumber / 2; i++) {
            list.remove(i);
            reservations.remove(i);
        }
        assertEquals(500, list.size());

        for (int i = 0; i < reservations.size(); i++)
            assertEquals(reservations.indexOf(reservations.get(i)),
                    list.indexOf(reservations.get(i)));

    }

    @Test
    public void isEmptyWithOneElementAddRemove() {
        assertTrue(list.isEmpty());

        list.add(dataGenrator.makeNewReservation());

        assertFalse(list.isEmpty());

        list.remove(0);

        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmptyWithTenElementAddRemove() {
        int testNumber = 10;

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        assertFalse(list.isEmpty());

        for (int i = testNumber - 1; -1 < i; i--)
            list.remove(i);

        assertTrue(list.isEmpty());

    }

    @Test
    public void isEmptyWithHundredElementAddRemove() {
        int testNumber = 100;

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        assertFalse(list.isEmpty());

        for (int i = testNumber - 1; -1 < i; i--)
            list.remove(i);

        assertTrue(list.isEmpty());

    }

    @Test
    public void isEmptyWithThousandElementAddRemove() {
        int testNumber = 1000;

        for (int i = 0; i < testNumber; i++)
            list.add(dataGenrator.makeNewReservation());

        assertFalse(list.isEmpty());

        for (int i = testNumber - 1; -1 < i; i--)
            list.remove(i);

        assertTrue(list.isEmpty());

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndexWhenEmpty() {
        list.remove(0);

    }

    @Test
    public void removeAtIndexWithOneElement() {
        Reservation res = dataGenrator.makeNewReservation();

        list.add(res);

        assertEquals(res, list.remove(0));

        assertEquals(0, list.size());

    }

    @Test
    public void removeAtIndexWithTenElements() {

        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        while (!list.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(0, list.size());

            assertEquals(reservations.remove(index), list.remove(index));

            for (int i = 0; i < reservations.size(); i++) {
                assertEquals(reservations.get(i), list.get(i));

            }
        }

    }

    @Test
    public void removeAtIndexWithHundredElements() {

        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        while (!list.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(0, list.size());

            assertEquals(reservations.remove(index), list.remove(index));

            for (int i = 0; i < reservations.size(); i++) {
                assertEquals(reservations.get(i), list.get(i));

            }
        }

    }

    @Test
    public void removeAtIndexWithThousandElements() {

        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        while (!list.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(0, list.size());

            assertEquals(reservations.remove(index), list.remove(index));

            for (int i = 0; i < reservations.size(); i++) {
                assertEquals(reservations.get(i), list.get(i));

            }
        }

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtlargerIndexOneElement() {
        list.add(dataGenrator.makeNewReservation());

        list.remove(1);

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtLargerIndexTenElement() {
        for (int i = 0; i < 10; i++)
            list.add(dataGenrator.makeNewReservation());

        try {
            list.add(list.size() + 1, dataGenrator.makeNewReservation());
        } catch (IndexOutOfBoundsException e) {

            try {
                try {
                    list.add(list.size() + 1, dataGenrator.makeNewReservation());
                } catch (IndexOutOfBoundsException f) {

                    list.add(list.size() + 2, dataGenrator.makeNewReservation());
                }
            } catch (IndexOutOfBoundsException g) {

                list.add(list.size() + 3, dataGenrator.makeNewReservation());
            }

        }

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtLargerIndexHundredElement() {
        for (int i = 0; i < 100; i++)
            list.add(dataGenrator.makeNewReservation());

        try {
            list.add(list.size() + 1, dataGenrator.makeNewReservation());
        } catch (IndexOutOfBoundsException e) {

            try {
                try {
                    list.add(list.size() + 2, dataGenrator.makeNewReservation());
                } catch (IndexOutOfBoundsException f) {

                    list.add(list.size() + 3, dataGenrator.makeNewReservation());
                }
            } catch (IndexOutOfBoundsException g) {

                list.add(list.size() + 4, dataGenrator.makeNewReservation());
            }

        }

    }

    @Test(expected = IllegalStateException.class)
    public void RemoveElementFromEmptyList() {
        list.remove(dataGenrator.makeNewReservation());

    }

    @Test
    public void removeElementOneElement() {
        Reservation res = dataGenrator.makeNewReservation();

        list.add(res);

        assertEquals(res, list.remove(res));

        assertTrue(list.isEmpty());

    }

    @Test
    public void removeTenElement() {
        int testNumber = 10;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        assertEquals(list.size(), reservations.size());

        while (!list.isEmpty()) {
            Reservation res = reservations.get(ThreadLocalRandom.current().nextInt(0, reservations.size()));

            list.remove(res);
            reservations.remove(res);

            assertEquals(list.size(), reservations.size());


            for (int i = 0; i < list.size(); i++)
                assertEquals(reservations.get(i), list.get(i));

        }

    }


    @Test
    public void removeHundredElement() {
        int testNumber = 100;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        assertEquals(list.size(), reservations.size());

        while (!list.isEmpty()) {
            Reservation res = reservations.get(ThreadLocalRandom.current().nextInt(0, reservations.size()));

            list.remove(res);
            reservations.remove(res);

            assertEquals(list.size(), reservations.size());


            for (int i = 0; i < list.size(); i++)
                assertEquals(reservations.get(i), list.get(i));

        }

    }

    @Test
    public void removeThousandElement() {
        int testNumber = 1000;

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());

        for (Reservation res : reservations)
            list.add(res);

        assertEquals(list.size(), reservations.size());

        while (!list.isEmpty()) {
            Reservation res = reservations.get(ThreadLocalRandom.current()
                    .nextInt(0, reservations.size()));

            list.remove(res);
            reservations.remove(res);

            assertEquals(list.size(), reservations.size());

            for (int i = 0; i < list.size(); i++)
                assertEquals(reservations.get(i), list.get(i));

        }

    }

    @Test
    public void sizeTest() {

        for (int i = 0; i < 1000; i++) {
            list.add(i, dataGenrator.makeNewReservation());
            assertEquals(i + 1, list.size());

        }

        for (int i = 999; i > -1; i--) {
            list.remove(i);
            assertEquals(i, list.size());


        }


    }

    @Test
    public void iteratorTestOneElement() {
        Reservation res = dataGenrator.makeNewReservation();

        list.add(res);

        Iterator it = list.iterator();

        while (it.hasNext()) {
            res.equals(it.next());


        }


    }

    @Test
    public void iteratorTestTenElements() {
        int testNumber = 10;


        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());


        for (Reservation res : reservations)
            list.add(res);


        Iterator<Reservation> ite = list.iterator();

        int counter = 0;

        while (ite.hasNext()) {
            assertTrue(ite.next().equals(reservations.get(counter)));
            counter++;

        }

    }

    @Test
    public void iteratorTestHundredElements() {
        int testNumber = 100;


        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());


        for (Reservation res : reservations)
            list.add(res);


        Iterator<Reservation> ite = list.iterator();

        int counter = 0;

        while (ite.hasNext()) {
            assertTrue(ite.next().equals(reservations.get(counter)));
            counter++;

        }

    }

    @Test
    public void iteratorTestThousandElements() {
        int testNumber = 1000;


        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < testNumber; i++)
            reservations.add(dataGenrator.makeNewReservation());


        for (Reservation res : reservations)
            list.add(res);


        Iterator<Reservation> ite = list.iterator();

        int counter = 0;

        while (ite.hasNext()) {
            assertTrue(ite.next().equals(reservations.get(counter)));
            counter++;

        }

    }
}

	

