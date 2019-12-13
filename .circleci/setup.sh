#!/usr/bin/env bash
set -x

sudo apt-get update
sudo apt-get install -y git gcc g++ gdb make curl wget

if [ ! -d "/tmp/pkgs" ]; then
  sudo mkdir -p /tmp/pkgs
  sudo chmod 0777 /tmp/pkgs
fi

# fetching cmake

if [ ! -f "/tmp/pkgs/cmake.sh" ]; then
  sudo wget -O /tmp/pkgs/cmake.sh https://github.com/Kitware/CMake/releases/download/v3.10.2/cmake-3.10.2-Linux-x86_64.sh
fi

if [ ! -f "/usr/bin/cmake" ]; then
  sudo sh /tmp/pkgs/cmake.sh --skip-license --prefix=/usr
fi
