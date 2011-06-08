package mhp;

import java.util.ArrayList;

import visitor.DepthFirstVoidVisitor;
import visitor.TreeDumper;
import visitor.TreeFormatter;
import syntaxtree.*;

public class NameVisitor extends DepthFirstVoidVisitor {
	private TreeDumper dumper = new TreeDumper();
	private TreeFormatter formatter = new TreeFormatter();
	private ArrayList<String> dict = new ArrayList<String>();

	public NameVisitor() {
		System.out.println("Here I am");
	}

	public void visit(final File n) {
		System.out.println("This is the File:" + n);
		// nodeListOptional -> ( TopLevelDeclaration() )*
		n.nodeListOptional.accept(this);
		// nodeToken -> < EOF >
		n.nodeToken.accept(this);
		System.out.println("in main visit: " + dict.get(0));
	}

	public void visit(final MainClass n) {
		System.out.println("Here's a Main Class: " + n.identifier.nodeToken);
		n.statement.accept(formatter);
		System.out.print("It's statement:");
		dict.add(n.identifier.nodeToken.tokenImage);
		n.statement.accept(dumper);
		System.out.println();
	}
	
	
}
