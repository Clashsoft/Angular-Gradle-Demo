# Angular Gradle Plugin - Demo Project

This is a demo project for the [Angular Gradle plugin](https://github.com/Clashsoft/Angular-Gradle).
Follow the steps below to integrate an Angular frontend into your Gradle project with the plugin.

## Guide

> ⓘ In the below guide, `angular-gradle-demo` is a placeholder for your project name.

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

### 4. Add "environment.gradle.ts"

Create a new file named `environment.gradle.ts` in `<angular-root>/src/environments`, with the following content:

```typescript
export const environment = {
  production: false,
};
```

### 5. Add "gradle" configuration to Angular config

In `angular.json`, add:

```json5
{
  // ...
  "projects": {
    "angular-gradle-demo": {
      // ...
      "architect": {
        "build": {
          // ...
          "configurations": {
            "gradle": {
              "fileReplacements": [
                {
                  "replace": "src/environments/environment.ts",
                  "with": "src/environments/environment.gradle.ts"
                }
              ]
            },
            "production": {
              // ...
}}}}}}}
```
