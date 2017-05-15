#!/usr/bin/env bash
# This script uploads the Serenity test reports to https://github.com/Educama/Educama.github.io using private/public key authentication (private key is decrypted by Travis CI). The reports can be viewed at https://educama.github.io
# The idea of this script is based on the concept from https://gist.github.com/domenic/ec8b0fc8ab45f39403dd
set -e # Exit with nonzero exit code if anything fails

START_TIME=$SECONDS

# Determine hash of newest commit
SHA=`git rev-parse --verify HEAD`
echo "Creating Serenity Report for commit $SHA"

REPO="git@github.com:Educama/Educama.github.io.git"
GIT_USER_NAME="Tobias Schaefer using Travis CI"
GIT_USER_EMAIL="tobias.schaefer@novatec-gmbh.de"
DIRECTORY="temp"

if [ "$TRAVIS_REPO_SLUG" != "Educama/Showcase" ] || [ "$TRAVIS_PULL_REQUEST" != "false" ] || [ "$TRAVIS_BRANCH" != "master" ]; then
    echo "Not uploading Serenity reports because repositories other than Educama/Showcase, pull requests and commits to branches other than 'master' are ignored:"
    echo "   TRAVIS_REPO_SLUG=$TRAVIS_REPO_SLUG"
    echo "   TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST"
    echo "   TRAVIS_BRANCH=$TRAVIS_BRANCH"
    exit 0
fi

echo "Step 1: Setup authentication"
# see https://docs.travis-ci.com/user/encrypting-files/
openssl aes-256-cbc -K $encrypted_d627a929dec2_key -iv $encrypted_d627a929dec2_iv -in deploy_key.enc -out deploy_key -d
chmod 600 deploy_key
eval `ssh-agent -s`
ssh-add deploy_key
ssh-add -l

echo "Step 2: Cloning repository $REPO"
git clone $REPO $DIRECTORY
cd $DIRECTORY
git config user.name "$GIT_USER_NAME"
git config user.email "$GIT_USER_EMAIL"

echo "Step 3: Updating working copy"
rm -rf site/serenity/**/*
mkdir -p site/serenity
cp -r ../target/site/serenity/* site/serenity

echo "Step 4: Commit files"
git add .
git commit -m "Serenity Reports for commit ${SHA}"

echo "Step 5: Push changes"
git push origin master:master

echo "Step 6: Show git status"
git status

echo "Successfully pushed changes!"

ELAPSED_TIME=$(($SECONDS - $START_TIME))
echo "Upload serenity reports duration: $ELAPSED_TIME seconds"

