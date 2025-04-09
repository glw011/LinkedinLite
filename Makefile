all:
	clear
	./gradlew build
	./gradlew run

debug:
	clear
	./gradlew build --debug
	./gradlew run --debug

clean:
	clear
	./gradlew clean