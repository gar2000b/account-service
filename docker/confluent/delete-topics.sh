#!/bin/bash
docker exec -t broker-1 kafka-topics --delete --topic test-topic --bootstrap-server broker-1:39092
docker exec -t broker-1 kafka-topics --delete --topic account-event-topic --bootstrap-server broker-1:39092
