package com.example.video.streaming.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("videos")
public class VideoFile {
	@Id
	private String id;

	private String filename;
	private String contentType;
	private Long size;
	private String gridFsId;

	public VideoFile() {
		super();
	}

	public VideoFile(String id, String filename, String contentType, Long size, String gridFsId) {
		super();
		this.id = id;
		this.filename = filename;
		this.contentType = contentType;
		this.size = size;
		this.gridFsId = gridFsId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getGridFsId() {
		return gridFsId;
	}

	public void setGridFsId(String gridFsId) {
		this.gridFsId = gridFsId;
	}

}