package com.sse.publisher.controller;

import com.sse.publisher.controller.vo.request.EventSettingsRequest;
import com.sse.publisher.controller.vo.response.EventSettingsResponse;
import com.sse.publisher.models.EventSettingsModel;
import com.sse.publisher.services.EventSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.sse.publisher.constants.StatusConstants.STATUS_200_GET_OK;
import static com.sse.publisher.constants.StatusConstants.STATUS_201_CREATED;
import static com.sse.publisher.constants.StatusConstants.STATUS_400_BAD_REQUEST;
import static com.sse.publisher.constants.StatusConstants.STATUS_500_UNAVAILABLE;

@RestController
@Api(value = "Manage event information")
@RequestMapping("/settings")
public class EventSettingsController {

    private final EventSettingsService service;

    public EventSettingsController(final EventSettingsService service) {
        this.service = service;
    }


    @PostMapping()
    @ApiOperation(
            value = "Insert or update an event information",
            notes = "If the ID doesn't exists a new record will be inserted otherwise the old one will be updated."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = STATUS_201_CREATED),
                    @ApiResponse(code = 400, message = STATUS_400_BAD_REQUEST),
                    @ApiResponse(code = 500, message = STATUS_500_UNAVAILABLE)
            })
    public ResponseEntity<Void> upsertEventSettings(@RequestBody @Validated final EventSettingsRequest request){


        service.upsert(request);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping(
            path= "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Get events list.",
            notes = "Get all possible events on the database."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = STATUS_200_GET_OK),
                    @ApiResponse(code = 400, message = STATUS_400_BAD_REQUEST),
                    @ApiResponse(code = 500, message = STATUS_500_UNAVAILABLE)
            })
    public ResponseEntity<List<EventSettingsResponse>> getEventSettings(){

        final List<EventSettingsModel> settingsModels = service.getAll();

        return ResponseEntity.ok(settingsModels.stream().map(EventSettingsResponse::new).collect(Collectors.toList()));
    }

}
