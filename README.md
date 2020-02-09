# Angular Gradle Plugin - Demo Project

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
   id 'de.clashsoft.angular-gradle' version '0.1.3' 
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
