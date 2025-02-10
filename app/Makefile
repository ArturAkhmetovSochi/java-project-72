check-deps:
	./gradlew dependencyUpdates -Drevision=release

dev:
	./gradlew run

setup:
	gradle wrapper --gradle-version 8.5

clean:
	./gradlew clean

build:
	./gradlew clean build

start: dev

install:
	./gradlew installDist

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

image-build:
	docker build -t ArturAkhmetovSochi/java-project-72:latest .

image-push:
	docker push ArturAkhmetovSochi/java-project-72:latest

.PHONY: build