package second_visitor;

import syntaxtree.BinOpNode;
import syntaxtree.OperandNode;
import syntaxtree.UnaryOpNode;
import syntaxtree.Visitor;

import java.util.SortedMap;
import java.util.TreeMap;

public class SecondVisitor implements Visitor {
    private SortedMap<Integer, FollowposTableEntry> followposTableEntries;

    public SecondVisitor() {
        followposTableEntries = new TreeMap<>();
    }

    @Override
    public void visit(OperandNode node) {

    }

    @Override
    public void visit(BinOpNode node) {

    }

    @Override
    public void visit(UnaryOpNode node) {

    }
}
