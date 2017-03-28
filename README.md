# com.bitplan.vzjava
![Volkszähler-Logo](http://wiki.volkszaehler.org/_media/logo.png) Java Frontend für [Volkszähler](http://volkszaehler.org/)

# Wünsche und Fehler
https://github.com/WolfgangFahl/com.bitplan.vzjava/issues

# Projekt
[![Build Status](https://travis-ci.org/WolfgangFahl/com.bitplan.vzjava.svg?branch=master)](https://travis-ci.org/WolfgangFahl/com.bitplan.vzjava)

* Open Source auf https://github.com/WolfgangFahl/com.bitplan.vzjava
* [Apache Lizenz](https://www.apache.org/licenses/LICENSE-2.0)
* Maven basiertes Java Projekt mit JUnit4 Tests.

# Screenshot
![demoplot](https://cloud.githubusercontent.com/assets/1336221/24326357/c7520d78-11ac-11e7-82da-ab2ff581d48f.png)

# Installation
```
git clone https://github.com/WolfgangFahl/com.bitplan.vzjava
cd com.bitplan.vzjava
mvn package assembly:single
java -jar target/com.bitplan.vzjava-0.0.1-jar-with-dependencies-and-services.jar 
```
# Usage
http://localhost:8380

# Tests
Es gibt 8 JUnit tests für version 0.0.1

## Tests laufen lassen
```
mvn test
...
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

# Versionsgeschichte
* 0.0.0: 2017-03-23 - Projektrahmen mit Tests - noch keine "nützliche" Funktion
* 0.0.1: 2017-03-28 - Hilfeseite mit Links, Demo-Datenbank, Datenbank-Einstellungs-Dialog, Simpler Demo-Plot über REST


