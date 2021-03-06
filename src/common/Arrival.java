package common;

import java.io.Serializable;

public class Arrival implements Serializable {
    private DateHandler checkInDate;

    /**
     * Constructor initializing common.Arrival
     *
     * @param checkInDate for initializing inside the constructor.
     */

    public Arrival(DateHandler checkInDate){
        this.checkInDate = checkInDate;
    }

    /**
     * Gets the check in date for the reservation.
     *
     * @return common.DateHandler, named checkInDate representing the check in date of the reservation.
     */

    public DateHandler getCheckInDate() {
        return checkInDate;
    }

    /**
     * Replaces the check in date for the reservation
     *
     * @param checkInDate the check in date to replace with.
     */

    public void setCheckInDate(DateHandler checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Gets copy of common.Arrival
     *
     * @return common.DateHandler, named checkInDate which is a copy of common.Arrival.
     */

    public Arrival copy(){
        return new Arrival(checkInDate);
    }

    /**
     * Check if an arrival is equal to another.
     *
     * @param obj Object for comparing
     * @return true or false. If the entire arrival is equal with obj, the method will return true, else it returns false.
     */
    public boolean equals(Object obj) {
        if(!(obj instanceof Arrival)) return false;

        Arrival arrival = (Arrival) obj;

        return checkInDate.equals(arrival.checkInDate);
    }
    /**
     * Returns a String
     *
     * @return String, representing the check in date.
     */

    public String toString() {
        return checkInDate.toString();
    }
}
