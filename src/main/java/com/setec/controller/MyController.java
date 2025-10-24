package com.setec.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping({ "/", "/home" })
	public String home(Model mod) {
		Booked booked = new Booked(1, "chouengsombo", "010 9999 88", "chouengsombo@gmail.com", "09/12/2025", "6:00 PM",
				10);
		mod.addAttribute("booked", booked);
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
	public String reservation(Model mod) {
		Booked booked = new Booked(1, "chouengsombo", "010 3333 44", "chouengsombo@gmail.com", "09/11/2025", "8:30 PM",
				2);
		mod.addAttribute("booked", booked);
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

	@Autowired
	private BookedRepo bookedRepo;
	@Autowired
	private MyTelegramBot bot;

	@PostMapping("/success")
	public String success(@ModelAttribute Booked booked) {
		bookedRepo.save(booked);
		bot.message(""
				+ "🆔 ID       : " + booked.getId() + "\n" 
				+ "👤 Name     : " + booked.getName() + "\n"
				+ "📧 Email    : " + booked.getEmail() + "\n" 
				+ "📞 Phone    : " + booked.getPhoneNumber() + "\n"
				+ "📅 Date     : " + booked.getDate() + "\n" 
				+ "⏰ Time     : " + booked.getTime() + "\n"
				+ "👥 Persons  : " + booked.getPerson()

		);
		return "success";
	}

}
