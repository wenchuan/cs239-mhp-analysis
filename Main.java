import syntaxtree.*;
import visitor.*;
import mhp.*;

public class Main {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		try {
			java.io.InputStream in;
			in = System.in;
			INode root = new MiniX10Parser(in).File();
			System.err.println("MiniX10 program parsed successfully.");
			root.accept(new TreeDumper());
			System.err.println("\n----------MHP----------");
			root.accept(new MhpVisitor());
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}
