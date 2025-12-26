.PHONY: help build test clean release format check docs

help:
	@echo "Available targets:"
	@echo "  build    - Compile the project"
	@echo "  test     - Run tests"
	@echo "  clean    - Remove build artifacts"
	@echo "  format   - Format code"
	@echo "  check    - Run all checks (lint, test, coverage)"
	@echo "  docs     - Generate documentation"
	@echo "  release  - Prepare a release"

build:
	mvn -B clean package -DskipTests

test:
	mvn -B test

clean:
	mvn clean
	rm -rf docs/generated/

format:
	mvn spotless:apply

check: format
	mvn -B clean verify

checkSkipDoc:
	mvn clean verify -Dmaven.javadoc.skip=true

docs:
	mvn javadoc:javadoc
	@echo "Docs generated in target/site/apidocs/"

release:
	@echo "Current version: $$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)"
	@read -p "New version: " version; \
	mvn versions:set -DnewVersion=$$version; \
	mvn versions:commit
	@echo "Don't forget to update CHANGELOG.md"
