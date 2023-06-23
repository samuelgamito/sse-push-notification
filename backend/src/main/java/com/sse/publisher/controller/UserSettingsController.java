package com.sse.publisher.controller;

import com.sse.publisher.controller.vo.request.UserRequest;
import com.sse.publisher.controller.vo.response.UserResponse;
import com.sse.publisher.models.UserModel;
import com.sse.publisher.services.UserSettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    public UserSettingsController(final UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    @PostMapping(path= "/")
    public ResponseEntity<Void> publishEvent(@RequestBody @Validated final UserRequest userRequest) {

        final UserModel userModel = new UserModel(userRequest);

        userSettingsService.create(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(path= "/{username}")
    public ResponseEntity<Void> updateUserAliasList(@RequestBody @NotBlank final List<String> aliasConfig,
                                                    @PathVariable final String username) {

        userSettingsService.updateAliasConfig(username, aliasConfig);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path= "/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        final List<UserModel> user = userSettingsService.getAll();



        return ResponseEntity.status(HttpStatus.OK).body( user.stream().map(UserResponse::new).collect(Collectors.toList()));
    }

}
