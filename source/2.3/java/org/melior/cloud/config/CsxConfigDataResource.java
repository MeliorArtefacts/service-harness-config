/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.cloud.config;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

/**
 * An extension to the client side of the Spring Cloud Config Server.
 * This class is a resource from which {@code ConfigData} can be loaded.
 * @author Melior
 * @since 2.3
 * @see CsxConfigDataLocationResolver
 * @see CsxConfigDataLoader
 */
public class CsxConfigDataResource extends ConfigDataResource {

    private List<String> uris;

    private List<String> profiles;

    private List<String> labels;

    /**
     * Constructor.
     * @param uris The URIs
     * @param profiles The profiles
     * @param labels The labels
     */
    private CsxConfigDataResource(
        final List<String> uris,
        final List<String> profiles,
        final List<String> labels) {

        super();

        this.uris = uris;

        this.profiles = profiles;

        this.labels = labels;
    }

    /**
     * Get instance of config data resource.
     * @param uris The URIs
     * @param profiles The profiles
     * @param labels The labels
     * @return The config data resource
     */
    public static CsxConfigDataResource of(
        final List<String> uris,
        final List<String> profiles,
        final List<String> labels) {
        return new CsxConfigDataResource(uris, profiles, labels);
    }

    /**
     * Get URIs as comma delimited string.
     * @return The comma delimited string
     */
    public String getUris() {
        return StringUtils.collectionToCommaDelimitedString(uris);
    }

    /**
     * Get profiles as comma delimited string.
     * @return The comma delimited string
     */
    public String getProfiles() {
        return StringUtils.collectionToCommaDelimitedString(profiles);
    }

    /**
     * Get labels as comma delimited string.
     * @return The comma delimited string
     */
    public String getLabels() {
        return StringUtils.collectionToCommaDelimitedString(labels);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o The other object
     * @return true if this object is the same as the other object, false otherwise
     */
    public boolean equals(
        final Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CsxConfigDataResource that = (CsxConfigDataResource) o;
        return Objects.equals(this.uris, that.uris)
            && Objects.equals(this.profiles, that.profiles)
            && Objects.equals(this.labels, that.labels);
    }

    /**
     * Returns a hash code value for the object.
     * @return The hash code value
     */
    public int hashCode() {
        return Objects.hash(this.uris, this.profiles, this.labels);
    }

    /**
     * Returns a string representation of the object.
     * @return The string representation of the object
     */
    public String toString() {
        return new ToStringCreator(this).append("uris", uris)
            .append("profiles", profiles)
            .append("labels", labels).toString();
    }

}
