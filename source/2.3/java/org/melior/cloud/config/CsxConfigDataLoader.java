/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.cloud.config;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

/**
 * An extension to the client side of the Spring Cloud Config Server.
 * This class loads {@code ConfigData} for a given {@code CsxConfigDataResource}.
 * The {@code ConfigData} injects a {@code PropertySource} with config properties
 * that have the following effects:
 * <ul>
 * <li>The host name is presented in a {@code host.name} config property.</li>
 * <li>The user may set the {@code spring.cloud.config.profile} or {@code spring.cloud.config.label}
 * config property to ${{@code host.name}} to send the host name to the Spring
 * Cloud Config Server as either the profile or the label.</li>
 * <li>If no profile is active and the user has not set the {@code spring.cloud.config.profile}
 * config property, then the {@code spring.cloud.config.profile} config property is
 * automatically set to ${{@code host.name}}.</li>
 * <li>If one or more profiles are active, but the user prefers to send some other value
 * to the Spring Cloud Config Server as the profile, then the user may set an {@code environment.name}
 * config property or an {@code ENVIRONMENT_NAME} environment variable as an alternative
 * to setting the {@code spring.cloud.config.profile} config property.</li>
 * </ul>
 * @author Melior
 * @since 2.3
 * @see CsxConfigDataLocationResolver
 * @see CsxConfigDataResource
 */
public class CsxConfigDataLoader implements ConfigDataLoader<CsxConfigDataResource>, Ordered {

    /**
     * Constructor.
     */
    public CsxConfigDataLoader() {

        super();
    }

    /**
     * Get the order value of this object.
     * @return The order value
     */
    public int getOrder() {
        return -2;
    }

    /**
     * Load {@link ConfigData} for the given resource.
     * @param context The loader context
     * @param resource The resource
     * @return The loaded config data or {@code null} if the location should be skipped
     * @throws IOException if an IO error occurred
     * @throws ConfigDataResourceNotFoundException if the resource cannot be found
     */
    public ConfigData load(
        final ConfigDataLoaderContext context,
        final CsxConfigDataResource resource) throws IOException, ConfigDataResourceNotFoundException {

        InetAddress address;
        String profiles;
        String labels;
        HashMap<String, Object> properties;
        ConfigData configData;

        try {

            address = InetAddress.getLocalHost();
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to retrieve address of local host.", exception);
        }

        profiles = resource.getProfiles();

        labels = resource.getLabels();

        properties = new HashMap<String, Object>();
        properties.put("host.name", address.getHostName());
        properties.put("spring.config.import", "configserver:" + resource.getUris());
        properties.put("spring.cloud.config.name", "${service.name:${spring.application.name:${spring.config.name:application}}}");
        properties.put("spring.cloud.config.profile", (StringUtils.hasText(profiles) == true)
            ? "${environment.name:" + profiles + "}" : "${environment.name:${host.name}}");
        if (StringUtils.hasText(labels) == true) properties.put("spring.cloud.config.label", labels);

        configData = new ConfigData(Collections.singletonList(new MapPropertySource("melior.cloud.config", properties)));

        return configData;
    }

}
