# I3A Agents Web Client

| | **Status** |
|---|:----|
| **circleCI** |[![CircleCI](https://circleci.com/gh/asw-i3a/agents-web-client.svg?style=svg)](https://circleci.com/gh/asw-i3a/agents-web-client)
| **code coverage** |[![codecov](https://codecov.io/gh/asw-i3a/agents-web-client/branch/master/graph/badge.svg)](https://codecov.io/gh/asw-i3a/agents-web-client)
| **code quality** |[![Codacy Badge](https://api.codacy.com/project/badge/Grade/93c9cdf439f7444481c42c426c0e988f)](https://www.codacy.com/app/colunga91/agents-web-client?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=asw-i3a/agents-web-client&amp;utm_campaign=Badge_Grade)
| **latest build** |[![Docker Badge](https://img.shields.io/badge/docker%20image-latest-blue.svg)](https://hub.docker.com/r/incisystem/agents_web_client/)

This client forms part of platform called GestUsers, if you don't know about it, we encourage you to see this other [repo](https://github.com/asw-i3a/project-documentation) first.

From the control panel provided by this client agents are able to submit new incidents and see the evolution of the ones submitted.

### Package
|Group|Artifact|
|-----|--------|
|io.github.asw.i3a|agents.web.client|

### Authors
- [Guillermo Facundo Colunga](https://github.com/thewilly)
- [Carlos García Hernández](https://github.com/CarlosGarciaHdez)
- [Victor Suárez Fernández](https://github.com/ByBordex)

## System requirements
As the project is developed in java macOS, Windows and Linux distributions are natively supported. Of course you will need the **latest JDK available**. Also, depending on where are you going to run the database, you will need **internet connection or MongoDB** installed and running on your machine.

### Java Development Kit (JDK)
A Java Development Kit (JDK) is a program development environment for writing Java applets and applications. It consists of a runtime environment that "sits on top" of the operating system layer as well as the tools and programming that developers need to compile, debug, and run applets and applications written in the Java programming language.

If you do not has the latest stable version download you can download it [here](http://www.oracle.com/technetwork/java/javase/downloads).

### MongoDB
This project uses MongoDB as the database. You can check how to use it on [MongoDB install](https://github.com/Arquisoft/participants_i2b/wiki/MongoDB). By defatult a dummy server is up and running, it´s configured at the file `applications.properties`. Change this configuration as needed, should not interfeer with the module itself.

### Apache kafka
This module uses apache kafka in order to notify any kafka consumer about an incident submission. You will need to download or use any online kafka server and, of course, configure it at the application.propperties file.

## Building the project
It is as simple as running `mvn install`. Or if you want to run it you can use `mvn mvn spring-boot:run`. Also you can download the last docker image and runnin it with `docker run _image-name-here_`. 
