/**
 * Test class for the fileadapter.
 *
 * @author MES
 */
package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.FakeGuest;
import common.Reservation;
import server.*;

public class FileAdapterTest {

    IFileAdapter adapter;
    File file;
    String reservationPath = "reservationssss.bin";
    String inHousePath = "inHouseGuestssssss";
    String pastResPath = "pastReservationsssssss.bin";

    @After
    public void cleanUp() {

        file = null;
        // manual run of the garbage collector, ensures that file is relased by
        // OS.
        System.gc();

        file = new File(reservationPath);

        if (file.exists())
            assertTrue(file.delete());

        file = null;
        // manual run of the garbage collector, ensures that file is relased by
        // OS.
        System.gc();

        file = new File(pastResPath);

        if (file.exists())
            assertTrue(file.delete());


        file = null;
        // manual run of the garbage collector, ensures that file is relased by
        // OS.
        System.gc();

        file = new File(inHousePath);

        if (file.exists())
            assertTrue(file.delete());

    }

    @Before
    public void setUp() throws Exception {

        adapter = new FileAdapter(reservationPath, pastResPath, inHousePath);

        file = new File(reservationPath);

        if (!file.exists())
            assertTrue(file.createNewFile());

        else {
            assertTrue(file.delete());

            assertTrue(file.createNewFile());
        }

        file = new File(inHousePath);

        if (!file.exists())
            assertTrue(file.createNewFile());

        else {
            assertTrue(file.delete());

            assertTrue(file.createNewFile());
        }

        file = new File(pastResPath);

        if (!file.exists())
            assertTrue(file.createNewFile());

        else {
            assertTrue(file.delete());

            assertTrue(file.createNewFile());
        }

    }


    @Test
    public void appendOneReservationToFile() throws IOException {


        Reservation temp = new FakeGuest().makeNewReservation();

        adapter.createReservation(temp);

        assertEquals(1, adapter.getAll().size());

        ArrayList<Reservation> arr = adapter.getAll();

        if (!arr.contains(temp))
            fail("reservation not found");


    }

    @Test
    public void appendTenReservationToFile() throws IOException {

        ArrayList<Reservation> arr = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            arr.add(new FakeGuest().makeNewReservation());

        for (int i = 0; i < arr.size(); i++)
            adapter.createReservation(arr.get(i));

        ArrayList<Reservation> fromFile = adapter.getAll();

        if (fromFile.size() != arr.size())
            fail("size should be the same");

        for (int i = 0; i < arr.size(); i++)
            if (!arr.contains(fromFile.get(i)))
                fail("Reservations missing");


    }

    @Test
    public void appendhundredReservationToFile() throws IOException {


        ArrayList<Reservation> arr = new ArrayList<>();

        for (int i = 0; i < 100; i++)
            arr.add(new FakeGuest().makeNewReservation());

        for (int i = 0; i < arr.size(); i++)
            adapter.createReservation(arr.get(i));

        ArrayList<Reservation> fromFile = adapter.getAll();

        System.out.println(fromFile.toString());

        if (fromFile.size() != arr.size())
            fail("size should be the same");

        for (int i = 0; i < arr.size(); i++)
            if (!arr.contains(fromFile.get(i)))
                fail("Reservations missing");


    }

    /**
     @Test public void appendThousandReservationToFile() throws IOException {

     ArrayList<Reservation> arr = new ArrayList<>();

     for (int i = 0; i < 1000; i++)
     arr.add(new FakeGuest().makeNewReservation());

     for (int i = 0; i < arr.size(); i++)
     adapter.createReservation( arr.get(i));

     ArrayList<Reservation> fromFile = adapter.getAll();

     System.out.println(fromFile.toString());

     if (fromFile.size() != arr.size())
     fail("size should be the same");

     for (int i = 0; i < arr.size(); i++)
     if (!arr.contains(fromFile.get(i)))
     fail("Reservations missing");


     }
     **/
    @Test(expected = IllegalArgumentException.class)
    public void appendNull() throws IOException {


        adapter.createReservation(null);


    }

