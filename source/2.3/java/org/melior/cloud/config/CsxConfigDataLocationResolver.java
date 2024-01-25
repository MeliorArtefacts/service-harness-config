/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.cloud.config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataLocationResolver;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.config.Profiles;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * An extension to the client side of the Spring Cloud Config Server.
 * This class resolves a {@code ConfigDataLocation} into one or more
 * {@code CsxConfigDataResource}s.  As a convenience the {@code ConfigDataLocation}
 * may optionally contain one ore more profiles and also one or more
 * labels, in addition to the one or more URIs that may be present.
 * @author Melior
 * @since 2.3
 * @see CsxConfigDataResource
 * @see CsxConfigDataLoader
 */
public class CsxConfigDataLocationResolver implements ConfigDataLocationResolver<CsxConfigDataResource>, Ordered {

    private static final String PREFIX = "csx:";

    private static final List<String> EMPTY_STRING_LIST = new ArrayList<String>();

    /**
     * Constructor.
     */
    public CsxConfigDataLocationResolver() {

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
     * Indicates if the specified location address can be resolved by this resolver.
     * @param context The location resolver context
     * @param location The location
     * @return true if the location is supported by this resolver, false otherwise
     */
    public boolean isResolvable(
        final ConfigDataLocationResolverContext context,
        final ConfigDataLocation location) {
        return location.hasPrefix(PREFIX);
    }

    /**
     * Resolve a location into one or more {@link ConfigDataResource} instances.
     * @param context The location resolver context
     * @param location The location
     * @return The list of resolved {@link ConfigDataResource resources}
     * @throws ConfigDataLocationNotFoundException if a non-optional location cannot be found
     * @throws ConfigDataResourceNotFoundException if a resolved resource cannot be found
     */
    public List<CsxConfigDataResource> resolve(
        final ConfigDataLocationResolverContext context,
        final ConfigDataLocation location) throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException {
        return Collections.emptyList();
    }

    /**
     * Resolve a location into one or more {@link ConfigDataResource} instances
     * based on available profiles.
     * @param context The location resolver context
     * @param location The location
     * @param profiles The profiles
     * @return The list of resolved {@link ConfigDataResource resources}
     * @throws ConfigDataLocationNotFoundException if a non-optional location cannot be found
     */
    public List<CsxConfigDataResource> resolveProfileSpecific(
        final ConfigDataLocationResolverContext context,
        final ConfigDataLocation location,
        final Profiles profiles) throws ConfigDataLocationNotFoundException {

        String[] segments;
        List<String> uris;
        List<String> newProfiles;
        List<String> labels;

        segments = location.getNonPrefixedValue(PREFIX).split("[|]");

        uris = (segments.length < 1) ? EMPTY_STRING_LIST : Arrays.asList(StringUtils.commaDelimitedListToStringArray(segments[0]));

        newProfiles = (segments.length < 2) ? profiles.getActive() : Arrays.asList(StringUtils.commaDelimitedListToStringArray(segments[1]));

        labels = (segments.length < 3) ? EMPTY_STRING_LIST : Arrays.asList(StringUtils.commaDelimitedListToStringArray(segments[2]));

        return Collections.singletonList(CsxConfigDataResource.of(uris, newProfiles, labels));
    }

}
