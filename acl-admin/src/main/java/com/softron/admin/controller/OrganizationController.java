package com.softron.admin.controller;

import com.softron.admin.dto.OrganizationDto;
import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.transferobjects.RawTree;
import com.softron.common.transferobjects.TreeTO;
import com.softron.common.utils.TreeBuilder;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.security.domain.UserPrincipal;

import static com.softron.core.constants.AdminApiConstants.ORG_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ApiConstants.TREE_VERB;
import static com.softron.core.constants.ApiConstants.ALL_ORG;
import static com.softron.core.constants.ApiConstants.ORGWITHSETTINGS;
import static com.softron.core.constants.AdminApiConstants.LOGGED_USER_ORG_ENDPOINT;
import static com.softron.core.constants.AdminApiConstants.ORG_AS_COMPANY;
import static com.softron.core.constants.ApiConstants.LOGGED_USER_ORG_TREE_VERB;
import static com.softron.core.constants.ApiConstants.ORG_LEAVES;


import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@ApiController
public class OrganizationController {


	@Autowired
    private AdminService adminService;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
	OrganizationRepository organizationRepository;

    @GetMapping(value = ORG_ENDPOINT + ORG_LEAVES, produces = EXTERNAL_MEDIA_TYPE)
    public Response getOrgLeaves(@RequestParam(required = false) Long orgId, final Principal principal) {
    	if(orgId == null) {
    		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
    		orgId = user.getOrgId();
    		return adminService.getOrgLeaves(orgId);
    	}else {
    		return adminService.getOrgLeaves(orgId);
    	}
		
    }
    
    @GetMapping(value = LOGGED_USER_ORG_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public Response getLoggedUserOrg(final Principal principal) {
    	final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
    	 return adminService.getLoggedUserOrg(user.getOrgId());
    }
    
    @GetMapping(value = ORG_AS_COMPANY , produces = EXTERNAL_MEDIA_TYPE)
    public Response getOrgAsCompany(final Principal principal) {
    	final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
    	 return adminService.getOrgAsCompanyId(user.getOrgId());
    }
    
    @GetMapping(value = ORG_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getActiveOrganizations() {
        return ResponseEntity.ok(adminService.getActiveOrganizations());
    }
    
    @GetMapping(value = ORG_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getAllOrganizations() {
        return ResponseEntity.ok(adminService.getAllOrganizations());
    }
    
    @GetMapping(value = ORG_ENDPOINT + ALL_ORG, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrganizations(@RequestParam(required = false) Long parentId) {
    	
    	
        return ResponseEntity.ok(adminService.getOrganizations(parentId));
    }

    @GetMapping(value = ORG_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> getOrganizationById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(adminService.getOrganizationById(id));
    }

    @DeleteMapping(value = ORG_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    //public ResponseEntity<?> deleteOrganization(@PathVariable(ID) Long id) {
    public Response deleteOrganization(@PathVariable(ID) Long id) {
        return adminService.deleteOrganization(id);
        //return ResponseEntity.ok().build();
    }

    @PostMapping(value = ORG_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOrganization(@RequestBody Organization org) {
        //adminService.saveOrganization(org);
        //return new ResponseEntity<>(HttpStatus.CREATED);
        return ResponseEntity.ok(adminService.saveOrganization(org));
    }
    
    @PostMapping(value = ORG_ENDPOINT+ORGWITHSETTINGS, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveOrganizationWithSetting(@RequestBody OrganizationDto org) {
       
        return ResponseEntity.ok(adminService.saveOrganizationWithSetting(org));
    }

    @PutMapping(value = ORG_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> updateOrganization(@RequestBody Organization org) {
        adminService.saveOrganization(org);
        //return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok(adminService.saveOrganization(org));
    }

    @GetMapping(value = ORG_ENDPOINT + TREE_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public List<TreeTO> getLocationTree() {
        List<Organization> locations = adminService.getActiveOrganizations();
        List<RawTree> rawData = locations.stream().map(l -> new RawTree(l.getId(), l.getNameEng(), l.getParentId())).collect(toList());
        return TreeBuilder.build(rawData);
    }
    
    @GetMapping(value = ORG_ENDPOINT + LOGGED_USER_ORG_TREE_VERB, produces = EXTERNAL_MEDIA_TYPE)
    public List<TreeTO> getLoggedUserOrgTree(final Principal principal) {
    	final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
    	List<Organization> organizationList = new ArrayList<Organization>();
		List<Organization> organizationDetail = organizationRepository.findAllById(user.getOrgId());
		organizationList.addAll(organizationDetail);
		
		List<Organization> orgList = adminService.getOrganizationList(user.getOrgId(), organizationList);

        //List<Organization> locations = adminService.getActiveOrganizations();
        List<RawTree> rawData = orgList.stream().map(l -> new RawTree(l.getId(), l.getName(), l.getParentId())).collect(toList());
        
        return TreeBuilder.build(rawData);
    }
}
