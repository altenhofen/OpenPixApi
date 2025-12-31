.PHONY: help build test clean release format check docs

# Extract the current version from pom.xml (e.g., 0.3.0-SNAPSHOT)
PROJECT_VERSION := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
# Remove -SNAPSHOT to suggest the tag name (e.g., 0.3.0)
SUGGESTED_VERSION := $(shell echo $(PROJECT_VERSION) | sed 's/-SNAPSHOT//')

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
	@read -p "Enter Release Version (e.g., 1.0.1): " version; \
	echo "Updating project to version $$version..."; \
	mvn versions:set -DnewVersion=$$version -DprocessAllModules=true -DgenerateBackupPoms=false; \
	git add "**/pom.xml"; \
	git commit -m "Prepare release v$$version"; \
	git tag -a "v$$version" -m "Release v$$version"; \
	git push origin master --tags; \
	echo "Release v$$version pushed! Check Actions."
