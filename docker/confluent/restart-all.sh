#!/bin/bash
/home/azureuser/apps/down-es-services.sh
sleep 1
echo "Account services stopped"
./bounce-confluent.sh
sleep 37
echo "Confluent cluster restarted"
./create-topics.sh
sleep 1
echo "Topics created"
/home/azureuser/apps/up-es-services.sh
sleep 30
echo "Account services started"
./list-topics.sh
echo "All services restarted"
