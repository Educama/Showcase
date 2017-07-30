#!/bin/bash
set -e # Exit with nonzero exit code if anything fails

(cd acceptance-tests && ./mvnw clean verify -Dwebdriver.firefox.bin="/opt/firefox/firefox-bin")

