# gradle-mustache-plugin
[![Build Status](https://travis-ci.org/kamkozlowski/gradle-mustache-plugin.svg?branch=master)](https://travis-ci.org/kamkozlowski/gradle-mustache-plugin)
[![codecov.io](https://codecov.io/github/kamkozlowski/gradle-mustache-plugin/coverage.svg?branch=master)](https://codecov.io/github/kamkozlowski/gradle-mustache-plugin?branch=master)

Gradle plugin can be used to process mustache templates during build. This project is a port of [mustache-maven-plugin](https://github.com/wouterd/mustache-maven-plugin) which provides similar functionality but for maven build system.

# Description
This gradle plugin allows you to define one or more contexts in YAML and push those through one or more mustache templates 
during your gradle build

# Usage
All you need to make the plugin work is use ```template``` function where you define template, output target and properties. At this moment plugin not present in maven repostiry, you have to build it and export to your local repository.
```
buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath group: 'com.github.kamkozlowski.gradle',
                name: 'mustache',
                version: '0.0.1'
    }
}

repositories {
    mavenCentral()
}

apply plugin: 'mustache-template'

mustache {
    template( uri('src/main/resources/template.mustache'), uri('src/main/resources/output.txt'), uri('src/main/resources/props.yaml'))
}
```
