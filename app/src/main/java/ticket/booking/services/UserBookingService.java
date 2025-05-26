package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.util.List;

public class UserBookingService {
    private User user; //global user
    private List<User> userList;
    private ObjectMapper objectMapper=new ObjectMapper();
    private static final String USER_PATH="../localDb/users.json";
    public UserBookingService(User user1){
        this.user=user1;
        File users= new File(USER_PATH);
/**
 * Ye line Jackson ObjectMapper ka use karke "users" JSON data ko
 * List<User> Java object me convert karti hai.
 *
 * Breakdown:
 *
 * 🔹 objectMapper:
 *     - ObjectMapper class ka object hai (Jackson library ka part).
 *     - Iska kaam hai: JSON ↔ Java object conversion (serialize/deserialize).
 *
 * 🔹 readValue(...):
 *     - Ye method JSON string/file ko Java object me convert karta hai.
 *     - Format: readValue(jsonData, Type)
 *
 * 🔹 users:
 *     - Ye JSON source hai (String, File, InputStream, ya Reader).
 *     - Aapke case me ho sakta hai: new File("path/to/users.json");
 *
 * 🔹 new TypeReference<List<User>>() {}:
 *     - Java me generics ka runtime me type erase ho jata hai.
 *     - Isliye List<User> jaise complex type ko batane ke liye
 *       TypeReference ka use hota hai.
 *     - Jackson ko batata hai ki:
 *       "Is JSON ko List<User> type me convert karo"
 *
 * Example:
 * List<User> userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
 */

        // File users.json ka data read karo aur usse List<User> me convert karo
        userList =objectMapper.readValue(users,
                new TypeReference<List<User>>() {// target type: List of User objects
        });
    }
}
