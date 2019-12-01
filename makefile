OUT = compiled
JFLAGS = -d "$(OUT)"
JC = javac $(JFLAGS)

CLASSES = \
	Jott.java

jott:
	@$(RM) -rf $(OUT)
	@mkdir $(OUT)
	@$(JC) Jott.java
	@cp Text -r ./$(OUT)
	@cp samples -r ./$(OUT)
	@cp tester.py ./$(OUT)
	@echo "To run Jott, please cd to compiled and use:"
	@echo "java Jott prog.j"

clean:
	$(RM) -rf $(OUT)

