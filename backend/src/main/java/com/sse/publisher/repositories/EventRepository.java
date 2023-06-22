package com.sse.publisher.repositories;

import com.sse.publisher.constants.MongoConstants;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import com.sse.publisher.models.EventDatabaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class EventRepository {


    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepository.class);

    private final MongoTemplate mongoTemplate;

    public EventRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void saveEvent(final EventDatabaseModel event) {
        final OffsetDateTime timeNow = OffsetDateTime.now();
        event.setCreatedAt(timeNow);
        event.setUpdatedAt(timeNow);

        try{
            mongoTemplate.insert(event, MongoConstants.HISTORY_COLLECTION_NAME);
        }catch (Exception e){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.DATABASE_ERROR)
                    .setFormattedErrorMessage(e.getMessage())
                    .setDetailedError(e);
        }
    }

    public List<EventDatabaseModel> getHistory() {

        return mongoTemplate.findAll(EventDatabaseModel.class, MongoConstants.HISTORY_COLLECTION_NAME);

    }
}
