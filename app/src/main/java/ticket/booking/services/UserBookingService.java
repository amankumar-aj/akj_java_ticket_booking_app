package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.TicketUtil;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {
    private static final String USER_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TrainServices trainServices;
    private List<User> userList;
    private User user;

    public UserBookingService(TrainServices trainServices, List<User> userList) throws IOException {
        this.trainServices = trainServices;
        this.userList = userList != null ? userList : new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() throws IOException {
        File file = new File(USER_PATH);
        if (!file.exists()) return;
        userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    private void saveUsers() throws IOException {
        objectMapper.writeValue(new File(USER_PATH), userList);
    }

    public boolean signUp(User newUser) {
        try {
            userList.add(newUser);
            saveUsers();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
//Booking
    public Ticket bookTicket(Scanner scanner) {
        if (user == null) {
            System.out.println("⚠ Please login to book tickets.");
            return null;
        }

        System.out.print("Enter source station: ");
        String source = scanner.nextLine().trim();
        System.out.print("Enter destination station: ");
        String destination = scanner.nextLine().trim();

        List<Train> foundTrains = trainServices.searchTrains(source, destination);
        if (foundTrains.isEmpty()) {
            System.out.println(" No trains available for the selected route.");
            return null;
        }

        System.out.println("\nAvailable Trains:");
        foundTrains.forEach(train -> System.out.println(train.getTrainInfo()));

        System.out.print("\nEnter Train ID to book: ");
        String selectedTrainId = scanner.nextLine().trim();

        Train selectedTrain = trainServices.getTrainById(selectedTrainId);
        if (selectedTrain == null) {
            System.out.println("❌ Invalid Train ID.");
            return null;
        }

        System.out.print("Enter travel date (yyyy-mm-dd): ");
        String travelDate = scanner.nextLine().trim();

        Ticket ticket = TicketUtil.createTicket(user, source, destination, travelDate, selectedTrain);
        if (ticket != null) {
            user.getTicketsBooked().add(ticket); // ✅ FIX: Ticket now gets stored properly in user object
            try {
                saveUsers(); // ✅ FIX: Ensure user profile gets updated
                System.out.println("✅ Ticket Booked Successfully!");
                System.out.println(ticket.getTicketInfo());
            } catch (IOException e) {
                System.out.println("⚠ Error saving user after booking: " + e.getMessage());
            }
        }

        return ticket;
    }
//cancel Booking
public boolean cancelBooking(String ticketId) {
    if (user == null) {
        System.out.println("⚠ Please login to cancel tickets.");
        return false;
    }

    boolean removedFromUser = user.getTicketsBooked().removeIf(t -> t.getTicketId().equalsIgnoreCase(ticketId)); // ✅ Ensure ticket is removed from user object

    if (!removedFromUser) {
        System.out.println("❌ Ticket not found in user profile.");
        return false;
    }

    try {
        saveUsers(); // ✅ Fix: Save user profile after ticket removal

        boolean removedFromGlobal = TicketUtil.cancelTicket(ticketId, user); // ✅ Ensure global removal also happens
        if (removedFromGlobal) {
            System.out.println("✅ Ticket successfully removed from user profile and system.");
            return true;
        } else {
            System.out.println("⚠ Ticket removed from user but still exists in global storage.");
        }
    } catch (IOException e) {
        System.out.println("⚠ Error updating user profile: " + e.getMessage());
    }

    return false;
}
    //login
    public boolean loginUser(User loginAttempt) {
        for (User u : userList) {
            if (u.getName().equalsIgnoreCase(loginAttempt.getName())
                    && UserServiceUtil.checkPassword(loginAttempt.getPassword(), u.getHashedPassword())) {
                this.user = u;
                return true;
            }
        }
        return false;
    }
//fetch booking
    public void fetchBooking() {
        if (user == null || user.getTicketsBooked().isEmpty()) {
            System.out.println("📭 No tickets found.");
        } else {
            user.printTickets();
        }
    }
}