    @Test
    public void getReservationFromEmptyFile() throws IOException {


        ArrayList<Reservation> fromFile = adapter.getAll();

        assertTrue(fromFile != null);


    }

    @Test
    public void removeOneReservationsFromFile() throws IOException {


        ArrayList<Reservation> generatedRes = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            generatedRes.add(new FakeGuest().makeNewReservation());

        for (Reservation res : generatedRes)
            adapter.createReservation(res);

        Reservation reservation = generatedRes.get(0);

        adapter.checkIn(generatedRes.get(0));

        ArrayList<Reservation> fromFile = adapter.getAll();

        assertEquals(generatedRes.size() - 1, fromFile.size());


        if (fromFile.contains(reservation))
            fail("the reservation was not removed");

        for (Reservation res : fromFile)
            if (!generatedRes.contains(res))
                fail("An unknown reservation is in the file");


    }

    @Test
    public void removeTenReservationsFromFile() throws IOException {


        ArrayList<Reservation> generatedRes = new ArrayList<>();


        for (int i = 0; i < 100; i++)
            generatedRes.add(new FakeGuest().makeNewReservation());

        ArrayList<Reservation> toRemove = getReducedList(generatedRes, 10);

        for (Reservation res : generatedRes)
            adapter.createReservation(res);

        for (int i = 0; i < toRemove.size(); i++)
            adapter.checkIn(toRemove.get(i));


        ArrayList<Reservation> fromFile = adapter.getAll();

        assertEquals(generatedRes.size() - 10, fromFile.size());

        for (int i = 0; i < toRemove.size(); i++)
            if (fromFile.contains(toRemove.get(i)))
                fail("one of the removed reservation was still in the file");


    }

    /**
     @Test public void removeHundredReservationaFromFile() throws IOException
     {



     ArrayList<Reservation> generatedRes = new ArrayList<>();


     for (int i = 0; i < 1000; i++)
     generatedRes.add(new FakeGuest().makeNewReservation());

     ArrayList<Reservation> toRemove = getReducedList(generatedRes, 100);

     for (Reservation res : generatedRes)
     adapter.createReservation(res);

     for(int i = 0; i<toRemove.size();i++)
     adapter.checkIn( toRemove.get(i));


     ArrayList<Reservation> fromFile = adapter.getAll();

     assertEquals(generatedRes.size()-100, fromFile.size());

     for(int i = 0; i<toRemove.size();i++)
     if(fromFile.contains(toRemove.get(i)))
     fail("one of the removed reservation was still in the file");









     }**/

    @Test(expected = IllegalStateException.class)
    public void removeFromEmptyFile() throws IOException {


        adapter.checkIn(new FakeGuest().makeNewReservation());


    }

    @Test
    public void updateOneReservation() {
        /**
         * update the FileAdapt, with the path name for current test
         *
         */
        Reservation oldRes = new FakeGuest().makeNewReservation();
        Reservation newRes = oldRes.copy();

        adapter.createReservation(oldRes);


        int oldRoomNumber = oldRes.getRoomNumber();
        newRes.setRoomNumber(newRes.getRoomNumber() + 1);

        adapter.updateReservation(oldRes, newRes);


        ArrayList<Reservation> fromFile = adapter.getAll();

        assertTrue(fromFile.size() == 1);

        assertTrue(fromFile.get(0).equals(newRes));

    }

    @Test
    public void checkInOneReservation() {
        Reservation res = new FakeGuest().makeNewReservation();


        adapter.createReservation(res);

        assertTrue(adapter.getAll().size() == 1);

        adapter.checkIn(res);

        assertTrue(adapter.getAll().size() == 0);


        ArrayList<Reservation> fromFile = adapter.getInHouseGuests();

        assertTrue(fromFile.size() == 1);

        assertTrue(fromFile.contains(res));

    }

