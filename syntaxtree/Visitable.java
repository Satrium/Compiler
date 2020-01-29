package syntaxtree;

public interface Visitable
{
    void accept(Visitor visitor);
}