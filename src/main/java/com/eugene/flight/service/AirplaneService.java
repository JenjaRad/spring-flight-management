package com.eugene.flight.service;

import com.eugene.flight.dao.AirplaneRepository;
import com.eugene.flight.domain.Airplane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirplaneService {

    private AirplaneRepository airplaneRepository;

    @Autowired
    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public Airplane createAirplane(Airplane airplane) {
        return Optional.of(airplaneRepository.save(airplane))
                .orElseThrow(IllegalArgumentException::new);
    }
}
