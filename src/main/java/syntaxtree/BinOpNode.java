package syntaxtree;

import java.util.Set;

public class BinOpNode extends SyntaxNode implements Visitable{
    public String operator;
    public Visitable left;
    public Visitable right;

    public BinOpNode(String operator, Visitable left, Visitable right)
    {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    @Override
    public Set<Integer> getFirstPos() {
        return firstpos;
    }

    @Override
    public Set<Integer> getLastPos() {
        return lastpos;
    }


}