import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<Ticket> ticketList;
    private final int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        this.ticketList = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void addTicket(Ticket ticket) {
        while (ticketList.size() >= maximumCapacity) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting to add ticket. Pool is full.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
                System.err.println("Thread interrupted while adding ticket: " + e.getMessage());
                return;
            }
        }

        ticketList.add(ticket);
        System.out.println(Thread.currentThread().getName() + " added a ticket. Pool size: " + ticketList.size());
        notifyAll(); // Notify waiting threads
    }

    public synchronized Ticket buyTicket() {
        while (ticketList.isEmpty()) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting to buy ticket. Pool is empty.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
                System.err.println("Thread interrupted while buying ticket: " + e.getMessage());
                return null;
            }
        }

        Ticket ticket = ticketList.remove(0);
        System.out.println(Thread.currentThread().getName() + " bought ticket: " + ticket);
        notifyAll(); // Notify waiting threads
        return ticket;
    }

    public synchronized int getCurrentSize() {
        return ticketList.size();
    }
}
