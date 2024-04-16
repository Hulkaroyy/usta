package uz.handihub.productms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("keycloak")
@Data
public class KeycloakProperties {
    private String realm;
    private String username;
    private String password;
    private String scope;
    private String clientId;
    private String serverUrl;
    private String clientSecret;
}
