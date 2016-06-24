# inFullMobile Toolkit

Plugin for [Gradle](https://gradle.org/) which configures commonly used tools and settings in [Android](https://www.android.com/) projects.

## Features


#### QA tools configuration

Right now, following tools are included:

- [Checkstyle](http://checkstyle.sourceforge.net/)
- [Android Lint](https://developer.android.com/studio/write/lint.html)
- [FindBugs](http://findbugs.sourceforge.net/)
- [Android Tests](https://developer.android.com/studio/test/index.html)

By default, all of those tools are enabled and will be executed during check.
This plugin automatically detects your build types and flavors. QA tools are configured for each one of them,
including their test targets.

Toolkit also allows you to configure said tools in various ways:

- By putting configuration file in module's directory
- By putting configuration file in root project directory
- By downloading configuration file from specified URL

This plugin also adds various check commands:

- `check{flavorName}[buildTypeName]` - performs all QA tools check in specified flavor and build type
- `check{flavorName}[buildTypeName]Connected` - same as above, but including Android connected tests
- `checkBuildType[buildTypeName]` - Runs all QA tools for specified build type (all flavors)
- `checkBuildType[buildTypeName]Connected`

#### Fabric configuration

Just a few command line arguments commonly used when uploading Android applications
to [Fabric](https://fabric.io):

- adds `-PfabricTeam="<team1,team2,...,teamN>"` command line argument.
Useful when you want to target your release to specific groups without the need for hardcoding them in your config.
- adds `-PfabricChangelogPath="<changelog_file_path>"` command line argument.
Allows specifying the path to changelog which should be included in your upload to Fabric.

It also adds the `build{flavorName}[buildTypeName]AndUpload` Gradle tasks.
They are just a shortcuts for building and uploading your application to Fabric.

#### Commonly used tools

- script that appends versionName to the output APK
- `project.createSigningConfigFromFile(<properties_file_path>)` function,
 which generates closure accepted by Android's signing config.
- configurable tool for setting up your application's version
- allows configuring project-wide sdk / build tools version setting

#### Important build artifacts all in one place

All tools that produce reports or build artifacts are configured by Toolkit to save said files
into single directory.

Example file structure is shown below:
```
.
├───build
│   └───results
│       ├───checkstyle
│       │   └───app
│       │           developmentDebug-androidTest.html
│       │           developmentDebug-androidTest.xml
│       │           developmentDebug-unitTest.html
│       │           developmentDebug-unitTest.xml
│       │           developmentDebug.html
│       │           developmentDebug.xml
│       │
│       ├───findbugs
│       │   └───app
│       │           developmentDebug.xml
│       │           developmentDebugAndroidTest.xml
│       │           developmentDebugUnitTest.xml
│       │
│       ├───lint
│       │   └───app
│       │       │   lint-results-developmentDebug.html
│       │       │   lint-results-developmentDebug.xml
│       │       │
│       │       └───lint-results-developmentDebug_files
│       │               hololike.css
│       │               lint-error.png
│       │               lint-run.png
│       │               lint-warning.png
│       │
│       └───test
│           └───app
│               └───unitTest
│                       TEST-com.infullmobile.standards.ExampleUnitTest.xml
```

Thanks to that you can configure your CI to archive `build/results` directory without worrying about
saving unnecessary files.

This also simplifies CI tools configuration. For example,
if you're using [Jenkins CI](https://jenkins.io), you might want to configure Checkstyle
reports by adding [Checkstyle Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Checkstyle+Plugin).
In such case, configuration of Checkstyle Report build step would be as simple as supplying path to reports in following format:
```
build/results/checkstyle/**.xml
```

## Installation

Make those changes in `build.gradle` file of your root project:

```
buildscript {
    repositories {
        maven {
            url "https://maven.infullmobile.com/public"
        }
    }
    dependencies {
        classpath 'com.infullmobile:toolkit:0.3.8'
    }
}
```

Now you can apply toolkit plugin in your Android application and library modules:

```
apply plugin: 'com.android.application'
apply plugin: 'io.fabric'
apply plugin: 'com.infullmobile.toolkit'
```

## Usage

You can configure this plugin by adding `toolkit.properties` files to your root project directory and module.
Properties placed in root project directory will be applied to all modules using this plugin.
At the same time, `toolkit.properties` placed in module's directory influence only said module's configuration.
Module properties, if specified, overwrite global ones.

Following sections describe each possible setting. At the end of _Usage_ section you can find example content
of `toolkit.properties` file with all available settings.

#### tempFilesDir
Default: `"build/temp"`

Directory used for storing temp files created during build / check process.

It is relative to root project directory.

#### resultsDir
Default: `"build/results"`

Directory used for storing qa tools reports and build artifacts.

It is relative to root project directory.

#### defaultFabricTeam
Default: `"defaultinternal"`

Team(s) which will be used during upload to Fabric if no team is specified
by using -PfabricTeam="<team(s)>" command line argument.

#### configureBuildTasks
Default: `true`

If true, then Toolkit will add following Gradle tasks:

- `build{flavorName}[buildTypeName]` - issues assemble task for specified flavor
and build type, followed by copying generated APK to results/bin folder
- `build{flavorName}[buildTypeName]` - uploads apk to Fabric. This task is added only if
module has `io.fabric` plugin applied

#### configureFabric
Default: `true`

If true, then Toolkit will process following command line arguments:

- `-PfabricTeam="<team1,team2,...,teamN>"` - specifies which team should receive the build.
Overwrites `defaultFabricTeam` setting.
- `-PfabricChangelogPath="<changelog_file_path>"` - specifies path to changelog file which should be uploaded to Fabric
along with build.

#### configureCoverage
Default: `false`

If set to true, then module will have coverage enabled and Jacoco coverage reports will be copied to results dir.

#### useJava8
Default: `true`

If set to true, source and target compatibility will be set Java 1.8. Jack will be also enabled for Android.

If set to false, Java 1.7 will be used.

Right now FindBugs won't be executed for Java 1.8 projects

#### appendVersionNameToAPK
Default: `true`

If set to true, APK output file names will be modified by appending `versionName` to them.

For example, file `app-development-debug.apk` would be renamed to `app-development-debug-1.2.3-SNAPSHOT.apk`.

#### configureTests
Default: `true`

If set to true, then custom check tasks will also run tests and test results will be copied to `results/test` directory.

#### configureCheckstyle
Default: `true`

If set to true, then custom check tasks will also run Checkstyle and Checkstyle results will be copied to
`results/checkstyle` directory.

#### ignoreCheckstyleFailures
Default: `true`

If set to true, Checkstyle tasks won't interrupt build / check process if rules violations are found.

#### checkstyleConfigFilePath
Default: `null`

Specifies the path to Checkstyle configuration file.
Lookup is done in following order:

- `moduleDirectory/${checkstyleConfigFilePath}`
- `rootProjectDirectory/${checkstyleConfigFilePath}`
- setting is ignored if no matching file is found

#### checkstyleConfigURL
Default: `null`

If not empty, then Checkstyle configuration will be downloaded from specified URL and stored into temp directory.
It will run every time before check tasks. This setting is ignored if `checkstyleConfigFilePath` is configured
and matching configuration file is found locally.

#### configureLint
Default: `true`

If set to true, then custom check tasks will also execute Lint and Lint results will be copied to
`results/lint` directory.

#### ignoreLintErrors
Default: `false`

If set to true, Lint tasks won't interrupt build / check process if rules violations are found.

#### lintConfigFilePath
Default: `null`

Specifies the path to Lint configuration file.
Lookup is done in following order:

- `moduleDirectory/${lintConfigFilePath}`
- `rootProjectDirectory/${lintConfigFilePath}`
- setting is ignored if no matching file is found

#### lintConfigURL
Default: `null`

If not empty, then Lint configuration will be downloaded from specified URL and stored into temp directory.
It will run every time before check tasks. This setting is ignored if `lintConfigFilePath` is configured
and matching configuration file is found locally.

#### configureFindbugs
Default: `true`

If set to true, then custom check tasks will also execute FindBugs and FindBugs results will be copied to
`results/findbugs` directory.

Doesn't work when useJava8 is set to true;.

#### ignoreFindbugsFailures
Default: `true`

If set to true, FindBugs tasks won't interrupt build / check process if rules violations are found.

#### findbugsUseXmlReports
Default: `true`

If set to true, then FindBugs reports will be generated in XML format. Otherwise, HTML format will be used.

#### findbugsExcludedFilesConfigFilePath
Default: `null`

Specifies the path to FindBugs files exclusion configuration file.
Lookup is done in following order:

- `moduleDirectory/${lintConfigFilePath}`
- `rootProjectDirectory/${lintConfigFilePath}`
- setting is ignored if no matching file is found

#### findbugsExcludedFilesConfigURL
Default: `null`

If not empty, then FindBugs files exclusion configuration will be downloaded from specified URL and stored into temp directory.
It will run every time before check tasks. This setting is ignored if `findbugsExcludedFilesConfigFilePath` is configured
and matching configuration file is found locally.

#### configureVersionName
Default: `false`

If set to true, then versionName will be configured in this module. Version name is generated based on
specified `versionFormat`. There are 2 ways to provide variables used when building actual version:

- By creating `version.properties` file in your module's or root project directory.
Example content is presented below:
```
versionMajor=1
versionIteration=23
versionMinor=2
versionSuffix=-SNAPSHOT
```

or you could declare versionName directly inside `version.properties`:
```
versionName=1.23.2-SNAPSHOT
```

- By using command line arguments. Here is the their list:
  - `-PversionMajor="2"`
  - `-PversionIteration="3"`
  - `-PversionMinor="4"`
  - `-PversionSuffix="-SNAPSHOT"`
  - `-PversionName="2.4.3-SNAPSHOT"`

Values provided via command line overwrite the ones found in `version.properties` file.

#### versionFormat
Default: `"${versionMajor}.${versionMinor}.${gitCommitIndex}${versionSuffix}"`

Format in which `versionName` should be configured for your module. Available variables:
- `versionMajor`
- `versionIteration`
- `versionMinor`
- `versionSuffix`
- `gitCommitIndex` - `integer` representing current commit count

#### configureVersionCode
Default: `false`

If set to true, then versionCode will be configured in this module.

Toolkit will look for versionCode inside `version.properties` file. It can be also
passed via `-PversionCode="<value>"` command line argument. If none of those values are found,
then git commit count is assigned to `android.defaultConfig.versionCode` variable.

Values provided via command line overwrite the ones found in `version.properties` file.

#### compileSdkVersion
Default: `null`

If not empty, then compileSdkVersion will be configured in this Android module with provided value.

#### buildToolsVersion
Default: `null`

If not empty, then buildToolsVersion will be configured in this Android module with provided value.

#### minSdkVersion
Default: `null`

If not empty, then minSdkVersion will be configured in this Android module with provided value.

#### targetSdkVersion
Default: `null`

If not empty, then targetSdkVersion will be configured in this Android module with provided value.

#### configureMisc
Default: `true`

If true, the multiple actions will be taken:

- `android.packaging` options will be configured with:
```
exclude 'META-INF/DEPENDENCIES.txt'
exclude 'META-INF/LICENSE.txt'
exclude 'META-INF/NOTICE.txt'
exclude 'META-INF/NOTICE'
exclude 'META-INF/LICENSE'
exclude 'META-INF/DEPENDENCIES'
exclude 'META-INF/notice.txt'
exclude 'META-INF/license.txt'
exclude 'META-INF/dependencies.txt'
exclude 'LICENSE.txt'
exclude 'LICENSE'
```
- Proguard configuration file will be set up for each build type. Removes the need for
specifying it manually each time.

Proguard will look for rules at `${moduleDirectory}/proguard-rules.pro` path.

#### configureCustomFunctions
Default: `true`

If true, then following functions will become available:
- `project.createSigningConfigFromFile("<properties_file_path>")`

Example usage:
```
android {
    signingConfigs {
        release project.createSigningConfigFromFile('app/keystore/development.properties');
    }
    (...)
}
```

`development.properties` file content:
```
key.store=dev.jks
key.alias=developmentAlias
key.store.password=foobar
key.alias.password=baz
```

Where:
- `key.store` - path to your keystore. It is relative to properties file.
- `key.alias` - alias name to used during APK signing.
- `key.store.password` - password for your keystore file
- `key.alias.password` - password to your alias

### Example toolkit.properties file content

```
tempFilesDir=foo/temp
resultsDir=foo/results
compileSdkVersion=23
buildToolsVersion=23.0.3
minSdkVersion=17
targetSdkVersion=23
versionFormat=${versionMajor}-${versionMinor}${versionSuffix}

defaultFabricTeam=foo,bar

configureBuildTasks=true
configureLint=true
configureCoverage=false
configureTests=true
configureFindbugs=true

ignoreLintErrors=false
ignoreCheckstyleFailures=false
ignoreFindbugsFailures=false

findbugsUseXmlReports=false

lintConfigURL=http://example.com/lint-rules.xml
checkstyleConfigURL=http://example.com/checkstyle.xml
findbugsExcludedFilesConfigURL=http://example.com/findbugs_exclude_files.xml

useJava8=false
appendVersionNameToAPK=false

configureMisc=true
configureCustomFunctions=true
```

## Planned work

- Support for Gradle `java` plugin
- Jacoco reports conversion to Cobertura format
- Reading configuration params from command line

## License

Released under the MIT license. See the LICENSE file for more info.
