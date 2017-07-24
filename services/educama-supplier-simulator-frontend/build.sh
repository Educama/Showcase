#!/usr/bin/env bash
set -e # Exit with nonzero exit code if anything fails

START_TIME=$SECONDS

echo -n "NPM Version: " && npm --version
echo -n "Node Version: " && node --version

npm install
npm run build
#ng test

echo "Directory content after build:"
ls -al

ELAPSED_TIME=$(($SECONDS - $START_TIME))
echo "educama-supplier-simulator-frontend Build & test duration: $ELAPSED_TIME seconds"
