SHELL := /bin/bash
.PHONY: help build test clean release format check docs check-status

# Extract the current version from pom.xml (e.g., 0.3.0-SNAPSHOT)
BRANCH := master

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


release: check-status
	@if [ -z "$(v)" ]; then \
		echo "Error: Version argument missing."; \
		echo "Usage: make release v=1.0.5"; \
		exit 1; \
	fi
	@echo "Preparing release v$(v) on branch $(BRANCH)..."
	@mvn versions:set -DnewVersion=$(v) -DprocessAllModules=true -DgenerateBackupPoms=false
	@git add pom.xml */pom.xml
	@git commit -m "Prepare release v$(v)"
	@git tag -a "v$(v)" -m "Release v$(v)"
	@git push origin $(BRANCH) --tags
	@echo "✅ Done! v$(v) pushed. Watch GitHub Actions for deployment."

check-status:
	@# Check if we are on the correct branch
	@if [ "$$(git rev-parse --abbrev-ref HEAD)" != "$(BRANCH)" ]; then \
		echo "Error: You are not on the '$(BRANCH)' branch."; \
		exit 1; \
	fi
	@# Check if the working directory is clean (ignore untracked files)
	@if ! git diff-index --quiet HEAD --; then \
		echo "Error: Your git working directory is not clean. Commit or stash changes first."; \
		exit 1; \
	fi
