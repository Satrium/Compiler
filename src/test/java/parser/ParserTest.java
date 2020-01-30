package parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ParserTest {

    @Test()
    public void testForSucess() {
        Parser parser = new Parser("(asdfg)#");
        parser.start();
    }

    @Test()
    public void testForFail(){
        Parser parser = new Parser("(asdfg.........)#");
        Assertions.assertThrows(RuntimeException.class, () -> {
            parser.start();
        }); 
    }

}