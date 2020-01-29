//import static org.junit.Assert.*;
//import org.junit.Test;
import parser.Parser;

public class Test {

    public static void main(String[] args) {
        Parser parser = new Parser("((a|b)*asdf)");
        parser.Start();
        System.out.println("asdfg");
    }

/*    @Test
    public void test() {


    }*/

}