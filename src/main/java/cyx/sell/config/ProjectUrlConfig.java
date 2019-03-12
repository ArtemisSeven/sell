package cyx.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="project")
@Data
@Component
public class ProjectUrlConfig {
    private String wxMpAuthorize;
    private String wxOpenAuthorize;
    private String sell;
}
