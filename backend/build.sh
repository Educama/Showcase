#!/usr/bin/env bash
set -e # Exit with nonzero exit code if anything fails

START_TIME=$SECONDS

echo -n "Java Version: " && java -version
echo -n "Javac Version: " javac -version

if [ "$TRAVIS_REPO_SLUG" == "Educama/Showcase" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
  echo "Building with Camunda Enterprise Edition"
  cp .travis.settings.xml $HOME/.m2/settings.xml
  ./mvnw install -P enterprise
else
  echo "Building with Camunda Community Edition because repositories other than Educama/Showcase, pull requests and commits to branches other than 'master' do not support the CamundaBPM Enterprise Edition:"
  echo "   TRAVIS_REPO_SLUG=$TRAVIS_REPO_SLUG"
  echo "   TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST"
  echo "   TRAVIS_BRANCH=$TRAVIS_BRANCH"
  ./mvnw install
fi

echo "Directory content after build:"
ls -al

ELAPSED_TIME=$(($SECONDS - $START_TIME))
echo "Backend Build & test duration: $ELAPSED_TIME seconds"
