package com.softron.masterdata.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.TargetAndManpowerDto;
import com.softron.masterdata.repository.SectionRepository;
import com.softron.masterdata.repository.TargetAndManpowerRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.DateTimeUtilConversionService;
import com.softron.common.businessobjects.Response;

@Service
public class LineTargetAndManPowerService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	TargetAndManpowerRepository targetAndManpowerRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;

	String root = "TargetAndManpower";

	public Response getByStartDateAndEndDate(Long orgId, String startDate, String endDate) {
		try {
			Date stDate = DateTimeUtilConversionService.getDateFromString(startDate,"yyyy-MM-dd");
			Date endDate1 = DateTimeUtilConversionService.getDateFromString(endDate, "yyyy-MM-dd");

			List<TargetAndManpower> targetAndManpowerList = targetAndManpowerRepository
					.findAllByStartAndEndDate(orgId, stDate, endDate1);

			List<TargetAndManpowerDto> targetAndManpowerDtoList = getResponseDtoList(targetAndManpowerList);

			for (TargetAndManpowerDto TargetAndManpowerDtoEach : targetAndManpowerDtoList) {
				String dateString = DateTimeUtilConversionService.getStringFromDate(TargetAndManpowerDtoEach.getDate(), "yyyy-MM-dd");
				TargetAndManpowerDtoEach.setDateString(dateString);
				TargetAndManpowerDtoEach.setDate(null);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, targetAndManpowerDtoList,
					targetAndManpowerList.size(), String.format("All %ses", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	private List<TargetAndManpowerDto> getResponseDtoList(List<TargetAndManpower> targetAndManpowers){
		List<TargetAndManpowerDto> responseDtoList = new ArrayList<>();
		targetAndManpowers.forEach(targetAndManpower -> {
			TargetAndManpowerDto targetAndManpowerDto = new TargetAndManpowerDto();
			modelMapper.map(targetAndManpower, targetAndManpowerDto);
			
			responseDtoList.add(targetAndManpowerDto);
		});
		return responseDtoList;
	}

}
