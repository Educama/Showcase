#!/usr/bin/env bash
set -e # Exit with nonzero exit code if anything fails

START_TIME=$SECONDS

echo -n "Java Version: " && java -version
echo -n "Javac Version: " javac -version

echo "Copy settings.xml:"
cp ../settings.xml $HOME/.m2/settings.xml

echo "Start maven:"
./mvnw install -P enterprise 

echo "Directory content after build:"
ls -al

ELAPSED_TIME=$(($SECONDS - $START_TIME))
echo "Backend Build & test duration: $ELAPSED_TIME seconds"
