### com.bitplan.vzjava
[Java Frontend für Volkszähler](http://www.bitplan.com/Vzjava) 

[![Travis (.org)](https://img.shields.io/travis/WolfgangFahl/com.bitplan.vzjava.svg)](https://travis-ci.org/WolfgangFahl/com.bitplan.vzjava)
[![Maven Central](https://img.shields.io/maven-central/v/com.bitplan.vzjava/com.bitplan.vzjava.svg)](https://search.maven.org/artifact/com.bitplan.vzjava/com.bitplan.vzjava/0.0.1/jar)
[![GitHub issues](https://img.shields.io/github/issues/WolfgangFahl/com.bitplan.vzjava.svg)](https://github.com/WolfgangFahl/com.bitplan.vzjava/issues)
[![GitHub issues](https://img.shields.io/github/issues-closed/WolfgangFahl/com.bitplan.vzjava.svg)](https://github.com/WolfgangFahl/com.bitplan.vzjava/issues/?q=is%3Aissue+is%3Aclosed)
[![GitHub](https://img.shields.io/github/license/WolfgangFahl/com.bitplan.vzjava.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![BITPlan](http://wiki.bitplan.com/images/wiki/thumb/3/38/BITPlanLogoFontLessTransparent.png/198px-BITPlanLogoFontLessTransparent.png)](http://www.bitplan.com)

### Documentation
* [Wiki](http://www.bitplan.com/Vzjava)
* [com.bitplan.vzjava Project pages](https://WolfgangFahl.github.io/com.bitplan.vzjava)
* [Javadoc](https://WolfgangFahl.github.io/com.bitplan.vzjava/apidocs/index.html)
* [Test-Report](https://WolfgangFahl.github.io/com.bitplan.vzjava/surefire-report.html)

### Maven dependency

Maven dependency
```xml
<dependency>
  <groupId>com.bitplan.vzjava</groupId>
  <artifactId>com.bitplan.vzjava</artifactId>
  <version>0.0.1</version>
</dependency>
```

[Current release at repo1.maven.org](http://repo1.maven.org/maven2/com/bitplan/vzjava/com.bitplan.vzjava/0.0.1/)

### How to build
```
git clone https://github.com/WolfgangFahl/com.bitplan.vzjava
cd com.bitplan.vzjava
mvn install
```
#### Install and Run
```
mvn package assembly:single
java -jar target/com.bitplan.vzjava-0.0.1-jar-with-dependencies-and-services.jar 
```
#### Screenshot
![demoplot](https://cloud.githubusercontent.com/assets/1336221/24326357/c7520d78-11ac-11e7-82da-ab2ff581d48f.png)

### Version history
* 0.0.0: 2017-03-23 - Projektrahmen mit Tests - noch keine "nützliche" Funktion
* 0.0.1: 2017-03-31 - Hilfeseite mit Links, Demo-Datenbank, Datenbank-Einstellungs-Dialog, Simpler Demo-Plot über REST
* 0.0.1: 2018-08-21 - fixes #10
