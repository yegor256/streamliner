#!/bin/bash

set -euo pipefail

if [ ! -f "pom.xml" ]; then
  cd ..
  if [ ! -f "pom.xml" ]; then
    echo "Run jmh.sh from project or misc directory"
    exit 1
  fi
fi

if [ "$#" -eq 0 ]; then
  ARGS="dk.casa.streamliner.jmh.Test.*"
else
  ARGS="$@"
fi

mvn compile

mvn exec:java -Dexec.mainClass=dk.casa.streamliner.asm.TransformASM

CP=$(mvn -q exec:exec -Dexec.executable=echo -Dexec.args="%classpath" 2> /dev/null)

java -Dfile.encoding=UTF-8 -classpath out/asm/:$CP org.openjdk.jmh.Main -rf JSON -rff misc/out.json $ARGS
