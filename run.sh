#!/bin/bash

LIBRARIES=lib/guava-r09.jar:lib/lucene-core-3.0.3.jar:lib/text-0.1.0.jar:lib/text-0.1.0-sources.jar:lib/twitter-text-1.1.8.jar

mkdir -p bin
javac -d bin -cp $LIBRARIES src/edu/washington/twitter_nlp/*.java
java -cp $LIBRARIES:bin edu.washington.twitter_nlp.NerUsageExample
