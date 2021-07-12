package com.softron.schema.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findByToken(String token);

	@Query("Update PasswordResetToken p set p.tokenRedeemed=true where p.token = :token and p.user.userName= :userName")
	@Modifying
	void reedemToken(@Param("userName") String username, @Param("token") String token);
}
