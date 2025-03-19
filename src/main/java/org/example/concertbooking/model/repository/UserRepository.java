package org.example.concertbooking.model.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.concertbooking.model.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements SupabaseRepository<UserDTO> {
    final private String tableName = "concert_user";

    public void save(UserDTO dto) throws Exception {
        save(dto, tableName);
    }

    public List<UserDTO> findAll() throws Exception {
        String responseJson = findAll(tableName);
        return mapper.readValue(responseJson, new TypeReference<>() {});
    }

    public UserDTO findById(String userId) throws Exception {
        String responseJson = findById(userId, tableName, "user_id");
        return mapper.readValue(responseJson, new TypeReference<>() {});
    }
}
