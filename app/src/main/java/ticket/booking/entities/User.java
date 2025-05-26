package ticket.booking.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private String name;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;
    private String userId;


    public User(String userId, String name, String hashedPassword) {
        this.userId = userId;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = new ArrayList<>();
    }


    public User() {
        this.ticketsBooked = new ArrayList<>();
    }

    // getters-----
    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public String getUserId() {
        return userId;
    }

    // setters----
    public void setName(String name) {
        this.name = name;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // TicketDetails
    public void printTickets() {
        if (ticketsBooked == null || ticketsBooked.isEmpty()) {
            System.out.println("ℹ️ No tickets booked.");
            return;
        }

        for (Ticket ticket : ticketsBooked) {
            System.out.println(ticket.getTicketInfo());
        }
    }

    // cancell ticked using ticketId
    public boolean cancelTicket(String ticketId) {
        if (ticketsBooked == null) return false;

        Iterator<Ticket> iterator = ticketsBooked.iterator();
        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();
            if (ticket.getTicketId().equalsIgnoreCase(ticketId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
