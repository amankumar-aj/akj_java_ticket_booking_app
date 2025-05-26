package ticket.booking;

import ticket.booking.services.UserBookingService;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting aj's E-Ticketing System✅");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        String loggedInUserId = null;

        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            System.out.println("❌ Initialization failed.");
            return;
        }

        while (option != 7) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Fetch My Bookings");
            System.out.println("4. Search Train");
            System.out.println("5. Book Ticket");
            System.out.println("6. Cancel Ticket");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                continue;
            }

            switch (option) {
                case 1:
                    userBookingService.registerUser(sc);
                    break;
                case 2:
                    loggedInUserId = userBookingService.loginUser(sc);
                    if (loggedInUserId != null) {
                        System.out.println("✅ Login successful.");
                    } else {
                        System.out.println("❌ Login failed.");
                    }
                    break;
                case 3:
                    if (checkLogin(loggedInUserId)) {
                        userBookingService.fetchBookings(sc, loggedInUserId);
                    }
                    break;
                case 4:
                    userBookingService.searchTrain(sc);
                    break;
                case 5:
                    if (checkLogin(loggedInUserId)) {
                        userBookingService.bookTicket(sc, loggedInUserId);
                    }
                    break;
                case 6:
                    if (checkLogin(loggedInUserId)) {
                        userBookingService.cancelTicket(sc, loggedInUserId);
                    }
                    break;
                case 7:
                    System.out.println("👋 Thank you for using aj's IRCTC.");
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    private static boolean checkLogin(String loggedInUserId) {
        if (loggedInUserId == null) {
            System.out.println("⚠️ Please login first to use this feature.");
            return false;
        }
        return true;
    }
}
