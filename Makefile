.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.PHONY: all clean test
.SECONDARY:
.PRECIOUS:

PACKAGES=java.util java.lang.reflect java.lang.ref java.lang java.util.stream

all: out.csv

out/classes: pom.xml
	mvn clean compile

out.csv: out/classes
	opts=()
	for p in $(PACKAGES); do
		opts+=("--add-opens java.base/$${p}=ALL-UNNAMED")
	done
	if java -version 2>&1 | head -1 | grep -qv \"1\.8; then
		export MAVEN_OPTS="$${opts[*]}"
	fi
	mvn exec:java -Dexec.mainClass=dk.casa.streamliner.asm.TransformASM
	CP=$$(mvn -q exec:exec -Dexec.executable=echo -Dexec.args="%classpath" 2>/dev/null)
	java -Dfile.encoding=UTF-8 -classpath "out/asm/:$${CP}" org.openjdk.jmh.Main -rf CSV -rff "out.csv" "dk.casa.streamliner.jmh.Test.*"

clean:
	mvn clean