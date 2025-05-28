package ticket.booking.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TicketUtil {

    private static final String TICKET_FILE = "app/src/main/java/ticket/booking/localDb/tickets.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveTicket(Ticket ticket) throws IOException {
        List<Ticket> existingTickets = loadAllTickets();
        existingTickets.add(ticket); // ✅ Ticket globally store hoga
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
    //cancel

    public static boolean cancelTicket(String ticketId, User user) {
        if (user == null) {
            System.out.println(" Please login to cancel tickets.");
            return false;
        }

        List<Ticket> tickets = loadAllTickets();
        boolean removedFromJson = tickets.removeIf(t -> t.getTicketId().equalsIgnoreCase(ticketId)); // ✅ Ensure removal

        if (!removedFromJson) {
            System.out.println(" Ticket not found in system.");
            return false;
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(TICKET_FILE), tickets); // Fix: Ensure JSON update happens correctly
            System.out.println(" Ticket successfully removed from system.");
            return true;
        } catch (IOException e) {
            System.out.println(" Error updating global ticket storage: " + e.getMessage());
            return false;
        }
    }
//==========
    private static String generateTicketId() {
        String random = String.format("%06d", new Random().nextInt(1_00));
        return "AJ" + random + "000000"; //  Unique Ticket ID generate karega
    }

    public static Ticket createTicket(User user, String source, String destination, String travelDate, Train train) {
        Ticket ticket = new Ticket(generateTicketId(), user.getName(), user.getUserId(), source, destination, travelDate, train);

        try {
            saveTicket(ticket);
        } catch (IOException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
            return null;
        }

        return ticket;
    }
}