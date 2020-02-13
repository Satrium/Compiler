package second_visitor;

import syntaxtree.*;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class SecondVisitor implements Visitor {
    private SortedMap<Integer, FollowposTableEntry> followposTable = new TreeMap<>();

    public SortedMap<Integer, FollowposTableEntry> getFollowposTable() {
        return followposTable;
    }

    @Override
    public void visit(OperandNode node) {
        FollowposTableEntry followposTableEntry = new FollowposTableEntry(node.position, node.symbol);
        followposTable.put(followposTableEntry.position, followposTableEntry);
    }

    @Override
    public void visit(BinOpNode node) {
        if(!node.operator.equals("°")) return;

        for(int i : ((SyntaxNode) node.left).lastpos) {
            FollowposTableEntry followposTableEntry = followposTable.get(i);
            Set<Integer> firstPosRight = ((SyntaxNode) node.right).firstpos;
            followposTableEntry.followpos.addAll(firstPosRight);
        }
    }

    @Override
    public void visit(UnaryOpNode node) {
        if(!node.operator.equals("+") && !node.operator.equals("*")) return;

        for(int i : node.lastpos) {
            FollowposTableEntry followposTableEntry = followposTable.get(i);
            Set<Integer> firstPos = node.firstpos;
            followposTableEntry.followpos.addAll(firstPos);
        }
    }
}
