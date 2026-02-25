package br.gov.caixa.siaas_api.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Comunidade Gestão de Identidade, Acesso e Antifraude",
                version = "1.0.0",
                description = "SIMPA - Sistema Matriz de Perfil de Acesso",
                contact = @Contact(name = "XXXXXXXX", email = "XXXXXXXX@caixa.gov.br")
        ),
        servers = {
                @Server(
                        description = "URL do API Manager - Ambiente LOCAL",
                        url = "http://localhost:8080/siaas-api"
                ),
                @Server(
                        description = "URL do API Manager - Ambiente DES",
                        url = "https://siaas-api-des.apps.nprd.caixa/siaas-api"
                ),
                @Server(
                        description = "URL do API Manager - Ambiente PRD",
                        url = "https://siaas-api.apps.nprd.caixa/siaas-api"
                )
        }
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        type = SecuritySchemeType.OAUTH2,
                        description = "Autenticação com SSO da Caixa",
                        name = "SSO",
                        flows = @OAuthFlows(
                                clientCredentials = @OAuthFlow(
                                        refreshUrl = "https://login.des.caixa/auth/realms/intranet/protocol/openid-connect/token",
                                        scopes = {
                                                @OAuthScope(name = "uma_authorization", description = "realm_access - roles")
                                        },
                                        tokenUrl = "https://login.des.caixa/auth/realms/intranet/protocol/openid-connect/token"
                                )
                        )
                ),
                @SecurityScheme(
                        type = SecuritySchemeType.APIKEY,
                        in = SecuritySchemeIn.HEADER,
                        name = "apiKey"
                )
        }
)
@Configuration
public class OpenApiConfig {
}
