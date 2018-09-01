package springbootexample.auth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class SecurityAuth2Configuration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;


    @Override
    public void configure( AuthorizationServerSecurityConfigurer oauthServer ) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure( ClientDetailsServiceConfigurer clients ) throws Exception {
        clients.inMemory()
                .withClient("javadeveloperzone")
                .secret("secret")
                .accessTokenValiditySeconds(2000)        // expire time for access token
                .refreshTokenValiditySeconds(-1)         // expire time for refresh token
                .scopes("read", "write")                         // scope related to resource server
                .authorizedGrantTypes("password", "refresh_token");      // grant type
    }

    @Override
    public void configure( AuthorizationServerEndpointsConfigurer endpoints ) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}
