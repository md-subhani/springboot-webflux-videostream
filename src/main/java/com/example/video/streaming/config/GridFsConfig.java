package com.example.video.streaming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;

@Configuration
public class GridFsConfig {

    @Bean
    public ReactiveGridFsTemplate reactiveGridFsTemplate(
            ReactiveMongoDatabaseFactory dbFactory,
            MappingMongoConverter converter) {
        return new ReactiveGridFsTemplate(dbFactory, converter);
    }
}