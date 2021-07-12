package com.softron.reporting.controller;

import com.softron.core.annotations.ApiController;
import com.softron.reporting.service.ReportSearchService;
import com.softron.reporting.to.SearchResponseTO;
import com.softron.reporting.to.SearchTO;

import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ReportingApiContants.OR_SEARCH_ENDPOINT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController
public class ReportingSearchController {

    @Autowired
    private ReportSearchService searchService;

    @PostMapping(value = OR_SEARCH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<List<SearchResponseTO>> search(@RequestBody SearchTO search) {
        return ResponseEntity.ok(searchService.search(search));
    }

}
