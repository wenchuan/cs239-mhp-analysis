run:
	javac Main.java
	java Main < MapReduce.x10

rebuild: clean
	jtb -jd -pp -printer miniX10.jj
	javacc jtb.out.jj
	javac Main.java

test:
	javac Main.java
	echo "mapreduce  --------------------"
	java Main < MapReduce.x10 
	echo "series-------------------------"
	java Main < Series.x10 

clean:
	rm -rf *.class visitor syntaxtree Parse* Mini* Token* JavaCharStream.java
