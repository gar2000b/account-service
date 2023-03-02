#!/bin/bash
docker exec -t broker-1 kafka-topics --alter --topic account-event-topic --partitions 4 --bootstrap-server broker-1:39092
