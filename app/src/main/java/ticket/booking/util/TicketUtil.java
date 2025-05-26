package ticket.booking.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ticket.booking.entities.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TicketUtil {

    private static final String TICKET_FILE_PATH = "app/src/main/java/ticket/booking/localDb/tickets.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTicketsToFile(List<Ticket> ticketList) {
        try (FileWriter writer = new FileWriter(TICKET_FILE_PATH)) {
            gson.toJson(ticketList, writer);
            System.out.println("✅ Tickets saved successfully to: " + TICKET_FILE_PATH);
        } catch (IOException e) {
            System.err.println("❌ Error saving tickets: " + e.getMessage());
        }
    }
}
/*help by gpt needs to resolve conflict causing by this file with other classes-[@amankumar_aj]*/