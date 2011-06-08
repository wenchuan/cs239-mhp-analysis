package mhp;

import java.io.StringWriter;
import java.util.*;

import visitor.DepthFirstRetVisitor;
import visitor.TreeDumper;
import syntaxtree.*;

public class MhpVisitor extends DepthFirstRetVisitor<MhpNode> {
	
	// Statement dictionary, Integer -> String
	private ArrayList<String> dict = new ArrayList<String>();
	// Function dictionary, String -> MhpNode
	public Map<String, MhpNode> functions = new TreeMap<String, MhpNode>();

	private String currentClassName;
	
	public MhpNode visit(final File n) {
		// walk the syntax tree, generate MhpNodes
		n.nodeListOptional.accept(this);
		
		for (final Iterator<String> e = functions.keySet().iterator(); e.hasNext();) {
			String str = e.next();
			System.out.println(str);
			MhpNode m = functions.get(str);
			System.out.println(m);
		}
		return null;
	}

	public MhpNode visit(final MainClass n) {
		String className = n.identifier.nodeToken.tokenImage;
		MhpNode nRes = n.statement.accept(this);
		functions.put(className + ".main", nRes);
		return null;
	}

	public MhpNode visit(final ClassDeclaration n) {
		String className = n.identifier.nodeToken.tokenImage;
		currentClassName = className;
		if (n.nodeListOptional.present()) {
			for (final Iterator<INode> e = n.nodeListOptional.elements(); e.hasNext();) {
				INode i = e.next();
				if (i instanceof ClassMember) {
					// TODO N.B. ConstantDeclaration in nodeListOptinal could contain expressions
					if (((ClassMember) i).nodeChoice.choice instanceof MethodDeclaration) {
						MethodDeclaration m = (MethodDeclaration) (((ClassMember) i).nodeChoice.choice);
						System.out.println("This is a method:" + className
								+ "." + m.identifier.nodeToken);
						MhpNode sRes = m.block.accept(this);
						if (sRes != null)
							functions.put(className + "."
									+ m.identifier.nodeToken, sRes);
					}
				}
			}
		}
		return null;
	}

	public MhpNode visit(final Block n) {
		if (!n.nodeListOptional.present())
			return null;
		final Iterator<INode> e = n.nodeListOptional.elements();
		BlockStatement i = (BlockStatement) e.next();
		MhpNode nRes = i.accept(this);
		for (; e.hasNext();) {
			i = (BlockStatement) e.next();
			MhpNode m = i.accept(this);
			if (m != null)
				nRes = new MhpSequenceNode(nRes, i.accept(this));
		}
		return nRes;
	}

	public MhpNode visit(final BlockStatement n) {
		return n.nodeChoice.accept(this);
	}

	public MhpNode visit(final Statement n) {
		return n.nodeChoice.accept(this);
	}

	public MhpNode visit(final Assignment n) {
		return new MhpSequenceNode(n.expression1.accept(this), n.expression
				.accept(this));
	}

	public MhpNode visit(final Expression n) {
		if (n.nodeChoice.choice instanceof DotMethodCall)
			return n.nodeChoice.choice.accept(this);
		else {
			StringWriter sw = new StringWriter();
			n.accept(new TreeDumper(sw));
			dict.add(sw.toString());
			return new MhpLabelNode(dict.size() - 1);
		}
	}
	
	public MhpNode visit(final DotMethodCall n) {
		String className;
		if (n.primaryExpression.nodeChoice.choice instanceof This)
			className = currentClassName;
		else if (n.primaryExpression.nodeChoice.choice instanceof AllocationExpression) {
			className = ((NewObject)((AllocationExpression)n.primaryExpression.nodeChoice.choice).nodeChoice.choice).identifier.nodeToken.tokenImage;
		} else
			className = ((Identifier)n.primaryExpression.nodeChoice.choice).nodeToken.tokenImage;
		return new MhpFunctionNode(className + "." + n.identifier.nodeToken.tokenImage);
	}

	public MhpNode visit(final AsyncStatement n) {
		// Seq place expression and the async expression
		return new MhpSequenceNode(n.expression.accept(this), new MhpAsyncNode(
				n.block.accept(this)));
	}

	public MhpNode visit(final FinishStatement n) {
		return new MhpFinishNode(n.statement.accept(this));
	}

	public MhpNode visit(final IfStatement n) {
		if (n.nodeOptional.present())
			return new MhpIfelseNode(n.expression.accept(this), n.statement.accept(this), n.nodeOptional.accept(this));
		else
			return new MhpSequenceNode(n.expression.accept(this), n.statement.accept(this));
	}

	public MhpNode visit(final LoopStatement n) {
		return new MhpLoopNode(new MhpSequenceNode(n.expression.accept(this),
				n.statement.accept(this)));
	}

	public MhpNode visit(final PostfixStatement n) {
		return n.expression.accept(this);
	}

	public MhpNode visit(final PrintlnStatement n) {
		return n.expression.accept(this);
	}

	public MhpNode visit(final ReturnStatement n) {
		return n.nodeOptional.accept(this);
	}

	public MhpNode visit(final ThrowStatement n) {
		return n.expression.accept(this);
	}

	public MhpNode visit(final WhileStatement n) {
		return new MhpLoopNode(new MhpSequenceNode(n.expression.accept(this),
				n.statement.accept(this)));
	}
}