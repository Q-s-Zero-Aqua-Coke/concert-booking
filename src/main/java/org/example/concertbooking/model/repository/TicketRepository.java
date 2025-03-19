package org.example.concertbooking.model.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.concertbooking.model.dto.ConcertDTO;
import org.example.concertbooking.model.dto.TicketDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepository implements SupabaseRepository<TicketDTO> {
    final private String tableName = "concert_ticket";

    public void save(TicketDTO dto) throws Exception {
        save(dto, tableName);
    }

    public List<TicketDTO> findAll() throws Exception {
        String responseJson = findAll(tableName);
        return mapper.readValue(responseJson, new TypeReference<>() {});
    }

    public TicketDTO findById(String ticketId) throws Exception {
        String responseJson = findById(ticketId, tableName, "ticket_id");
        return mapper.readValue(responseJson, new TypeReference<>() {});
    }
}
