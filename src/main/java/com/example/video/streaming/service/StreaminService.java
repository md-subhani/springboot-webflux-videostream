package com.example.video.streaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class StreaminService {

	private static final String FORMAT = "classpath:video/%s.mp4";

	@Autowired
	private ResourceLoader loader;

	public Mono<Resource> streamVideo(String title) {
		return Mono.fromSupplier(() -> loader.getResource(String.format(FORMAT, title)));

	}

}
