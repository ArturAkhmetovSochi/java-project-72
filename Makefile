check-deps:
	make -C app ./gradlew dependencyUpdates -Drevision=release

dev:
	make -C app ./gradlew run

setup:
	make -C app gradle wrapper --gradle-version 8.5

clean:
	make -C app ./gradlew clean

build:
	make -C app ./gradlew clean build

start: dev

install:
	make -C app ./gradlew installDist

lint:
	make -C app ./gradlew checkstyleMain

test:
	make -C app ./gradlew test

image-build:
	make -C app docker build -t ArturAkhmetovSochi/java-project-72:latest .

image-push:
	make -C app docker push ArturAkhmetovSochi/java-project-72:latest

.PHONY: build