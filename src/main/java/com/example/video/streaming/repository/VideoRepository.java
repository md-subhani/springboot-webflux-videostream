package com.example.video.streaming.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.video.streaming.entity.VideoFile;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<VideoFile, String> {
}