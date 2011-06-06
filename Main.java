import syntaxtree.*;
import visitor.*;

public class Main {
  public static void main(String [] args) {
    try {
      INode root = new MiniX10Parser(System.in).File();
      System.err.println("MiniX10 program parsed successfully.");
      root.accept(new TreeDumper());
    }
    catch (ParseException e) {
      System.out.println(e.toString());
    }
  }
}
