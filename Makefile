run:
	javac Main.java
	java Main < MapReduce.x10

build: clean
	jtb -jd -pp -printer -f miniX10.jj
	javacc jtb.out.jj
	javac Main.java
	ctags -R .

test:
	javac Main.java
	echo "mapreduce  --------------------"
	java Main < MapReduce.x10 
	echo "series-------------------------"
	java Main < Series.x10 

clean:
	rm -rf *.class visitor/*.class syntaxtree/*.class mhp/*.class

realclean: clean
	rm -rf *.class visitor syntaxtree Parse* Mini* Token* JavaCharStream.java tags
