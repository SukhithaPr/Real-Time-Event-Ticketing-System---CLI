import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<Ticket> ticketQueue;
    private int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumCapacity ) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        this.ticketQueue.add(ticket);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has added a ticket to the pool. Current size is " + ticketQueue.size());
    }

    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        Ticket ticket = ticketQueue.poll();
        notifyAll();
        System.out.println(Thread.currentThread().getName() + "has bought a ticket from the pool. Current size is " + ticketQueue.size() + " Ticket is " + ticket);

        return ticket;
    }


}
