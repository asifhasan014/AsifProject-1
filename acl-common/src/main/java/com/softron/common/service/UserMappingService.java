package com.softron.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.softron.common.entity.OrgUsersMapping;
import com.softron.common.exceptions.ModuleIdMissingException;
import com.softron.common.repository.OrgUsersMappingRepository;
import com.softron.core.domain.RequestContextData;

@Service
public class UserMappingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserMappingService.class);

	@Autowired
	private OrgUsersMappingRepository orgUsersMapppingRepo;

	@Resource(name = "requestContextData")
	private RequestContextData requestContextData;

	public Long getUserOrgId(String userId) {
		return orgUsersMapppingRepo.findOrgIdByUserId(getModuleId(), userId);
	}

	public List<String> findAllUserIdsByOrgId(Long orgId) {
		return orgUsersMapppingRepo.findAllUserIdsByOrgId(getModuleId(), orgId);
	}

	public List<OrgUsersMapping> findAllUserIdsByOrgIdsIn(List<Long> orgIds) {
		return orgUsersMapppingRepo.findAllUserIdsByOrgIdsIn(getModuleId(), orgIds);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrgUserMapping(Long moduleId, Long orgId, List<String> userIds) {
		orgUsersMapppingRepo.deleteByOrgId(moduleId, orgId);
		final List<OrgUsersMapping> mappings = new ArrayList<>();
		userIds.stream().filter(u -> !StringUtils.isEmpty(u)).forEach(userId -> mappings
				.add(OrgUsersMapping.builder().moduleId(moduleId).orgId(orgId).userId(userId).build()));
		if (!mappings.isEmpty()) {
			orgUsersMapppingRepo.saveAll(mappings);
		}
	}

	private long getModuleId() {
		LOGGER.info("Module id value in request context: {}", requestContextData.getModuleId());
		return requestContextData.getModuleId()
				.orElseThrow(() -> new ModuleIdMissingException("Module id not present in request context."));
	}

}
