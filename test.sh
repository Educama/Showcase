#!/bin/bash

cd acceptance-tests

./mvnw clean verify -Dwebdriver.firefox.bin="/opt/firefox/firefox-bin"

cd ..
