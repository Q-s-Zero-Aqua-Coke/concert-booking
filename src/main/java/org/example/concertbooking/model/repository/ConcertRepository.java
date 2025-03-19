package org.example.concertbooking.model.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.concertbooking.model.dto.ConcertDTO;
import org.example.concertbooking.model.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertRepository implements SupabaseRepository<ConcertDTO> {
    final private String tableName = "concert_concert";

    public void save(ConcertDTO dto) throws Exception {
        save(dto, tableName);
    }

    public List<ConcertDTO> findAll() throws Exception {
        String responseJson = findAll(tableName);
        return mapper.readValue(responseJson, new TypeReference<>() {});
    }

    public ConcertDTO findById(String concertId) throws Exception {
        String responseJson = findById(concertId, tableName, "concert_id");
        List<ConcertDTO> concertDTOList = mapper.readValue(responseJson, new TypeReference<>() {});
        if(!concertDTOList.isEmpty())
            return concertDTOList.get(0);
        return null;
    }
}
