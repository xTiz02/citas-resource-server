package org.prd.resourceserver.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_app")
public class ClientApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientId;
    private String clientSecret;
    private String clientName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "client_authentication_methods",
            joinColumns = @JoinColumn(name = "client_id")
    )
    @Column(name = "authentication_method")
    private List<String> authenticationMethods = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "client_authorization_grant_type",
            joinColumns = @JoinColumn(name = "client_id")
    )
    @Column(name = "authorization_grant_type")
    private List<String> authorizationGrantTypes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "client_logout_redirect_uri",
            joinColumns = @JoinColumn(name = "client_id")
    )
    @Column(name = "logout_redirect_uri")
    private List<String> postLogoutRedirectUris;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "client_redirect_uris",
            joinColumns = @JoinColumn(name = "client_id")
    )
    @Column(name = "redirect_uri")
    private List<String> redirectUris;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "client_scope",
            joinColumns = @JoinColumn(name = "client_id")
    )
    @Column(name = "scope")
    private List<String> scopes;
    private int durationInMinutes;
    private boolean requireProofKey;


    public static RegisteredClient toRegisteredClient(ClientApp clientApp) {
        return RegisteredClient.withId(clientApp.getId().toString())
                .clientId(clientApp.getClientId())
                //.clientIdIssuedAt(null)
                .clientSecret(clientApp.getClientSecret())
                //.clientSecretExpiresAt(null)
                .clientName(clientApp.getClientName())
                .authorizationGrantTypes(gts->{
                    gts.addAll(clientApp.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::new).toList());
                })
                .clientAuthenticationMethods(cams->{
                    cams.addAll(clientApp.getAuthenticationMethods().stream().map(ClientAuthenticationMethod::new).toList());
                })
                .redirectUris(rus ->{
                    rus.addAll(clientApp.getRedirectUris());
                })
                .postLogoutRedirectUris(plrus->{
                    plrus.addAll(clientApp.getPostLogoutRedirectUris());
                })
                .scopes(scopes->{
                    scopes.addAll(clientApp.getScopes());
                })
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(clientApp.isRequireProofKey())
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(clientApp.getDurationInMinutes()))
                        .refreshTokenTimeToLive(Duration.ofMinutes(clientApp.getDurationInMinutes() * 4L))
                        .build())
                .build();
    }
}