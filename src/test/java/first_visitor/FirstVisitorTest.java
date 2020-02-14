package first_visitor;

import org.junit.jupiter.api.Test;
import parser.Parser;
import syntaxtree.*;

public class FirstVisitorTest {

    private boolean equals(Visitable expected, Visitable visited)
    {
        if (expected == null && visited == null) return true;
        if (expected == null || visited == null) return false;
        if (expected.getClass() != visited.getClass()) return false;
        if (expected.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) expected;
            BinOpNode op2 = (BinOpNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos) &&
                    equals(op1.left, op2.left) &&
                    equals(op1.right, op2.right);
        }
        if (expected.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) expected;
            UnaryOpNode op2 = (UnaryOpNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos) &&
                    equals(op1.subNode, op2.subNode);
        }
        if (expected.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) expected;
            OperandNode op2 = (OperandNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos);
        }
        throw new IllegalStateException(
                String.format( "Beide Wurzelknoten sind Instanzen der Klasse %1$s !"
                                + " Dies ist nicht erlaubt!",
                        expected.getClass().getSimpleName())
        );
    }

    public Visitable treeFromParser(){
        //Baum aus Vorlesung (a|b)+(b|c)*#
        Parser parser = new Parser("(a|b)+(b|c)*d#");
        return parser.start();
    }

    public Visitable selfMadeTree(){
        Visitable selfMadeTree = new Visitable() {
            @Override
            public void accept(Visitor visitor) {

            }
        };

        //Baum aus Vorlesung (a|b)+(b|c)*#
        OperandNode a = new OperandNode("a");
        a.position=1;
        a.nullable=false;
        a.firstpos.add(1);
        a.lastpos.add(1);
        OperandNode b = new OperandNode("b");
        b.position=2;
        b.nullable=false;
        b.firstpos.add(2);
        b.lastpos.add(2);
        OperandNode b2 = new OperandNode("b");
        b2.position=3;
        b2.nullable=false;
        b2.firstpos.add(3);
        b2.lastpos.add(3);
        OperandNode c = new OperandNode("c");
        c.position=4;
        c.nullable=false;
        c.firstpos.add(4);
        c.lastpos.add(4);
        OperandNode d = new OperandNode("d");
        d.position=5;
        d.nullable=true;
        d.firstpos.add(5);
        d.lastpos.add(5);
        OperandNode hash = new OperandNode("#");
        hash.position=6;
        hash.nullable=true;
        hash.firstpos.add(6);
        hash.lastpos.add(6);

        BinOpNode oder  = new BinOpNode("|", a, b);
        oder.nullable=false;
        oder.firstpos.add(1);
        oder.firstpos.add(2);
        oder.lastpos.add(1);
        oder.lastpos.add(2);
        BinOpNode oder2 = new BinOpNode("|", b2, c);
        oder2.firstpos.add(3);
        oder2.firstpos.add(4);
        oder2.lastpos.add(3);
        oder2.lastpos.add(4);

        UnaryOpNode plus = new UnaryOpNode("+", oder);
        plus.nullable=false;
        plus.firstpos.add(1);
        plus.firstpos.add(2);
        plus.lastpos.add(1);
        plus.lastpos.add(2);
        UnaryOpNode kleenscheHuelle = new UnaryOpNode("*", oder2);
        kleenscheHuelle.nullable=true;
        kleenscheHuelle.firstpos.add(3);
        kleenscheHuelle.firstpos.add(4);
        kleenscheHuelle.lastpos.add(3);
        kleenscheHuelle.lastpos.add(4);

        BinOpNode konkatenation = new BinOpNode("°", plus, kleenscheHuelle);
        konkatenation.nullable=false;
        konkatenation.firstpos.add(1);
        konkatenation.firstpos.add(2);
        konkatenation.lastpos.add(1);
        konkatenation.lastpos.add(2);
        konkatenation.lastpos.add(3);
        konkatenation.lastpos.add(4);
        BinOpNode konkatenation2 = new BinOpNode("°", konkatenation, d);
        konkatenation2.nullable=false;
        konkatenation2.firstpos.add(1);
        konkatenation2.firstpos.add(2);
        konkatenation2.lastpos.add(5);
        BinOpNode konkatenation3 = new BinOpNode("°", konkatenation2, hash);
        konkatenation3.nullable=false;
        konkatenation3.firstpos.add(1);
        konkatenation3.firstpos.add(2);
        konkatenation3.lastpos.add(6);
        return selfMadeTree;
    }

    @Test
    public void testCorrectTree(){
        Visitable treeFromParser = treeFromParser();
        Visitable selfMadeTree = selfMadeTree();
        equals(treeFromParser, selfMadeTree);


    }
}
