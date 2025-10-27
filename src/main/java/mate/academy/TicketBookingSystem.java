package mate.academy;

import java.util.concurrent.Semaphore;

public class TicketBookingSystem extends Thread {
    private int totalSeats;
    private final Semaphore semaphore = new Semaphore(1);

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public BookingResult attemptBooking(String user) {
        try {
            semaphore.acquire();
            if (totalSeats > 0) {
                BookingResult bookingResult =
                        new BookingResult(user, true, "Booking successful.");
                totalSeats = totalSeats - 1;
                return bookingResult;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
        return new BookingResult(user, false, "No seats available.");
    }
}
