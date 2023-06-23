package com.sse.publisher.repositories;

import com.sse.publisher.constants.MongoConstants;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import com.sse.publisher.models.UserModel;
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
public class UserSettingsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSettingsRepository.class);
    private final MongoTemplate mongoTemplate;

    public UserSettingsRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public UserModel getById(final String id){

        try{
            return mongoTemplate.findById(id, UserModel.class,  MongoConstants.USER_COLLECTION_NAME);
        }catch (Exception e){
        throw  GlobalException.getBuilder(LOGGER)
                .setExceptionType(ExceptionType.DATABASE_ERROR)
                .setFormattedErrorMessage(e.getMessage())
                .setDetailedError(e);
        }

    }

    public void createUser(final UserModel user){
        try{

            final OffsetDateTime timeNow = OffsetDateTime.now();

            user.setCreatedAt(timeNow);
            user.setUpdatedAt(timeNow);

            mongoTemplate.insert(user, MongoConstants.USER_COLLECTION_NAME);
        }catch (Exception e){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.DATABASE_ERROR)
                    .setFormattedErrorMessage(e.getMessage())
                    .setDetailedError(e);
        }
    }

    public void updateAliasConfig(final String id, final List<String> configsList){

        try{
            final OffsetDateTime timeNow = OffsetDateTime.now();
            final Query updateCondition = Query.query(Criteria.where(MongoConstants.ID).is(id));
            final UpdateDefinition updateDefinition = Update.update(MongoConstants.ID,id)
                    .set(MongoConstants.ALIAS_LIST_NAME, configsList)
                    .set(MongoConstants.UPDATED_AT, timeNow);
            mongoTemplate.updateFirst(updateCondition, updateDefinition, UserModel.class, MongoConstants.USER_COLLECTION_NAME);
        }catch (Exception e){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.DATABASE_ERROR)
                    .setFormattedErrorMessage(e.getMessage())
                    .setDetailedError(e);
        }

    }

    public List<UserModel> getAll() {

        try{
            return mongoTemplate.findAll(UserModel.class,  MongoConstants.USER_COLLECTION_NAME);
        }catch (Exception e){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.DATABASE_ERROR)
                    .setFormattedErrorMessage(e.getMessage())
                    .setDetailedError(e);
        }
    }
}