    @Test
    public void checkInTenResevations() {
        ArrayList<Reservation> res = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            res.add(new FakeGuest().makeNewReservation());

        assertTrue(res.size() == 10);

        for (Reservation toCheck : res)
            adapter.createReservation(toCheck);

        assertTrue(adapter.getAll().size() == 10);

        for (Reservation toCheck : res)
            adapter.checkIn(toCheck);

        assertTrue(adapter.getAll().size() == 0);


        ArrayList<Reservation> inhouse = adapter.getInHouseGuests();

        assertEquals(res.size(), inhouse.size());


        for (Reservation inRes : inhouse)
            if (!res.contains(inRes))
                fail("difference between the checked in reservation, and the orginal one");
    }

    @Test
    public void checkInHundredResevations() {
        ArrayList<Reservation> res = new ArrayList<>();

        for (int i = 0; i < 100; i++)
            res.add(new FakeGuest().makeNewReservation());

        assertTrue(res.size() == 100);

        for (Reservation toCheck : res)
            adapter.createReservation(toCheck);

        assertTrue(adapter.getAll().size() == 100);

        for (Reservation toCheck : res)
            adapter.checkIn(toCheck);

        assertTrue(adapter.getAll().size() == 0);


        ArrayList<Reservation> inhouse = adapter.getInHouseGuests();

        assertEquals(res.size(), inhouse.size());


        for (Reservation inRes : inhouse)
            if (!res.contains(inRes))
                fail("difference between the checked in reservation, and the orginal one");
    }


    private ArrayList<Reservation> getReducedList(ArrayList<Reservation> reservations, int number) {
        ArrayList<Reservation> list = new ArrayList<>();

        for (int i = 0; i < number; i++)
            list.add(reservations.get(i));

        return list;

    }
    @Test
    public void checkoutOneReservation()
    {
        Reservation reservation = new FakeGuest().makeNewReservation();

        adapter.createReservation(reservation);

        assertEquals(1, adapter.getAll().size());

        adapter.checkIn(reservation);

        assertEquals(0, adapter.getAll().size());

        assertEquals(1, adapter.getInHouseGuests().size());

        adapter.checkOut(reservation);

        assertEquals(0, adapter.getAll().size());

        assertEquals(0, adapter.getInHouseGuests().size());

        assertEquals(1, adapter.getPast().size());

        ArrayList<Reservation> pastres = adapter.getPast();

        if(!pastres.contains(reservation))
            fail("reservation not in past reservations");

    }
    @Test
    public void checkoutTenReservation()
    {
        ArrayList<Reservation> reservation = new ArrayList<>();

        for(int i = 0; i<10;i++)
            reservation.add(new FakeGuest().makeNewReservation());

        for(Reservation res : reservation)
            adapter.createReservation(res);

        assertEquals(10, adapter.getAll().size());

        for(Reservation res : reservation)
            adapter.checkIn(res);

        assertEquals(0, adapter.getAll().size());

        assertEquals(10, adapter.getInHouseGuests().size());


        for(Reservation res : reservation)
            adapter.checkOut(res);

        assertEquals(0, adapter.getAll().size());

        assertEquals(0, adapter.getInHouseGuests().size());

        assertEquals(10, adapter.getPast().size());

        ArrayList<Reservation> pastres = adapter.getPast();

        for(Reservation res : reservation)
            if(!pastres.contains(res))
                fail("reservation missing");

    }

    @Test
    public void checkoutHundredReservation()
    {
        ArrayList<Reservation> reservation = new ArrayList<>();

        for(int i = 0; i<100;i++)
            reservation.add(new FakeGuest().makeNewReservation());

        for(Reservation res : reservation)
            adapter.createReservation(res);

        assertEquals(100, adapter.getAll().size());

        for(Reservation res : reservation)
            adapter.checkIn(res);

        assertEquals(0, adapter.getAll().size());

        assertEquals(100, adapter.getInHouseGuests().size());


        for(Reservation res : reservation)
            adapter.checkOut(res);

        assertEquals(0, adapter.getAll().size());

        assertEquals(0, adapter.getInHouseGuests().size());

        assertEquals(100, adapter.getPast().size());

        ArrayList<Reservation> pastres = adapter.getPast();

        for(Reservation res : reservation)
            if(!pastres.contains(res))
                fail("reservation missing");

    }

}
