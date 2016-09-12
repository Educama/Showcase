#!/usr/bin/env sh
set -e # Exit with nonzero exit code if anything fails

echo "Uploading documentation"

REPO = "https://github.com/tobiasschaefer/tobiasschaefer.github.io.git"
DIRECTORY = "documentation"

git clone $REPO $DIRECTORY

# Clean out existing contents
rm -rf $DIRECTORY/**/*

cp -r target/site/serenity/* $DIRECTORY

cd $DIRECTORY
git config user.name "Travis CI"
git config user.email "$COMMIT_AUTHOR_EMAIL"

git status

git diff


