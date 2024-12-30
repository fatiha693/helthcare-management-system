#!/bin/bash
mkdir -p bin
javac -cp "lib/*:." -d bin src/main/java/*.java
java -cp "bin:lib/*" Main 