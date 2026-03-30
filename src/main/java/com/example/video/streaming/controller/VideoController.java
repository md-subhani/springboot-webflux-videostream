package com.example.video.streaming.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.example.video.streaming.entity.VideoFile;
import com.example.video.streaming.service.VideoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	private VideoService videoService;

	@Autowired
	private ReactiveGridFsTemplate gridFsTemplate;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<VideoFile> upload(@RequestPart("file") FilePart filePart) {
		return videoService.uploadVideo(filePart);
	}

	@GetMapping
	public Flux<VideoFile> getAll() {
		return videoService.listVideos();
	}

	/*
	 * @GetMapping(value = "/{id}", produces = "video/mp4") public Mono<VideoFile>
	 * getVideos(@PathVariable String id, @RequestHeader("Range") String range) {
	 * System.out.println("range in bytes() : " + range); return
	 * videoService.getVideo(id); }
	 */

	@GetMapping("/stream/{filename}/{gridFsId}")
	public Mono<ResponseEntity<Flux<DataBuffer>>> stream(@PathVariable String filename, @PathVariable String gridFsId) {

		return getVideoStream(filename, gridFsId)
				.map(stream -> ResponseEntity.ok().contentType(MediaType.valueOf("video/mp4")).body(stream));
	}

	public Mono<Flux<DataBuffer>> getVideoStream(String filename, String gridFsId) {

		Query query = Query.query(Criteria.where("_id").is(new ObjectId(gridFsId)).and("filename").is(filename));

		return gridFsTemplate.findOne(query) // Mono<GridFSFile>
				.flatMap(file -> gridFsTemplate.getResource(file) // Mono<ReactiveGridFsResource>
						.flatMap(resource -> Mono.just(resource.getDownloadStream()) // ✅ Flux<DataBuffer>
						));
	}
}