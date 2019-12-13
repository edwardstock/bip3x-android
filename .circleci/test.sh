#!/usr/bin/env bash
set -x

BIP39_LIBS=${PWD}/_libs/lib ./gradlew lint test
