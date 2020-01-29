package parser;

import org.junit.Test;

public class ParserTest {

    @Test
    public void testForSucess() {
        Parser parser = new Parser("(asdfg)");
        parser.Start();
    }

    @Test(expected=RuntimeException.class)
    public void testForFail(){
        Parser parser = new Parser("(asdfg.........)");
        parser.Start();
    }

}