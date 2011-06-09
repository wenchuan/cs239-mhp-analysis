import java.io.FileNotFoundException;

import syntaxtree.*;
import visitor.*;
import mhp.*;

public class Main {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		try {
			java.io.InputStream in;
			in= new java.io.FileInputStream("/home/wenchuan/cs239/hw2/original/Series.x10");
			in = System.in;
			INode root = new MiniX10Parser(in).File();
			System.err.println("MiniX10 program parsed successfully.");
			MhpVisitor mhp = new MhpVisitor();
			TreeDumper dumper = new TreeDumper();
			root.accept(dumper);
			//root.accept(new TreeFormatter());
			System.err.println("\n----------MHP----------");
			root.accept(mhp);
		} catch (ParseException e) {
			System.out.println(e.toString());
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
}