package com.demo.kafkademo.controller;

import com.demo.kafkademo.common.AppConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kafkademo.model.User;
import com.demo.kafkademo.service.KafKaProducerService;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerController {
	private final KafKaProducerService producerService;

	@Autowired
	public KafkaProducerController(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.producerService.sendMessage(message);
	}
	
	@PostMapping(value = "/createUser")
	public void sendMessageToKafkaTopic(
			@RequestParam("userId") long userId, 
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {
		
		User user = new User();
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		this.producerService.saveCreateUserLog(user);
	}

	@Bean
	public NewTopic registerMessageTopic() {
		return new NewTopic(AppConstants.TOPIC_CUSTOM_MESSAGE, 3, (short)1);
	}

	@Bean
	public NewTopic registerUserLogTopic() {
		return new NewTopic(AppConstants.TOPIC_NAME_USER_LOG, 3, (short)1);
	}
}