# Melior Service Harness :: Config
<div style="display: inline-block;">
<img src="https://img.shields.io/badge/version-2.3-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/production-ready-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/compatibility-spring_boot_2.4.5-green?style=for-the-badge"/>
</div>

## Artefact
Get the artefact and the POM file in the *artefact* folder.
```
<dependency>
    <groupId>org.melior</groupId>
    <artifactId>melior-harness-config</artifactId>
    <version>2.3</version>
</dependency>
```

## Client
Set the config data location in the application properties to use the Spring Cloud Config Server extended client.  If no profile is active, then by default the host name is sent to the Spring Cloud Config Server as the profile.
```
spring.config.import=csx:http://config.server:8888
```

If one or more profiles are active, extend the config data location with a shortcut to send a different value to the Spring Cloud Config Server as the profile.  This is equivalent to using the **spring.cloud.config.profile** application property.
```
spring.config.import=csx:http://config.server:8888|some.other.value
```

If one or more profiles are active, extend the config data location to send the host name to the Spring Cloud Config Server as the profile.
```
spring.config.import=csx:http://config.server:8888|${host.name}
```

If the config data location is not extended, and either an **environment.name** system property or an **ENVIRONMENT_NAME** environment variable is set, then the environment name is sent to the Spring Cloud Config Server as the profile.

&nbsp;  
## References
Refer to the [**Melior Service Harness :: Core**](https://github.com/MeliorArtefacts/service-harness-core) module for detail on the Melior logging system and available utilities.
