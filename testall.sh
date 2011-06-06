#!/bin/bash

rm *.class
rm syntaxtree/*.{java,class}
rm visitor/*.class
rm visitor/TreeDumper.java      
rm visitor/TreeFormatter.java

jtb -printer miniX10.jj
javacc jtb.out.jj
javac Main.java
jjdoc miniX10.jj

echo "mapreduce  --------------------"
java Main < MapReduce.x10 
echo "series-------------------------"
java Main < Series.x10 
