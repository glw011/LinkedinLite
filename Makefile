ifeq ($(OS), Windows_NT)
	CLEAR_CMD = cls
	GRADLEW_CMD = gradlew
else
	CLEAR_CMD = clear
	GRADLEW_CMD = ./gradlew
endif

all:
	$(CLEAR_CMD)
	$(GRADLEW_CMD) build
	$(GRADLEW_CMD) run

debug:
	$(CLEAR_CMD)
	$(GRADLEW_CMD) build --debug
	$(GRADLEW_CMD) run --debug

clean:
	$(CLEAR_CMD)
	$(GRADLEW_CMD) clean