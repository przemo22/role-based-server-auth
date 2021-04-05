#!/usr/bin/env bash
set -ex

echo "Creating docker vis_test network"
docker network create vis_test

echo "Building and starting application with database inside vis_test network"
docker-compose up -d