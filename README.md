# Angular Gradle Plugin - Demo Project

[![Build Status](https://travis-ci.org/Clashsoft/Angular-Gradle-Demo.svg?branch=master)](https://travis-ci.org/Clashsoft/Angular-Gradle-Demo)

This is a demo project for the [Angular Gradle plugin](https://github.com/Clashsoft/Angular-Gradle).
Follow the steps below to integrate an Angular frontend into your Gradle project with the plugin.

## Guide

> ⓘ In the below guide, `angular-gradle-demo` is a placeholder for your project name.
> It also assumes your backend server runs on port 4567 during development.

### 1. Adding the plugin

Add the plugin to `build.gradle`:

```groovy
plugins {
   // ...
   id 'de.clashsoft.angular-gradle' version '0.1.6' 
}
```

If you are using the legacy plugins DSL, check out the [plugin download page](https://plugins.gradle.org/plugin/de.clashsoft.angular-gradle).

### 2. (optional) Set up Angular root directory

> ⓘ This is only needed if your Angular project is placed in a directory other than `src/main/angular`.

Set the `appDir` property to your angular root path.

```groovy
angular {
    appDir = 'angular/angular-gradle-demo'
}
```

### 3. (optional) Set up Angular build output directory

> ⓘ This is only needed if your Angular project uses a different name than your Gradle project.

Set the `outputDir` property to your Angular build output path.
Usually, this is `<angular-root>/dist/<angular-project-name>`.

```groovy
angular {
    // ...
    outputDir = 'angular/angular-gradle-demo/dist/angular-gradle-demo'
}
```

### 4. Add "apiURL" environment variables

Add the following property to the `environment` object in `<angular-root>/src/environments/environment.ts`:

```typescript
export const environment = {
  // ...
  apiURL: 'localhost:4567',
}
```

In `<angular-root>/src/environments/environment.prod.ts`, add the same property, but with an empty value:

```typescript
export const environment = {
  // ...
  apiURL: '',
}
```

Make sure you prefix your API request URLs with this variable.
If you are already using an environment variable with a similar purpose, you can also keep that.
The name `apiURL` is only a suggestion here.

### 5. Add "environment.gradle.ts"

Create a new file named `environment.gradle.ts` in `<angular-root>/src/environments`, with the following content:

```typescript
export const environment = {
  production: false,
  apiURL: '',
};
```

If you are already an environment variable to prefix API request URLs, use that variable's name instead of `apiURL` here, too.

### 6. Add "gradle" configuration to Angular config

In `angular.json`, add under `projects/angular-gradle-demo/architect/build/configurations`:

```json
            "gradle": {
              "fileReplacements": [
                {
                  "replace": "src/environments/environment.ts",
                  "with": "src/environments/environment.gradle.ts"
                }
              ]
            },
```

### 7. Enable CORS in development mode on your backend server

To run and test frontend and backend separately, you need to run them on two different ports.
This was prepared with `apiURL` (or similar) already, but we also need to enable CORS so the requests are not blocked by the browser.

With the [Sparkjava](http://sparkjava.com/) backend server, you can use the following code:

```java
class Main {
    public static void main(String[] args) {
        final Service service = Service.ignite();
        service.port(...);
        // if you are using Spark singleton APIs instead of Service, replace service.* calls below with Spark.* or static import

        // define static files location
        // needs to be done before enabling CORS!
        service.staticFiles.location("/angular-gradle-demo");

        final boolean devMode = ...;
        if (devMode) {
            // enable CORS for static files (not handled by before(...) below)
            service.staticFiles.header("Access-Control-Allow-Origin", "*");

            // enable CORS for non-static requests
            before((req, res) -> {
                res.header("Access-Control-Allow-Origin", "*");
                res.header("Access-Control-Allow-Headers", "*");
            });

            // allow CORS preflight for methods other than GET
            options("/*", (req, res) -> {
                String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
                if (accessControlRequestHeaders != null) {
                    res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                }

                String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
                if (accessControlRequestMethod != null) {
                    res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                }

                return "OK";
            });
        }
    }
}
```

## Running

> ⓘ IntelliJ users can check out the [premade run configurations](.idea/runConfigurations).

### Serving Frontend via Backend

To build the frontend and serve it via the backend, just run your main class manually.
E.g., if you are using the `application` Gradle plugin, run `gradle run`.

This will build Angular in production mode, so the build may take a while and it will be hard to debug.
To build Angular in dev mode, use `gradle run -Pangular-dev`.
Even in dev mode, Angular may take a while to build.

### Serving Frontend and Backend separately

To run the backend without the frontend, run `gradle run -Pno-angular`.
Now you can run just the frontend with `ng serve`. 
This allows you to debug Angular as usual and is also quite fast.
