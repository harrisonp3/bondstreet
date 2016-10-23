#!/bin/sh
sudo chmod +x run.sh

javac -d bin -sourcepath src -classpath ./lib/okhttp-3.4.1.jar:./lib/okio-1.11.0.jar:./lib/twitter4j-core-4.0.4.jar src/Main.java

java -classpath bin:./lib/okhttp-3.4.1.jar:./lib/okio-1.11.0.jar:./lib/twitter4j-core-4.0.4.jar Main