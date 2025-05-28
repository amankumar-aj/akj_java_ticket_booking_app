package ticket.booking.entities;

public class Ticket {
    private String ticketId;
    private String userId;
    private String name;
    private String source;
    private String destination;
    private String dateOfTravel;
    private Train train;
    private String ticketInfo;

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
        this.ticketInfo = buildTicketInfo();
    }

    private String buildTicketInfo() {
        return "===== TICKET DETAILS =====\n"
                + "Ticket ID       : " + ticketId + "\n"
                + "User ID         : " + userId + "\n"
                + "Passenger Name  : " + name + "\n"
                + "Source          : " + source + "\n"
                + "Destination     : " + destination + "\n"
                + "Date of Travel  : " + dateOfTravel + "\n"
                + "Train Details   : " + train.getTrainInfo() + "\n";
    }

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

    public String getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(String ticketInfo) {
        this.ticketInfo = ticketInfo;
    }
}