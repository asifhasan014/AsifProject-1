package com.softron.masterdata.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.SubSection;
import com.softron.masterdata.service.SectionService;
import com.softron.masterdata.service.SubSectionService;

@ApiController
//@RequestMapping(UrlConstants.SubSection.ROOT)
public class SubSectionController {
//	@Autowired
//	SubSectionService subSectionService;
//
//	@PostMapping(UrlConstants.SubSection.GET_ALL)
//	public SubSection createSubSection(@Valid @RequestBody SubSection subSection) {
//		return subSectionService.save(subSection);
//	}
//
//	@GetMapping(UrlConstants.SubSection.GET_ALL)
//	public List<SubSection> getAllPlant() {
//		return subSectionService.findAll();
//	}
//
//	@GetMapping(UrlConstants.SubSection.GET)
//	public ResponseEntity<SubSection> getSubSectionById(@PathVariable(value = "id") Long id) {
//		return ResponseEntity.ok(subSectionService.getSubSectionById(id));
//	}
//
//	@DeleteMapping(UrlConstants.SubSection.GET)
//	public ResponseEntity<SubSection> deleteSubSection(@PathVariable(value = "id") Long id) {
//
//		subSectionService.delete(id);
//		return ResponseEntity.ok().build();
//	}
//
//	@PutMapping(UrlConstants.SubSection.GET)
//	public ResponseEntity<SubSection> updateSubSection(@PathVariable(value = "id") Long id,
//			@Valid @RequestBody SubSection defectSubSection) {
//
//		return ResponseEntity.ok().body(subSectionService.update(id, defectSubSection));
//	}
}
