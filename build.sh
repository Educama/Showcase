#!/bin/bash
set -e # Exit with nonzero exit code if anything fails

(cd backend && ./build.sh)

(cd frontend && ./build.sh)

(cd services/flightinformation &&./build.sh)

(cd services/educama-supplier-simulator-backend && ./build.sh)

(cd services/educama-supplier-simulator-frontend && ./build.sh)
