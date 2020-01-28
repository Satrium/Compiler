package syntaxtree;

public class UnaryOpNode extends SyntaxNode implements Visitable
{
    public String operator;
    public Visitable subNode;
    public UnaryOpNode(String operator, Visitable subNode)
    {
        this.operator = operator;
        this.subNode = subNode;
    }
    
    @Override
    public void accept(Visitor vistor)
    {
        visitor.visit(this);
    }
}