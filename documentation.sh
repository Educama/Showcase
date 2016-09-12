#!/usr/bin/env bash
set -e # Exit with nonzero exit code if anything fails

REPO="https://github.com/tobiasschaefer/tobiasschaefer.github.io.git"
DIRECTORY="documentation"
SHA=`git rev-parse --verify HEAD`

echo "Uploading documentation of $SHA"

git clone $REPO $DIRECTORY

# Clean out existing contents
rm -rf $DIRECTORY/**/*

cp -r target/site/serenity/* $DIRECTORY

cd $DIRECTORY
git config user.name "Travis CI"
git config user.email "$COMMIT_AUTHOR_EMAIL"

git status

git diff

git add .
git commit -m "Test results of: ${SHA}"

