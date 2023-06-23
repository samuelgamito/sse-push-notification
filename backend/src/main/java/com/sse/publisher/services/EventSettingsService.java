package com.sse.publisher.services;

import com.sse.publisher.controller.vo.request.EventSettingsRequest;
import com.sse.publisher.models.EventSettingsModel;
import com.sse.publisher.repositories.EventSettingsRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventSettingsService {


    private final EventSettingsRepository eventSettingsRepository;

    public EventSettingsService(EventSettingsRepository eventSettingsRepository) {
        this.eventSettingsRepository = eventSettingsRepository;
    }

    public void upsert(final EventSettingsRequest eventSettingsRequest){

        final EventSettingsModel eventSettingsModel = new EventSettingsModel();
        eventSettingsModel.setAlias(eventSettingsRequest.getAlias());
        eventSettingsModel.setDescription(eventSettingsRequest.getDescription());

        final String routingKey = eventSettingsRequest
                .getAlias()
                .replaceAll("[^a-zA-Z0-9]", "")
                .strip()
                .toLowerCase()
                .concat(".*");

        eventSettingsModel.setRoutingKey(routingKey);

        eventSettingsRepository.upsert(eventSettingsModel);
    }

    public List<EventSettingsModel> getAll(){
        final List<EventSettingsModel> eventSettingsModel = eventSettingsRepository.getAll();

        return eventSettingsModel;
    }
}
