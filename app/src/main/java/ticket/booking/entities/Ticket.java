package ticket.booking.entities;

public class Ticket {
    private String ticketId;
    private String userId;
    private String name;
    private String source;
    private String destination;
    private String dateOfTravel;
    private Train train;


    public Ticket() {}


    public Ticket(String ticketId, String name, String userId, String source,
                  String destination, String dateOfTravel, Train train) {
        this.ticketId = ticketId;
        this.name = name;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    // Ticket Details
    public String getTicketInfo() {
        return String.format(
                "===== IRCTC TICKET DETAILS =====\n" +
                        "Ticket ID       : %s\n" +
                        "User ID         : %s\n" +
                        "Passenger Name  : %s\n" +
                        "Source          : %s\n" +
                        "Destination     : %s\n" +
                        "Date of Travel  : %s\n" +
                        "Train Details   : %s\n",
                ticketId,
                userId,
                name,
                source,
                destination,
                dateOfTravel,
                (train != null) ? train.toString() : "N/A"
        );
    }

    // === Getters ===
    public String getTicketId() {
        return ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public Train getTrain() {
        return train;
    }

    // === Setters ===
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
//gpt fix --aj
    // Optional: useful if train.toString() isn't overridden properly
    @Override
    public String toString() {
        return getTicketInfo();
    }
}
