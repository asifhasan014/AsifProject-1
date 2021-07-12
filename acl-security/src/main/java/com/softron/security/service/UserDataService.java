package com.softron.security.service;


import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.Role;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.repository.ModuleRepository;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.RoleRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.security.exceptions.UserAlreadyExistsException;
import com.softron.security.exceptions.UserNotFoundException;
import com.softron.security.payload.ChangePasswordTO;
import com.softron.security.payload.ResetPasswordTO;
import com.softron.security.payload.UserTO;
import com.softron.security.util.AuthUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDataService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(final UserTO userTO) {
		
		if (!this.isUsernameOrEmailExisting(userTO.getUserName(), userTO.getEmail())) {
			final UserEntity user = new UserEntity();
			convertToEntity(userTO, user);
			user.setPassword(passwordEncoder.encode(userTO.getPassword()));
			userRepository.save(user);
		} else {
			throw new UserAlreadyExistsException(
					"An account for the username/email already exists. Please try other username/email.");
		}
		
		

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(final UserTO userTO) {
		final UserEntity user = userRepository.findById(userTO.getUserName())
				.orElseThrow(notFoundException(userTO.getUserName()));
		convertToEntity(userTO, user);
		userRepository.save(user);
	}

	public List<UserTO> getAllUsers(Long orgId) {
		List<Organization> organizationList = new ArrayList<Organization>();
		List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
		organizationList.addAll(organizationDetail);

		getOrganizationList(orgId, organizationList);

		return userRepository.findAllByOrganizationIn(organizationList).stream().map(UserTO::fromUserEntity).collect(Collectors.toList());
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

	public UserTO getUser(final String userName) {
		return userRepository.findById(userName).map(UserTO::fromUserEntity).orElseThrow(notFoundException(userName));
	}

	public boolean isUsernameOrEmailExisting(final String username, final String email) {
		return userRepository.findByUserNameAndEmail(username, email).isPresent();
	}

	public UserEntity getUserByNameOrEmail(final String userName) {
		return userRepository.findByUserNameAndEmail(userName, userName).orElseThrow(notFoundException(userName));
	}

	public List<Role> getRoles(List<Long> ids) {
		return roleRepository.findAllById(ids);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteByUserName(final String username) {
		userRepository.softDelete(username);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean changePassword(final ChangePasswordTO passwordRequest) {
		final UserEntity userEntity = userRepository.findByUserNameIgnoreCase(AuthUtil.getUserName())
				.orElseThrow(notFoundException(AuthUtil.getUserName()));
		if (passwordEncoder.matches(passwordRequest.getOldPassword(), userEntity.getPassword())) {
			userEntity.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
			userRepository.save(userEntity);
			return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean resetPassword(final ResetPasswordTO passwordRequest) {
		final Optional<UserEntity> user = userRepository.findByUserNameIgnoreCase(passwordRequest.getUsername());
		return user.map(userEntity -> {
			userEntity.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
			userRepository.save(userEntity);
			return true;
		}).orElseThrow(notFoundException(passwordRequest.getUsername()));
	}

	private void convertToEntity(final UserTO userTO, final UserEntity user) {
		user.setUserName(userTO.getUserName());
		user.setFullName(userTO.getFullName());
		user.setEmail(userTO.getEmail());
		user.setGovtId(userTO.getGovtId());
		user.setMobile(userTO.getMobile());
		user.setEnabled(true);
		user.setAssignedModules(moduleRepository.findAllById(userTO.getModuleIds() == null ? new ArrayList<Long>() : userTO.getModuleIds()));
		user.setRoles(getRoles(userTO.getRolesIds() == null ? new ArrayList<Long>() : userTO.getRolesIds()));
		Organization organization = organizationRepository.findByIdAndActiveTrue(userTO.getOrgId());
		user.setOrganization(organization);
	}

	private Supplier<? extends RuntimeException> notFoundException(String userName) {
		return () -> new UserNotFoundException("User does not exist: " + userName);
	}

}
