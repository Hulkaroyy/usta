package uz.handihub.productms.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.handihub.productms.properties.KeycloakProperties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KeycloakConfiguration {

    private final KeycloakProperties properties;

    @Bean("Keycloak")
    public Keycloak configure() {
        log.info("Configuring Keycloak bean");
        return KeycloakBuilder
                .builder()
                .serverUrl(properties.getServerUrl())
                .realm(properties.getRealm())
                .grantType(OAuth2Constants.PASSWORD)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .scope(properties.getScope())
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .build();
    }
}
