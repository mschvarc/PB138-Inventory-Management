package pb138.web.email;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Responsible for loading email sender configuration files
 * @author Martin Schvarcbacher
 */
@Component
public interface EmailConfigLoader {
    /**
     * Returns map containing email config
     *
     * @return KV map
     */
    Map<String, String> extractEmailConfig();
}
