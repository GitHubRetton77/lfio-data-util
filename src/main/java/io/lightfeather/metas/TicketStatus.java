package io.lightfeather.metas;

public enum TicketStatus {

    BACKLOG("Backlog"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Complete");

    public static TicketStatus fromStringForm(String stringForm) throws Exception {
        for(TicketStatus ticketStatus: TicketStatus.values()){
            if (ticketStatus.stringForm.equals(stringForm)){
                return ticketStatus;
            }
        }
        throw new Exception("Ticket status string form provided is unknown");
    }

    private String stringForm;

    TicketStatus(String stringForm){
        this.stringForm = stringForm;
    }

    public String getStringForm(){
        return this.stringForm;
    }

}
