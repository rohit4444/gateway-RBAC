package com.el2.ordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emailService")
public class EmailController {

	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping("/sendemail")
	public void sendEmail1() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("rohit.jadhav@zgcns.com");
		message.setSubject("hi test");
		message.setText("test test");
		javaMailSender.send(message);
		System.out.println("Email sent successfully!");
	}

}
