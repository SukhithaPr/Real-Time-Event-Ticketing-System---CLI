import java.math.BigDecimal;

public class Ticket {
    private int ticketid;
    private String eventName;
    private BigDecimal ticketPrice;

    public Ticket(int ticketid, String eventName, BigDecimal ticketPrice) {
        this.ticketid = ticketid;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "( " + "Ticket Id: " +
                ticketid + " | " + eventName + ", " + "Rs."+ticketPrice + " )";
    }
}
