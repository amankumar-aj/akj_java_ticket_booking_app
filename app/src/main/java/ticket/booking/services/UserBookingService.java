package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {
    private List<User> userList;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService() throws IOException {
        loadUsers();
    }

    //  Loading user from json and adding to userList
    public List<User> loadUsers() throws IOException {
        File users = new File(USER_PATH);
        if (!users.exists()) {
            users.getParentFile().mkdirs();
            users.createNewFile();
            objectMapper.writeValue(users, List.of());
        }
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        return userList;
    }

    //  Saving userList to users.json
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    // Register user
    public void registerUser(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        if (userList.stream().anyMatch(u -> u.getName().equalsIgnoreCase(name))) {
            System.out.println("❌ User already exists. Please try logging in.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        String hashedPassword = UserServiceUtil.hashPassword(password);
        String userId = "USER" + System.currentTimeMillis();

        User newUser = new User(userId, name, hashedPassword);
        try {
            userList.add(newUser);
            saveUserListToFile();
            System.out.println("✅ Registration successful.");
        } catch (IOException ex) {
            System.out.println("❌ Failed to save user data.");
        }
    }

    // ✅ Login user and return userId if successful
    public String loginUser(Scanner sc) {
        System.out.print("Enter username: ");
        String name = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Optional<User> foundUser = userList.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name) &&
                        UserServiceUtil.checkPassword(password, user.getHashedPassword()))
                .findFirst();

        return foundUser.map(User::getUserId).orElse(null);
    }

    //  Fetch bookings for the logged-in user
    public void fetchBookings(Scanner sc, String userId) {
        Optional<User> user = userList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (user.isPresent()) {
            user.get().printTickets();
        } else {
            System.out.println("❌ User not found.");
        }
    }

    // 🚧 Placeholder for train search
    public void searchTrain(Scanner sc) {
        System.out.println("🚧 Train search functionality is under construction.");
        // Add logic to load trains.json and filter/search trains
    }

    // 🚧 Placeholder for booking tickets
    public void bookTicket(Scanner sc, String userId) {
        System.out.println("🚧 Ticket booking functionality is under construction.");
        // Later: collect source, destination, trainId, create Ticket object, add to user
    }
    // canceling tickets
    public void cancelTicket(Scanner sc, String userId) {
        System.out.print("Enter Ticket ID to cancel: ");
        String ticketId = sc.nextLine();

        Optional<User> user = userList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (user.isEmpty()) {
            System.out.println("❌ User not found.");
            return;
        }

        boolean removed = user.get().cancelTicket(ticketId);
        if (removed) {
            try {
                saveUserListToFile();
                System.out.println("✅ Ticket cancelled successfully.");
            } catch (IOException e) {
                System.out.println("❌ Failed to update tickets.");
            }
        } else {
            System.out.println("❌ Ticket not found.");
        }
    }
}
