package io.lightfeather.repositories;

import io.lightfeather.models.KanbanTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanTicketJPADatabase extends JpaRepository<KanbanTicket, Long> {
}
