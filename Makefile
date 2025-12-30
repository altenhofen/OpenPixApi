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
	@echo "Current project version: $(PROJECT_VERSION)"
	@read -p "Enter Release Version (default: $(SUGGESTED_VERSION)): " v; \
	VERSION=$${v:-$(SUGGESTED_VERSION)}; \
	echo "This will create and push tag: v$$VERSION"; \
	read -p "Are you sure? [y/N] " confirmation; \
	if [ "$$confirmation" = "y" ]; then \
		git tag -a "v$$VERSION" -m "Release v$$VERSION"; \
		git push origin "v$$VERSION"; \
		echo "Tag v$$VERSION pushed! Watch the deployment here: https://github.com/$(shell git config --get remote.origin.url | sed 's/.*github.com[:/]\(.*\).git/\1/')/actions"; \
		echo "IMPORTANT: Wait for the Action to finish, then run 'git pull' to get the new SNAPSHOT version."; \
	else \
		echo "Release cancelled."; \
	fi
