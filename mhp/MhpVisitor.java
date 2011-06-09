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
	private String mainClassName;
	
	public MhpNode visit(final File n) {
		// walk the syntax tree, generate MhpNodes
		n.nodeListOptional.accept(this);

		MhpNode root = functions.get(mainClassName + ".main");
		root = root.accept(this, 3);
			
		for (final Iterator<String> e = functions.keySet().iterator(); e.hasNext();) {
			String str = e.next();
			System.out.println("****" + str);
			MhpNode m = functions.get(str);
			System.out.println(m);
		}
		
		System.out.println("------------------\nExpression list:");
		for (int i = 0; i < dict.size(); i++)
			System.out.println("L" + i + ": " + dict.get(i));
			
		System.out.println("------------------\nAfter Unfold:");
		System.out.println(root);

		MhpInfoGenerator gen = new MhpInfoGenerator();
    System.out.println("\n-------------MHP Info------------");
		printMhp(root.accept(gen));
		
		return null;
	}

	private void printMhp(MhpInfo m) {
		Iterator<Pair> it;
		for (it = m.M.iterator(); it.hasNext();){
			Pair p = it.next();
			System.out.println("{\"" + dict.get(p.fst) + "\" ,\"" +  dict.get(p.snd) + "\"}");
		}
	}
	
	public MhpNode unfold(final MhpLabelNode n, final int level) {
		return n;
	}
	
	public MhpNode unfold(final MhpFinishNode n, final int level) {
		return new MhpFinishNode(n.s.accept(this, level));
	}
	
	public MhpNode unfold(final MhpAsyncNode n, int level) {
		return new MhpAsyncNode(n.s.accept(this, level));
	}
	
	public MhpNode unfold(final MhpLoopNode n, int level) {
		return new MhpLoopNode(n.s.accept(this, level));
	}
	
	public MhpNode unfold(final MhpSequenceNode n, int level) {
		if (n.s0 != null) {
			if (n.s1 != null)
				return new MhpSequenceNode(n.s0.accept(this, level), n.s1.accept(this, level));
			else
				return n.s0.accept(this, level);
		}
		else {
			if (n.s1 != null)
				return n.s1.accept(this, level);
			else
				return null;
		}
	}
	
	public MhpNode unfold(final MhpIfelseNode n, int level) {
		if (n.ifnode != null) {
			if (n.elsenode != null)
				return new MhpIfelseNode(n.e.accept(this, level), n.ifnode.accept(this, level), n.elsenode.accept(this, level));
			else
				return new MhpSequenceNode(n.e.accept(this, level), n.ifnode.accept(this, level));
		} else {
			if (n.elsenode != null)
				return new MhpSequenceNode(n.e.accept(this, level), n.elsenode.accept(this, level));
			else
				return n.e.accept(this, level);
		}
	}
	
	public MhpNode unfold(final MhpFunctionNode n, int level) {
		MhpNode m = functions.get(n.name);
		if (level > 0 && m != null)
			return m.accept(this, level--);
		else
			return null;
	}
	
	public MhpNode unfold(final MhpNode n, int level) {
		return n;
	}

	public MhpNode visit(final MainClass n) {
		String className = n.identifier.nodeToken.tokenImage;
		MhpNode nRes = n.statement.accept(this);
		mainClassName = className;
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
					if (((ClassMember) i).nodeChoice.choice instanceof MethodDeclaration) {
						MethodDeclaration m = (MethodDeclaration) (((ClassMember) i).nodeChoice.choice);
						MhpNode sRes = m.block.accept(this);
						functions.put(className + "." + m.identifier.nodeToken, sRes);
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
			dict.add(sw.toString().trim());
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
		//return new MhpSequenceNode(n.expression.accept(this), new MhpAsyncNode(n.block.accept(this)));
		return new MhpAsyncNode(n.block.accept(this));
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
