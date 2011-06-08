public class Test {
    public static void main(String[] args) { new T().run(); }
}

class T {
  public void f() {
    async(d[0]) {
      System.out.println("a5");
    }
  }
  public void run() {
    finish {
      async(d[0]) {
        System.out.println("a3");
      }
      this.f();
    }
    finish {
      this.f();
      async(d[0]) {
        System.out.println("a4");
      }
    }
  }
}
