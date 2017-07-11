#!/bin/bash

cd backend
./build.sh
cd ..

cd frontend 
./build.sh
cd ..

cd services/flightinformation 
./build.sh 
cd ..

cd services

cd educama-supplier-simulator-backend 
./build.sh
cd ..

cd educama-supplier-simulator-frontend 
./build.sh
cd ..

cd ..
