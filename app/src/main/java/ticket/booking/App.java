package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.services.TrainServices;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Welcome to AJ's E-Ticketing System ===");

        Scanner sc = new Scanner(System.in);
        int option = 0;

        TrainServices trainServices;
        UserBookingService userBookingService;

        try {
            trainServices = new TrainServices();
            userBookingService = new UserBookingService(trainServices, new ArrayList<>()); // ✅ Fixed argument compatibility
        } catch (IOException ex) {
            System.out.println("Error loading data files. Exiting...");
            return;
        }

        while (option != 7) {
            System.out.println("\n1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book Ticket");
            System.out.println("6. Cancel Ticket");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (option) {
                case 1 -> {
                    System.out.print("Enter name: ");
                    String signUpName = sc.nextLine();
                    System.out.print("Enter password: ");
                    String signUpPassword = sc.nextLine();

                    User newUser = new User();
                    newUser.setName(signUpName);
                    newUser.setPassword(signUpPassword);
                    newUser.setUserId(UUID.randomUUID().toString());
                    newUser.setHashedPassword(UserServiceUtil.hashPassword(signUpPassword));

                    if (userBookingService.signUp(newUser)) {
                        System.out.println(" Sign-up successful!");
                    } else {
                        System.out.println(" Sign-up failed. User may already exist.");
                    }
                }

                case 2 -> {
                    System.out.print("Enter name: ");
                    String loginName = sc.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = sc.nextLine();

                    User loginUser = new User();
                    loginUser.setName(loginName);
                    loginUser.setPassword(loginPassword);

                    if (userBookingService.loginUser(loginUser)) {
                        System.out.println(" Login successful!");
                    } else {
                        System.out.println(" Login failed. Invalid credentials.");
                    }
                }

                case 3 -> userBookingService.fetchBooking();

                case 4 -> {
                    System.out.print("Enter source station: ");
                    String src = sc.nextLine();
                    System.out.print("Enter destination station: ");
                    String dst = sc.nextLine();

                    List<Train> foundTrains = trainServices.searchTrains(src, dst);
                    if (foundTrains.isEmpty()) {
                        System.out.println(" No trains found for this route.");
                    } else {
                        System.out.println(" Available Trains:");
                        foundTrains.forEach(train -> System.out.println(train.getTrainInfo()));
                    }
                }

                case 5 -> userBookingService.bookTicket(sc);

                case 6 -> {
                    System.out.print("Enter Ticket ID to cancel: ");
                    String cancelId = sc.nextLine();
                    userBookingService.cancelBooking(cancelId);
                }

                case 7 -> System.out.println(" Exiting... Thank you for using AJ's E-Ticketing System!");

                default -> System.out.println("❗ Invalid option. Try again.");
            }
        }

        sc.close();
    }
}