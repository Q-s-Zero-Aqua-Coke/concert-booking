package org.example.concertbooking.controller;

import org.example.concertbooking.model.dto.ConcertDTO;
import org.example.concertbooking.model.dto.TicketDTO;
import org.example.concertbooking.model.dto.UserDTO;
import org.example.concertbooking.model.repository.ConcertRepository;
import org.example.concertbooking.model.repository.TicketRepository;
import org.example.concertbooking.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class IndexController {
    final private UserRepository userRepository;
    final private ConcertRepository concertRepository;
    final private Logger logger = Logger.getLogger(String.valueOf(IndexController.class));
    private final TicketRepository ticketRepository;

    public IndexController(UserRepository userRepository, ConcertRepository concertRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.concertRepository = concertRepository;
        this.ticketRepository = ticketRepository;
    }

    @RequestMapping("/")
    String index(Model model) throws Exception {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("concerts", concertRepository.findAll());
        model.addAttribute("tickets", ticketRepository.findAll());
        return "index";
    }

    @PostMapping("/user")
    String addUser(@ModelAttribute UserDTO userDTO) throws Exception {
        logger.info(userDTO.toString());
        userRepository.save(new UserDTO(
                UUID.randomUUID().toString(),
                userDTO.name(),
                userDTO.email(),
                userDTO.phone()
        ));
        return "redirect:/";
    }

    @PostMapping("/concert")
    String addConcert(@ModelAttribute ConcertDTO concertDTO) throws Exception {
        logger.info(concertDTO.toString());
        concertRepository.save(new ConcertDTO(
                UUID.randomUUID().toString(),
                concertDTO.title(),
                concertDTO.date(),
                concertDTO.location()
        ));
        return "redirect:/";
    }

    @PostMapping("/ticket")
    String addConcert(@ModelAttribute TicketDTO ticketDTO) throws Exception {
        logger.info(ticketDTO.toString());
        if(concertRepository.findById(ticketDTO.concert_id()) == null)
            throw new Exception("Cannot find concert");

        ticketRepository.save(new TicketDTO(
                UUID.randomUUID().toString(),
                ticketDTO.seat_number(),
                ticketDTO.price(),
                ticketDTO.purchase_date(),
                ticketDTO.concert_id(),
                ticketDTO.user_id()
        ));
        return "redirect:/";
    }
}
