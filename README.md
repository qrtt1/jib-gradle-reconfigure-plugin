# jib-gradle-reconfigure-plugin

A Gradle plugin that allows you to dynamically reconfigure the Jib target image settings using environment variables.

## Features

- Overrides the Jib target image configuration using environment variables
- Simplifies the process of changing the target image repository without modifying the Gradle build file
- Automatically generates the environment variable name based on the last part of the target image URL

## Usage

1. Apply the `jib-gradle-reconfigure-plugin` to your Gradle project.

2. Configure the Jib plugin in your `build.gradle` file with the desired target image:

```groovy
jib {
    to {
        image = 'docker.foobarbar.io/foo/barbar-api'
    }
}
```

3. To override the target image repository, set the corresponding environment variable. The plugin will automatically generate the environment variable name based on the last part of the target image URL.

In this example, the generated environment variable name would be `BARBAR_API_REPO`. The plugin replaces the alphabetic and numeric characters with underscores "-" and appends `_REPO` at the end.

```bash
export BARBAR_API_REPO='my-custom-registry.io/foo/barbar-api'
```

4. Run the Gradle build command, and the plugin will reconfigure the Jib target image based on the provided environment variable.

## Usage

Add the following to your `build.gradle` file:

```groovy
plugins {
    id 'io.github.qrtt1.jib'
}
```

Please check details at the [installation](INSTALL.md) document.

## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, please open an issue or submit a pull request on the GitHub repository.

## License

This plugin is licensed under the [MIT License](LICENSE).