package io.lightfeather.models;

import io.lightfeather.metas.TicketStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class KanbanTicket {

    @Id
    private Long id;

    private String description;

    private String user;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDate creationDate;

    public KanbanTicket() {
    }


    public KanbanTicket(String description, TicketStatus status, String user) {
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public KanbanTicket(String csvKanbanTicket) throws Exception {
        String[] fields = csvKanbanTicket.split(",");
        id = Long.valueOf(fields[0]);
        description = fields[1];
        status = TicketStatus.valueOf(fields[2]);
        String[] dateFields = fields[3].split("-");
        creationDate = LocalDate.of(Integer.valueOf(dateFields[0]), Integer.valueOf(dateFields[1]), Integer.valueOf(dateFields[2]));
        user = fields[4];
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getCreationDateString() {
        return creationDate.getMonth().getValue() + "/" + creationDate.getDayOfMonth() + "/" + creationDate.getYear();
    }

    @Override
    public String toString() {
        return id + "," + description + "," + status + "," + creationDate + "," + user;
    }
}
