package xml.authorship.service.authorship.service.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
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

    // fuseki
    @Value("${conn.endpoint}")
    private String endpoint;

    @Value("${conn.dataset}")
    private String dataset;

    @Value("${conn.query}")
    private String queryEndpoint;

    @Value("${conn.update}")
    private String updateEndpoint;

    @Value("${conn.data}")
    private String dataEndpoint;

    public String getFullQueryEndpoint() {
        return String.join("/", this.endpoint, this.dataset, this.queryEndpoint).trim();
    }

    public String getFulUpdateEndpoint() {
        return String.join("/", this.endpoint, this.dataset, this.updateEndpoint).trim();
    }

    public String getFullDataEndpoint() {
        return String.join("/", this.endpoint, this.dataset, this.dataEndpoint).trim();
    }
}
