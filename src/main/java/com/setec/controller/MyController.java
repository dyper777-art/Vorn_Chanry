package com.setec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.setec.entities.Booked;
import com.setec.repos.BookedRepo;
import com.setec.telegrambot.MyTelegramBot;

@Controller
public class MyController {

    @Autowired
    private BookedRepo bookedRepo;

    @Autowired
    private MyTelegramBot bot;

    // Default booking values, customizable via environment variables
    @Value("${booking.default.name:chouengsombo}")
    private String defaultName;

    @Value("${booking.default.phone:010 0000 0000}")
    private String defaultPhone;

    @Value("${booking.default.email:chouengsombo@gmail.com}")
    private String defaultEmail;

    @Value("${booking.default.date:YYYY/MM/DD}")
    private String defaultDate;

    @Value("${booking.default.time:HH:MM AM/PM}")
    private String defaultTime;

    @Value("${booking.default.persons:1}")
    private int defaultPersons;

    // Helper method to create a default Booked object
    private Booked createDefaultBooking() {
        return new Booked(1, defaultName, defaultPhone, defaultEmail, defaultDate, defaultTime, defaultPersons);
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("booked", createDefaultBooking());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/service")
    public String service() {
        return "service";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/reservation")
    public String reservation(Model model) {
        // Customize reservation page booking (example)
        Booked reservationBooking = createDefaultBooking();

        model.addAttribute("booked", reservationBooking);
        return "reservation";
    }

    @GetMapping("/testimonial")
    public String testimonial() {
        return "testimonial";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/success")
    public String success(@ModelAttribute Booked booked) {
        // Save booking to database
        bookedRepo.save(booked);

        // Send Telegram message
        bot.message(
            "🆔 ID       : " + booked.getId() + "\n" +
            "👤 Name     : " + booked.getName() + "\n" +
            "📧 Email    : " + booked.getEmail() + "\n" +
            "📞 Phone    : " + booked.getPhoneNumber() + "\n" +
            "📅 Date     : " + booked.getDate() + "\n" +
            "⏰ Time     : " + booked.getTime() + "\n" +
            "👥 Persons  : " + booked.getPerson()
        );

        return "success";
    }
}
