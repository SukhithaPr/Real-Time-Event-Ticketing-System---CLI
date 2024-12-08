import java.math.BigDecimal;

public class Vendor implements Runnable{
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int totalTicket, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTicket;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Spandana", new BigDecimal("2000"));
            ticketPool.addTicket(ticket);

            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }


}
