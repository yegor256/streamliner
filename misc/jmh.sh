#!/bin/bash

set -euo pipefail

self=$(dirname "$0")

if [ "$#" -eq 0 ]; then
  ARGS="dk.casa.streamliner.jmh.Test.*"
else
  ARGS="$@"
fi

mvn --file "${self}/../pom.xml" compile

packages=(
  java.util
  java.lang.reflect
  java.lang.ref
  java.lang
  java.util.stream
)
opts=()
for p in "${packages[@]}"; do
  opts+=("--add-opens java.base/${p}=ALL-UNNAMED")
done
export "MAVEN_OPTS=${opts[*]}"

mvn --file "${self}/../pom.xml" exec:java -Dexec.mainClass=dk.casa.streamliner.asm.TransformASM

CP=$(mvn -q --file "${self}/../pom.xml" exec:exec -Dexec.executable=echo -Dexec.args="%classpath" 2>/dev/null)

java -Dfile.encoding=UTF-8 -classpath "${self}/../out/asm/:${CP}" org.openjdk.jmh.Main -rf JSON -rff --file "${self}/out.json" "${ARGS}"
