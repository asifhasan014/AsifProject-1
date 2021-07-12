package com.softron.schema.admin.repository;

import static com.softron.core.constants.CommonTableNameConstants.USER_MAPPING_TABLE;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.bo.UserBO;

/**
 * 
 * UserRespository for data store operations as read/write user information.
 *
 * @author Mozahid
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * 
     * Method to find a user by matching user-name ignoring case.
     * 
     * @param username
     *            - user-name to be matched.
     * @return - {@code Optional} UserEntity object.
     */
    Optional<UserEntity> findByUserNameIgnoreCase(String username);

    /**
     * 
     * Method to find a user by matching email ignoring case.
     * 
     * @param email
     *            - email to be matched.
     * @return - {@code Optional} UserEntity object.
     */
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    /**
     * 
     * Method to find a user by matching user name or email in lower case.
     * 
     * @param user
     *            - user-name to be matched.
     * @return - {@code Optional} UserEntity object.
     */
    @Query("Select u from UserEntity u where lower(u.userName)=?1 or lower(u.email)=?1")
    Optional<UserEntity> findByUserNameOrEmail(String user);

    /**
     * 
     * Method to find a user by matching user name and email in lower case.
     * 
     * @param userName
     *            - user-name to be matched.
     * @param email
     *            - email to be matched.
     * @return - {@code Optional} UserEntity object.
     */
    @Query("Select u from UserEntity u where lower(u.userName)= :userName or lower(u.email)= :email")
    Optional<UserEntity> findByUserNameAndEmail(@Param("userName") String userName, @Param("email") String email);

    /**
     * 
     * Method to fetch all user details ordering by user-name.
     * 
     * @return - List of {@code UserEntity} objects.
     */
    List<UserEntity> findAllByOrderByUserName();

    @Modifying
    @Query("Update UserEntity u set u.enabled=false where u.userName=?1")
    void softDelete(String user);
    
    @Query(value = "Select u.USER_ID as userName, u.FULL_NAME as fullName, u.EMAIL as email from USERS u "
            + "inner join USERS_ASSIGNED_MODULES m on m.USER_ENTITY_USER_ID = u.USER_ID "
            + "where m.ASSIGNED_MODULES_ID = :moduleId and u.USER_ID not in (SELECT USER_ID from " + USER_MAPPING_TABLE + " WHERE MODULE_ID= :moduleId AND ORG_ID = :orgId)", nativeQuery = true)
    List<UserBO> findNonMappedUsersByModuleId(@Param("moduleId") Long moduleId, @Param("orgId") Long orgId);
    
    @Query(value = "Select u.USER_ID as userName, u.FULL_NAME as fullName, u.EMAIL as email from USERS u "
            + "inner join USERS_ASSIGNED_MODULES m on m.USER_ENTITY_USER_ID = u.USER_ID "
            + "where m.ASSIGNED_MODULES_ID = :moduleId and u.USER_ID in (SELECT USER_ID from " + USER_MAPPING_TABLE + " WHERE MODULE_ID= :moduleId AND ORG_ID = :orgId)", nativeQuery = true)
    List<UserBO> findMappedUsersByModuleIdOrgId(@Param("moduleId") Long moduleId, @Param("orgId") Long orgId);

    List<UserEntity> findAllByOrganizationIn(List<Organization> organizations);

}
