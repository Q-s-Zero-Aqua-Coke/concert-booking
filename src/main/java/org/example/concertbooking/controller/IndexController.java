package org.example.concertbooking.controller;

import org.example.concertbooking.model.dto.UserDTO;
import org.example.concertbooking.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    final private UserRepository userRepository;

    public IndexController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @PostMapping("/user")
    String addUser(UserDTO userDTO) throws Exception {
        userRepository.save(userDTO);
        return "redirect:/";
    }
}
