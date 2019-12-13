#!/usr/bin/env bash

rm -rf _libs
mkdir _libs
LIBS_DIR=${PWD}/_libs

cd bip3x/src/main/cpp/bip3x

rm -rf _build
mkdir _build && cd _build

cmake .. -DCMAKE_BUILD_TYPE=Debug \
  -DENABLE_BIP39_TESTS=Off -DENABLE_BIP39_C=Off \
  -DENABLE_BIP39_SHARED=On -DENABLE_BIP39_JNI=On \
  -DENABLE_BIP39_CONAN=Off -DENABLE_TOOLBOX_TEST=Off \
  -DENABLE_TOOLBOX_CONAN=Off -DENABLE_SHARED=Off \
  -DCMAKE_INSTALL_PREFIX=${LIBS_DIR}

make -j4
make install
