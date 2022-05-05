package com.eugene.flight.service;

import com.eugene.flight.dao.AirCompanyRepository;
import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.exception.AirCompanyNotFoundException;
import com.eugene.flight.exception.AirplaneNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class AirCompanyService {

    private final AirCompanyRepository companyRepository;

    @Transactional
    public AirCompany createCompany(AirCompany company) {
        return companyRepository.save(company);
    }

    public List<AirCompany> findAll() {
        return companyRepository.findAll();
    }

    public AirCompany findAirCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new AirCompanyNotFoundException("This Air Company ID doesn't exist - " + id));
    }

    @Transactional
    public void updateAirCompanyByName(Long companyId, String name) {
        if (companyId == null || !StringUtils.hasText(name)) {
            throw new AirCompanyNotFoundException("Cannot update company by name");
        }
        log.info("Successfully updated company with ID : {} ", companyId);
        companyRepository.updateCompanyByName(companyId, name);
    }

    @Transactional
    public void deleteCompanyById(Long id) {
        if (id == null) {
            throw new AirCompanyNotFoundException("Cannot delete company by this id");
        }
        log.info("Successfully deleted company with ID : {} ", id);
        companyRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AirCompany reassignAirplaneToAnotherCompany(Long fromCompanyId, Long toCompanyId, Long airplaneId) {
        AirCompany currentCompany = companyRepository.findById(fromCompanyId)
                .orElseThrow(AirCompanyNotFoundException::new);
        List<Airplane> airplanes = currentCompany.getAirplanes();

        Airplane airplaneToSwitch = findAirplaneById(airplaneId, airplanes);

        currentCompany.removeAirplane(airplaneToSwitch);

        AirCompany destCompany = findOrCreateTargetCompany(toCompanyId, airplaneToSwitch);
        if (!destCompany.getAirplanes()
                .contains(airplaneToSwitch)) {
            destCompany.addAirplane(airplaneToSwitch);
            log.info("Successfully add an airplane to existed company: {} ", destCompany.getId());
        }
        log.info("Successfully add an airplane to newly created company: {} ", destCompany.getId());
        return destCompany;
    }

    private AirCompany findOrCreateTargetCompany(Long toCompanyId, Airplane airplaneToSwitch) {
        return companyRepository.findById(toCompanyId)
                .orElseGet(() -> {
                    var targetCompany = new AirCompany();
                    targetCompany.setId(toCompanyId);
                    targetCompany.addAirplane(airplaneToSwitch);
                    return companyRepository.saveAndFlush(targetCompany);
                });
    }

    private Airplane findAirplaneById(Long airplaneId, List<Airplane> airplanes) {
        return airplanes.stream()
                .filter(currentAirplane -> currentAirplane.getId()
                        .equals(airplaneId))
                .findFirst()
                .orElseThrow(() -> {
                            log.error("Cannot find airplane by this ID: {} ", airplaneId);
                            throw new AirplaneNotFoundException(
                                    String.format("Airplane by this ID: %d was not found or this airplane belongs to other company", airplaneId));
                        }
                );
    }
}