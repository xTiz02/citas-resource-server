package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Authorization, String> {
//    Optional<Authorization> findByState(String state);
//    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);
//    Optional<Authorization> findByAccessTokenValue(String accessToken);
//    Optional<Authorization> findByRefreshTokenValue(String refreshToken);
//    Optional<Authorization> findByOidcIdTokenValue(String idToken);
//    Optional<Authorization> findByUserCodeValue(String userCode);
//    Optional<Authorization> findByDeviceCodeValue(String deviceCode);
    @Query("select a from Authorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token"
    )
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);

}