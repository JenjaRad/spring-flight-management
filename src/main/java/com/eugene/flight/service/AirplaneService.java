package com.eugene.flight.service;

import com.eugene.flight.dao.AirplaneRepository;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.exception.AirplaneNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    @Transactional
    public Airplane createAirplane(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    public Airplane findById(Long id) {
        return airplaneRepository.findById(id)
                .orElseThrow(AirplaneNotFoundException::new);
    }

    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }
}
