#!/bin/bash
docker exec -t broker-1 kafka-topics --create --topic test-topic --bootstrap-server broker-1:39092 --replication-factor 3 --partitions 3
docker exec -t broker-1 kafka-topics --create --topic account-event-topic --bootstrap-server broker-1:39092 --replication-factor 3 --partitions 3
