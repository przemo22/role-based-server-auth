#!/usr/bin/env bash
set -ex

echo "Stopping docker compose"
docker-compose down

echo "Removing vis_test network"
docker network rm vis_test
