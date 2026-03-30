package com.example.video.streaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.video.streaming.service.StreaminService;

import reactor.core.publisher.Mono;

@RestController
public class StreamingController {

	@Autowired
	private StreaminService service;

	@GetMapping(value = "/streaming/{title}", produces = "video/mp4")
	public Mono<Resource> streamVideo(@PathVariable String title, @RequestHeader("Range") String range) {
		 System.out.println("range in bytes() : " + range);
		return service.streamVideo(title);
	}

}
