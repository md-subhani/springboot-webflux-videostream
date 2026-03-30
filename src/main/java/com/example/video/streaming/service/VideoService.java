package com.example.video.streaming.service;

import org.springframework.http.codec.multipart.FilePart;

import com.example.video.streaming.entity.VideoFile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoService {

	Mono<VideoFile> uploadVideo(FilePart filePart);

	Flux<VideoFile> listVideos();

}