package ticket.booking.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TicketUtil {

    private static final String TICKET_FILE = "tickets.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String generateTicketId() {
        String random = String.format("%06d", new Random().nextInt(1_000_000));
        return "AJ" + random + "000000";
    }

    public static void saveTicket(Ticket ticket) throws IOException {
        List<Ticket> existingTickets = loadAllTickets();
        existingTickets.add(ticket);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(TICKET_FILE), existingTickets);
    }

    public static List<Ticket> loadAllTickets() {
        File file = new File(TICKET_FILE);
        if (!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<List<Ticket>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ✅ FIX: **Added missing `createTicket()` method**
    public static Ticket createTicket(User user, String source, String destination, String travelDate, Train train) {
        if (user == null) {
            System.out.println("⚠ Error: User must be logged in to book a ticket.");
            return null;
        }

        Ticket ticket = new Ticket(generateTicketId(), user.getName(), user.getUserId(),
                source, destination, travelDate, train);

        try {
            saveTicket(ticket);
        } catch (IOException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
            return null;
        }

        return ticket;
    }

    public static boolean cancelTicket(String ticketId, User user) {
        if (user == null) {
            System.out.println("⚠ Error: User must be logged in to cancel a ticket.");
            return false;
        }

        List<Ticket> tickets = loadAllTickets();
        Optional<Ticket> optionalTicket = tickets.stream()
                .filter(t -> t.getTicketId().equalsIgnoreCase(ticketId))
                .findFirst();

        if (optionalTicket.isEmpty()) {
            System.out.println("❌ Ticket not found.");
            return false;
        }

        Ticket ticketToCancel = optionalTicket.get();
        user.getTicketsBooked().removeIf(t -> t.getTicketId().equalsIgnoreCase(ticketId));
        tickets.removeIf(t -> t.getTicketId().equalsIgnoreCase(ticketId));

        try {
            // ✅ Fix: Save updated ticket list after cancellation
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(TICKET_FILE), tickets);
            System.out.println("✅ Ticket successfully canceled.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}