jar_file = instancias.jar

jar: compile
	jar cvfm $(jar_file) Manifest.txt -C bin .

compile: clean
	mkdir -p bin
	find . -name *.java | xargs javac -cp "lib/*:bin" -d bin
clean:
	rm -rf html
	rm -rf bin/*
	rm -f $(jar_file)
debug: compile_debug
	cd bin
	jbd -sourcepath ../src
compile_debug: clear
	mkdir -p bin
	find . -name *.java | xargs javac -g -cp "lib/*:bin" -d bin
