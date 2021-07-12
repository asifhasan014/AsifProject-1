package com.softron.reporting.service;

import com.softron.common.service.UserMappingService;
import com.softron.common.utils.AuthUtil;
import com.softron.reporting.repository.ReportRepository;
import com.softron.reporting.repository.ReportingReportTypeRepository;
import com.softron.reporting.repository.specification.ReportSpecification;
import com.softron.reporting.to.SearchResponseTO;
import com.softron.reporting.to.SearchTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportSearchService {

    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportingReportTypeRepository typeRepo;

    @Autowired
    private UserMappingService userMappingService;

    public List<SearchResponseTO> search(final SearchTO search) {
        search.setUserName(AuthUtil.getUserName());
        search.setOrgId(userMappingService.getUserOrgId(search.getUserName()));
        final Optional<String> reportType = search.getType() == null ? Optional.empty() : Optional.ofNullable(typeRepo.getNameById(search.getType()));
        return repository.findAll(ReportSpecification.search(search)).stream().map(r -> {
            //@formatter:off
            return SearchResponseTO
                    .builder()
                    .id(r.getReportId())
                    .type(reportType.orElseGet(()->typeRepo.getNameById(r.getType())))
                    .subject(r.getSubject())
                    .build();
        }).collect(Collectors.toList());
    }

}
