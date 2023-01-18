package xml.main.main.service.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class AuthenticationUtilities {
    // exist

    @Value("${conn.username}")
    private String user;
    @Value("${conn.password}")
    private String password;

    @Value("${conn.host}")
    private String host;

    @Value("${conn.port}")
    private int port = -1;

    @Value("${conn.driver}")
    private String driver;
    @Value("${conn.url}")
    private String uri;

}
