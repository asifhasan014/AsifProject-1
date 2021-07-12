package com.softron.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.admin.dto.OrganizationDto;
import com.softron.admin.transferobjects.RoleTO;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.exceptions.RecordAlreadyExisting;
import com.softron.common.utils.ResponseUtils;
//import com.softron.quality.dto.QcPassReportDto;
//import com.softron.masterdata.dto.ClientDto;
import com.softron.schema.admin.entity.Advocate;
import com.softron.schema.admin.entity.Location;
import com.softron.schema.admin.entity.Menu;
import com.softron.schema.admin.entity.OrgType;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.Petitioner;
import com.softron.schema.admin.entity.Respondent;
import com.softron.schema.admin.entity.Role;
import com.softron.schema.admin.entity.Settings;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.repository.LocationRepository;
import com.softron.schema.admin.repository.MenuRepository;
import com.softron.schema.admin.repository.OrgTypeRepository;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.RoleRepository;
import com.softron.schema.legal.repository.AdvocateRepository;
import com.softron.schema.legal.repository.PetitionerRepository;
import com.softron.schema.legal.repository.RespondentRepository;

@Service
@Transactional(readOnly = true)
public class AdminService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdvocateRepository advRepository;

	@Autowired
	private PetitionerRepository petRepository;

	@Autowired
	private RespondentRepository respRepository;

	@Autowired
	private LocationRepository locRepository;

	@Autowired
	private OrganizationRepository orgRepository;

	@Autowired
	private OrgTypeRepository orgTypeRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	ModelMapper modelMapper;

	String root = "Admin";

	/*
	 * ********** Logged User Organization List API ******************
	 */

	public Response getOrgLeaves(Long loggedUserid) {
		try {
			List<Organization> organizationList = organizationRepository.findAllActive();
			List<Organization> leaves = getLeaves(loggedUserid, organizationList, new ArrayList<Organization>());

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, leaves, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public List<Organization> getLeaves(Long orgId, List<Organization> organizationList, List<Organization> result) {
		// List<Organization> childs = organizationRepository.findByParentId(orgId);
		List<Organization> childOrganizations = organizationList.stream()
				.filter(organization -> orgId.equals(organization.getParentId())).collect(Collectors.toList());
		if (childOrganizations.isEmpty()) {
			List<Organization> list = organizationList.stream()
					.filter(organization -> orgId.equals(organization.getId())).collect(Collectors.toList());
			result.addAll(list);
		}
		for (Organization organization : childOrganizations) {
			getLeaves(organization.getId(), organizationList, result);
		}
		return result;
	}

	public Response getOrgAsCompanyId(Long orgId) {
		try {
//			Organization organizationDetail = organizationRepository.findOrganizationById(orgId);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getOrgAsCompany(orgId),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Organization getOrgAsCompany(Long orgId) {
			Organization organization = organizationRepository.findOrganizationById(orgId);
			if (organization.getParentId() == null) {
				return organization;
			} else {
				return getOrgAsCompany(organization.getParentId());
			}
	}

	public Response getLoggedUserOrg(Long orgId) {
		try {

			List<Organization> organizationList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			organizationList.addAll(organizationDetail);
			// getOrganizationList(orgId, sectionList);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getOrganizationList(orgId, organizationList),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public List<Organization> getOrganizationList(Long orgId, List<Organization> result) {
		// List<Section> result = new ArrayList<Section>();
		try {
			// List<Organization> organizationList = organizationRepository.findAll(orgId);

			List<Organization> organizationChilds = organizationRepository.findByParentId(orgId);
			result.addAll(organizationChilds);
			for (Organization org : organizationChilds) {
				getOrganizationList(org.getId(), result);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	/*
	 * ********** Advocate CRUD Operations ******************
	 */

	public List<Advocate> getActiveAdvocates() {
		return advRepository.findAllActive();
	}

	public List<Advocate> getAllAdvocates() {
		return advRepository.findAll();
	}

	public Advocate getAdvocateById(Long id) {
		return advRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Advocate doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAdvocateById(Long id) {
		advRepository.deleteById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAdvocate(Advocate advocate) {
		advRepository.save(advocate);
	}

	/*
	 * ********** Petitioner CRUD Operations ******************
	 */

	public List<Petitioner> getActivePetitioners() {
		return petRepository.findAllActive();
	}

	public List<Petitioner> getAllPetitioners() {
		return petRepository.findAll();
	}

	public Petitioner getPetitionerById(Long id) {
		return petRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Petitioner doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void savePetitioner(Petitioner pet) {
		petRepository.save(pet);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePetitioner(Long id) {
		petRepository.deleteById(id);
	}

	/*
	 * ********** Respondents CRUD Operations ******************
	 */

	public List<Respondent> getActiveRespondents() {
		return respRepository.findAllActive();
	}

	public List<Respondent> getAllRespondents() {
		return respRepository.findAll();
	}

	public Respondent getRespondentById(Long id) {
		return respRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Respondent doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRespondent(Respondent resp) {
		respRepository.save(resp);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRespondent(Long id) {
		respRepository.deleteById(id);
	}

	/*
	 * ********** Location CRUD Operations ******************
	 */

	public List<Location> getActiveLocations() {
		return locRepository.findAllActive();
	}

	public List<Location> getAllLocations() {
		return locRepository.findAll();
	}

	public Location getLocationById(Long id) {
		return locRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Location doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveLocation(Location location) {
		locRepository.save(location);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLocation(Long id) {
		locRepository.deleteById(id);
	}

	public List<Location> getChildLocations(Long parentId) {
		return locRepository.getChildLocations(parentId);
	}

	/*
	 * ********** Organization CRUD Operations ******************
	 */

	public List<Organization> getActiveOrganizations() {
		return orgRepository.findAllActive();
	}

	public Response getOrganizations(Long parentId) {
		System.out.println(parentId);
		List<Organization> org = new ArrayList<Organization>();
		try {

			if (parentId != null) {
				System.out.println("parent here");
				org = orgRepository.findByParentId(parentId);
			} else {
				System.out.println("parent not here");
				org = orgRepository.findAllWithOutParentId();
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, org, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getAllOrganizations() {

		try {
			List<Organization> organizations = orgRepository.findAll();
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, organizations, organizations.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Organization getOrganizationById(Long id) {
		return orgRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Organization doesn't exist for id=" + id));
	}

//	@Transactional(propagation = Propagation.REQUIRED)
//	public void saveOrganization(Organization org) {
//		orgRepository.save(org);
//		
//	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Response saveOrganization(Organization org) {
		try {
			// Organization organization = modelMapper.map(org, Organization.class);
			if (org.getParentId() == null) {
				org.setRootPath("/" + org.getName());
			} else {
				Optional<Organization> parentRootpath = organizationRepository.findById(org.getParentId());
				org.setRootPath(parentRootpath.get().getRootPath() + "/" + org.getName());
			}
			orgRepository.save(org);
			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, org, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Response saveOrganizationWithSetting(OrganizationDto organizationDto) {
		try {
			// Organization organization = modelMapper.map(org, Organization.class);
			Organization organization = new Organization();
			BeanUtils.copyProperties(organizationDto, organization);
			Settings settings = new Settings();
			BeanUtils.copyProperties(organizationDto.getSettings(), settings);
			organization.setSettings(settings);

			orgRepository.save(organization);

			BeanUtils.copyProperties(organization, organizationDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, organizationDto,
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

//	@Transactional(propagation = Propagation.REQUIRED)
//	public void deleteOrganization(Long id) {
//		orgRepository.deleteById(id);
//	}

	public Response deleteOrganization(Long id) {
		try {
			Organization organization = orgRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			organization.setActive(false);
			orgRepository.save(organization);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, organization,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	/*
	 * ********** Organization Type CRUD Operations ******************
	 */

	public List<OrgType> getActiveOrgTypes() {
		return orgTypeRepository.findAllActive();
	}

	public List<OrgType> getAllOrgTypes() {
		return orgTypeRepository.findAll();
	}

	public OrgType getOrgTypeById(Long id) {
		return orgTypeRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("OrgType doesn't exist for id=" + id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrgType(OrgType orgType) {
		orgTypeRepository.save(orgType);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOrgType(Long id) {
		orgTypeRepository.deleteById(id);
	}

	/*
	 * ********** Roles CRUD Operations ******************
	 */

	public List<Role> getActiveRoles() {
		return roleRepository.findAllActive();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRole(RoleTO roleTO) {
		roleTO.setName(roleTO.getName().toUpperCase());
		if (roleTO.getId() == null && roleRepository.findByNameIgnoreCase(roleTO.getName()).isPresent()) {
			throw new RecordAlreadyExisting("Role name '" + roleTO.getName() + "' already existing in system.");
		}
		Role role = new Role();
		BeanUtils.copyProperties(roleTO, role);
		Set<Menu> permissions = menuRepository.findByIdIn(roleTO.getMenuIds());
		role.setPermittedMenus(permissions);
		roleRepository.save(role);
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public RoleTO getRoleById(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new NoRecordExistsException("Role doesn't exist for id=" + id));
		RoleTO roleTO = new RoleTO();
		BeanUtils.copyProperties(role, roleTO);
		roleTO.setMenuIds(role.getPermittedMenus().stream().map(Menu::getId).collect(Collectors.toList()));
		return roleTO;
	}

	public List<Role> getRolesByModuleIds(final List<Long> moduleIds) {
		return roleRepository.findByModuleIds(moduleIds);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(Long id) {
		roleRepository.deleteById(id);
	}

	private List<OrganizationDto> getResponseDtoList(List<Organization> organizations) {
		List<OrganizationDto> responseDtoList = new ArrayList<>();

		organizations.forEach(organization -> {
			OrganizationDto organizationDto = new OrganizationDto();
			modelMapper.map(organization, organizationDto);
			// BeanUtils.copyProperties(client, clientDto);
			responseDtoList.add(organizationDto);
		});
		return responseDtoList;
	}

}
