package second_visitor;

import org.junit.jupiter.api.Test;
import syntaxtree.DepthFirstIterator;
import syntaxtree.Visitable;

import java.util.SortedMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondVisitorTest {

    @Test
    public void testCorrectFollowposTable() {
        Visitable syntaxTree = ;
        SortedMap<Integer, FollowposTableEntry> expected = ;
        SecondVisitor visitor = new SecondVisitor();

        DepthFirstIterator.traverse(syntaxTree, visitor);

        SortedMap<Integer, FollowposTableEntry> result = visitor.getFollowposTable();
        assertEquals(expected, result);
    }
}
