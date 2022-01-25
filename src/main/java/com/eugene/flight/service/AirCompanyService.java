package com.eugene.flight.service;

import com.eugene.flight.dao.AirCompanyRepository;
import com.eugene.flight.dao.AirplaneRepository;
import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.exception.AirCompanyNotFoundException;
import com.eugene.flight.exception.AirplaneNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class AirCompanyService {

    private AirCompanyRepository companyRepository;

    private AirplaneRepository airplaneRepository;

    @Autowired
    public AirCompanyService(AirCompanyRepository companyRepository, AirplaneRepository airplaneRepository) {
        this.companyRepository = companyRepository;
        this.airplaneRepository = airplaneRepository;
    }

    public AirCompany createCompany(AirCompany company) {
        return Optional.of(companyRepository.save(company))
                .orElseThrow(() -> new AirCompanyNotFoundException("Air Company cannot be null" + company));
    }

    public AirCompany findAirCompanyById(Long id) {
        return Optional.of(companyRepository.getById(id))
                .orElseThrow(() -> new AirCompanyNotFoundException("Air Company ID cannot be null" + id));
    }

    @Transactional
    public AirCompany updateAirCompanyByName(Long companyId, String name) {
        if (companyId == null || !StringUtils.hasText(name)) {
            throw new AirCompanyNotFoundException("Cannot update company by name");
        }
        return companyRepository.updateCompanyByName(companyId, name);
    }

    @Transactional
    public void deleteCompanyById(Long id) {
        if (id == null) {
            throw new AirCompanyNotFoundException("Cannot delete company by this id");
        }
        companyRepository.deleteById(id);
    }

    @Transactional
    public AirCompany reassignAirplaneToAnotherCompany(Long fromCompanyId, Long toCompanyId, Long airplaneId) {
        AirCompany currentCompany = companyRepository.getById(fromCompanyId);
        List<Airplane> airplanes = currentCompany.getAirplanes();

        Airplane airplaneToSwitch = airplanes.stream()
                .filter(currentAirplane -> currentAirplane.getId()
                        .equals(airplaneId))
                .findFirst()
                .orElseThrow(() -> new AirplaneNotFoundException(
                        String.format("Airplane by this ID: %d was not found or this airplane belongs to other company", airplaneId)
                ));

        currentCompany.removeAirplane(airplaneToSwitch);

        AirCompany destCompany = companyRepository.getById(toCompanyId);
        destCompany.addAirplane(airplaneToSwitch);
        airplaneRepository.save(airplaneToSwitch);
        return destCompany;
    }
}