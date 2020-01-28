package syntaxtree;

public abstract class SyntaxNode
{
    public Boolean nullable;
    public final Set<Integer> firstpos = new HashSet<>();
    public final Set<Integer> lastpos = new HashSet<>();
}