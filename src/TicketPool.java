import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private List<Ticket> ticketList;
    private int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        this.ticketList = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void addTicket(Ticket ticket) {
        while (ticketList.size() >= maximumCapacity ) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        this.ticketList.add(ticket);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has added a ticket to the pool. Current size is " + ticketList.size());
    }

    public synchronized Ticket buyTicket() {
        while (ticketList.isEmpty()) {
            try {
                wait();
                System.out.println(".");
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        Ticket ticket = ticketList.remove(0);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has bought a ticket. "+ ticket + " - Current size is " + ticketList.size());

        return ticket;
    }


}
