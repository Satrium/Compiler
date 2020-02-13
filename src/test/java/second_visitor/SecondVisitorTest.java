package second_visitor;

import org.junit.jupiter.api.Test;
import syntaxtree.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondVisitorTest {

    @Test
    public void testCorrectFollowposTable() {
        // a|b
        OperandNode optionLeft = new OperandNode("a");
        optionLeft.position = 1;
        optionLeft.firstpos.addAll(Arrays.asList(1));
        optionLeft.lastpos.addAll(Arrays.asList(1));
        OperandNode optionRight = new OperandNode("b");
        optionRight.position = 2;
        optionRight.firstpos.addAll(Arrays.asList(2));
        optionRight.lastpos.addAll(Arrays.asList(2));
        BinOpNode option =  new BinOpNode("|", optionLeft, optionRight);
        option.firstpos.addAll(Arrays.asList(1, 2));
        option.lastpos.addAll(Arrays.asList(1, 2));
        // (a|b)*
        UnaryOpNode kleeneStar = new UnaryOpNode("*", option);
        kleeneStar.firstpos.addAll(Arrays.asList(1, 2));
        kleeneStar.lastpos.addAll(Arrays.asList(1, 2));
        // (a|b)*a
        OperandNode konkatination1Right = new OperandNode("a");
        konkatination1Right.position = 3;
        konkatination1Right.firstpos.addAll(Arrays.asList(3));
        konkatination1Right.lastpos.addAll(Arrays.asList(3));
        BinOpNode konkatination1 = new BinOpNode("°", kleeneStar, konkatination1Right);
        konkatination1.firstpos.addAll(Arrays.asList(1, 2, 3));
        konkatination1.lastpos.addAll(Arrays.asList(3));
        // (a|b)*ab
        OperandNode konkatination2Right = new OperandNode("b");
        konkatination2Right.position = 4;
        konkatination2Right.firstpos.addAll(Arrays.asList(4));
        konkatination2Right.lastpos.addAll(Arrays.asList(4));
        BinOpNode konkatination2 = new BinOpNode("°", konkatination1, konkatination2Right);
        konkatination2.firstpos.addAll(Arrays.asList(1, 2, 3));
        konkatination2.lastpos.addAll(Arrays.asList(4));
        // (a|b)*abb
        OperandNode konkatination3Right = new OperandNode("b");
        konkatination3Right.position = 5;
        konkatination3Right.firstpos.addAll(Arrays.asList(5));
        konkatination3Right.lastpos.addAll(Arrays.asList(5));
        BinOpNode konkatination3 = new BinOpNode("°", konkatination2, konkatination3Right);
        konkatination3.firstpos.addAll(Arrays.asList(1, 2, 3));
        konkatination3.lastpos.addAll(Arrays.asList(5));
        // (a|b)*abb#
        OperandNode endNode = new OperandNode("#");
        endNode.position = 6;
        endNode.firstpos.addAll(Arrays.asList(6));
        endNode.lastpos.addAll(Arrays.asList(6));
        BinOpNode tree = new BinOpNode("°", konkatination3, endNode);
        tree.firstpos.addAll(Arrays.asList(1, 2, 3));
        tree.lastpos.addAll(Arrays.asList(6));

        // Syntaxtree
        Visitable syntaxTree = tree;

        // Expected FollowposTable
        SortedMap<Integer, FollowposTableEntry> expected = new TreeMap<>();

        FollowposTableEntry followposTableEntry = new FollowposTableEntry(1, "a");
        followposTableEntry.followpos.addAll(Arrays.asList(1, 2, 3));
        expected.put(1, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(2, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(1, 2, 3));
        expected.put(2, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(3, "a");
        followposTableEntry.followpos.addAll(Arrays.asList(4));
        expected.put(3, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(4, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(5));
        expected.put(4, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(5, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(6));
        expected.put(5, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(6, "#");
        followposTableEntry.followpos.addAll(Arrays.asList());
        expected.put(6, followposTableEntry);


        SecondVisitor visitor = new SecondVisitor();
        DepthFirstIterator.traverse(syntaxTree, visitor);
        SortedMap<Integer, FollowposTableEntry> result = visitor.getFollowposTable();

        // Output
        System.out.println("FollowposTable expected:");
        for(int i = 1; i < 7; i++) {
            System.out.println(i + ": " + expected.get(i).followpos);
        }
        System.out.println("FollowposTable result:");
        for(int i = 1; i < 7; i++) {
            System.out.println(i + ": " + visitor.getFollowposTable().get(i).followpos);
        }

        assertEquals(expected, result);
    }
}
