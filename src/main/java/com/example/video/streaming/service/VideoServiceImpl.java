package com.example.video.streaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.example.video.streaming.entity.VideoFile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private ReactiveGridFsTemplate gridFsTemplate;

	@Autowired
	private org.springframework.data.mongodb.repository.ReactiveMongoRepository<VideoFile, String> metadataRepo;

	@Override
	public Mono<VideoFile> uploadVideo(FilePart filePart) {

		return gridFsTemplate.store(filePart.content(), filePart.filename(), "video/mp4").flatMap(fileId -> {

			VideoFile metadata = new VideoFile();
			metadata.setFilename(filePart.filename());
			metadata.setContentType("video/mp4");
			metadata.setGridFsId(fileId.toHexString());
			metadata.setSize(null); // can calculate if needed

			return metadataRepo.save(metadata);
		});
	}

	@Override
	public Flux<VideoFile> listVideos() {
		return metadataRepo.findAll();
	}

	/*
	 * @Override public Mono<Flux<DataBuffer>> getVideoStream(String filename,
	 * String gridFsId) {
	 * 
	 * Query query = Query.query( Criteria.where("_id").is(new ObjectId(gridFsId))
	 * .and("filename").is(filename) );
	 * 
	 * return gridFsTemplate.findOne(query) .flatMap(resource ->
	 * gridFsTemplate.getResource(resource).getDownloadStream()); }
	 */
}