#! /bin/bash

#Do the version change
./mvnw -B release:prepare -DskipTests

#Remove leftover files
./mvnw release:clean