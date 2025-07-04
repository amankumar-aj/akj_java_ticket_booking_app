package ticket.booking.util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    public static String hashPassword(String simplePassword) {
        return BCrypt.hashpw(simplePassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String simplePassword, String hashedPassword) {
        return BCrypt.checkpw(simplePassword, hashedPassword);
    }
}