import java.math.BigDecimal;

public class Vendor implements Runnable{
    private TicketPool ticketPool;
    private int totalTicket;
    private int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int totalTicket, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTicket = totalTicket;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTicket; i++) {
            Ticket ticket = new Ticket(i, "Spandana", new BigDecimal("2000"));
            ticketPool.addTicket(ticket);

            try {
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }


}
