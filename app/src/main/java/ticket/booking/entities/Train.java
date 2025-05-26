package ticket.booking.entities;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, LocalTime> stationTimes;
    private List<String> stations;

}
