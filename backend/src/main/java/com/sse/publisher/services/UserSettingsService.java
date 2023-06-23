package com.sse.publisher.services;

import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import com.sse.publisher.models.UserModel;
import com.sse.publisher.repositories.UserSettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserSettingsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSettingsService.class);
    private final UserSettingsRepository userSettingsRepository;

    public UserSettingsService(final UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public void create(final UserModel user){

       final UserModel userCheck = userSettingsRepository.getById(user.getUsername());

       if(userCheck != null){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.USER_ALREADY_CREATED);
       }else{
           userSettingsRepository.createUser(user);
       }
    }

    public void updateAliasConfig(final String username, final List<String> configsList){

        final UserModel userCheck = userSettingsRepository.getById(username);

        if(userCheck == null){
            throw  GlobalException.getBuilder(LOGGER)
                    .setExceptionType(ExceptionType.USER_NOT_FOUND);
        }else{
            userSettingsRepository.updateAliasConfig(username, configsList);
        }
    }

    public List<UserModel> getAll() {

        final List<UserModel> usersList = userSettingsRepository.getAll();

        return Optional.ofNullable(usersList).orElse(Collections.emptyList());
    }
}
