package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainServices {
    private static final String TRAIN_PATH = "app/src/main/java/ticket/booking/localDb/trains.json";
    private final List<Train> trains;

    public TrainServices() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(TRAIN_PATH);
        if (!file.exists()) {
            this.trains = new ArrayList<>();
        } else {
            this.trains = mapper.readValue(file, new TypeReference<List<Train>>() {});
        }
    }

    public List<Train> searchTrains(String src, String dst) {
        List<Train> found = new ArrayList<>();

        for (Train train : trains) {
            List<String> stations = train.getStations();
            if (stations.contains(src) && stations.contains(dst)) {
                int srcIndex = stations.indexOf(src);
                int dstIndex = stations.indexOf(dst);

                if (srcIndex < dstIndex) { // ✅ Fix: Ensures correct route order
                    found.add(train);
                }
            }
        }

        return found.isEmpty() ? null : found; // ✅ Fix: Returns null if no trains found
    }
    public Train getTrainById(String id) {
        for (Train train : trains) {
            if (train.getTrainId().equalsIgnoreCase(id)) return train;
        }
        System.out.println("❌ Error: Train ID not found!");
        return null; // ✅ Fix: Returns null instead of breaking flow
    }
}