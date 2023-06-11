package com.sse.publisher.repositories;

import com.sse.publisher.constants.MongoConstants;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import com.sse.publisher.models.EventSettingsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class EventSettingsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventSettingsRepository.class);

    private final MongoTemplate mongoTemplate;

    public EventSettingsRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void upsert(final EventSettingsModel eventSettingsModel){

        final OffsetDateTime timeNow = OffsetDateTime.now();

        final Query updateCondition = Query.query(Criteria.where(MongoConstants.ID).is(eventSettingsModel.getAlias()));
        final UpdateDefinition updateDefinition = Update.update(MongoConstants.ID, eventSettingsModel.getAlias())
                .set(MongoConstants.ROUTING_KEY, eventSettingsModel.getRoutingKey())
                .set(MongoConstants.DESCRIPTION, eventSettingsModel.getDescription())
                .set(MongoConstants.UPDATED_AT, timeNow)
                .setOnInsert(MongoConstants.CREATED_AT, timeNow);

        try{
            mongoTemplate.upsert(updateCondition, updateDefinition, MongoConstants.SETTINGS_COLLECTION_NAME);
        }catch (Exception e){
           throw  GlobalException.getBuilder(LOGGER)
                   .setExceptionType(ExceptionType.DATABASE_ERROR)
                   .setFormattedErrorMessage(e.getMessage())
                   .setDetailedError(e);
        }
    }

    public EventSettingsModel getByAlias(final String alias){
        try{
            return mongoTemplate.findById(alias,EventSettingsModel.class, MongoConstants.SETTINGS_COLLECTION_NAME);
        }catch (Exception e){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.DATABASE_ERROR)
                    .setFormattedErrorMessage(e.getMessage())
                    .setDetailedError(e);
        }
    }

    public List<EventSettingsModel> getAll(){
        try{
            return mongoTemplate.findAll(EventSettingsModel.class,  MongoConstants.SETTINGS_COLLECTION_NAME);
        }catch (Exception e){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.DATABASE_ERROR)
                    .setFormattedErrorMessage(e.getMessage())
                    .setDetailedError(e);
        }

    }



}
