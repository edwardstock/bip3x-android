#!/usr/bin/env bash
set -x

./gradlew lint test -PjvmArgs="-Djava.library.path=${PWD}/_libs/lib"
