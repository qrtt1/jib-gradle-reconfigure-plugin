## Installation

Since this plugin has not been published to the Gradle Plugin Portal yet, you can install it by directly referencing the
Maven dependency. Before the official release, in addition to including it in your project, you need to configure the
plugin source in your `settings.gradle` file:

```groovy
plugins {
    id 'io.github.qrtt1.jib'
}
```

### Using GitHub Package Registry

Modify your `settings.gradle` file:

```groovy
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "io.github.qrtt1.jib") {
                useModule("io.github.qrtt1:jib-gradle-reconfigure-plugin:0.3.1")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://maven.pkg.github.com/qrtt1/jib-gradle-reconfigure-plugin/")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
}
```

To use the GitHub Package Registry, you need to use a Classic Personal Access Token with the "read:packages" permission.


### Using JitPack

```groovy
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "io.github.qrtt1.jib") {
                useModule("io.github.qrtt1:jib-gradle-reconfigure-plugin:0.3.1")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        maven { url 'https://jitpack.io' }
    }
}
```