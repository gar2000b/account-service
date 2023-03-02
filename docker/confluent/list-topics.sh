#!/bin/bash
docker exec -t broker-1 kafka-topics --list --bootstrap-server broker-1:39092
