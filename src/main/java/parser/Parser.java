package parser;

import syntaxtree.*;

public class Parser
{
    private int position;
    private final String eingabe;

    public Parser(String eingabe)
    {
        this.eingabe = eingabe;
        this.position = 0;
    }

    public Visitable start()
    {
        if(eingabe.charAt(position) == '#')
        {
            match('#');
            return new OperandNode("#");
        }
        else if (eingabe.charAt(position) == '(')
        {
            match('(');
            Visitable subTree = new BinOpNode("°", regExp(null), new OperandNode("#"));
            match(')');
            match('#');
            assertEndOfInput();
            return subTree;
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private Visitable regExp(Visitable parameter)
    {
        if(eingabe.charAt(position) == '(' || isAlphaNum())
        {
            return re(term(null));
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private Visitable re(Visitable parameter)
    {
        if(eingabe.charAt(position) == '|')
        {
            match('|');
            return re(new BinOpNode("|", parameter, term(null)));
        }
        else if(eingabe.charAt(position) == ')')
        {
            return parameter;
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }


    private Visitable term(Visitable parameter)
    {
        if(isAlphaNum() || eingabe.charAt(position) == '(')
        {
            if(parameter != null)
            {
                return term(new BinOpNode("°", parameter, factor(null)));
            }
            else 
            {
                return term(factor(null));
            }
        }
        else if (eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')')
        {
            return parameter;
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
        
    }

    private Visitable factor(Visitable parameter)
    {
        if(eingabe.charAt(position) == '(' || isAlphaNum())
        {
            return HOp(element(null));
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private Visitable HOp(Visitable parameter)
    {
        if(eingabe.charAt(position) == '*')
        {
            match('*');
            return new UnaryOpNode("*", parameter);
        }
        else if(eingabe.charAt(position) == '+')
        {
            match('+');
            return new UnaryOpNode("+", parameter);
        }
        else if(eingabe.charAt(position) == '?')
        {
            match('?');
            return new UnaryOpNode("?", parameter);
        }
        else if(isAlphaNum() || eingabe.charAt(position) == '(' || eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')' )
        {
            return parameter;
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private Visitable element(Visitable parameter)
    {
        Visitable param = null;
        if(isAlphaNum())
        {
            return alphaNum(null);
        }
        else if(eingabe.charAt(position) == '(')
        {
            match('(');
            param = regExp(null);
            match(')');
            return param;
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private Visitable alphaNum(Visitable parameter)
    {
        if(isAlphaNum())
        {
            match(eingabe.charAt(position));
            return new OperandNode(Character.toString(eingabe.charAt(position-1)));
        }
        else
        {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private boolean isAlphaNum()
    {
        return (eingabe.charAt(position) >= '0' && eingabe.charAt(position) <= '9' || eingabe.charAt(position) >= 'A' && eingabe.charAt(position) <= 'Z' || eingabe.charAt(position) >= 'a' && eingabe.charAt(position) <= 'z');
    }

    private void match(char symbol)
    {
        if ((eingabe == null) || ("".equals(eingabe)))
        {
            throw new RuntimeException("Syntax error !");
        }
        if (position >= eingabe.length())
        {
            throw new RuntimeException("End of input reached !");
        }
        if (eingabe.charAt(position) != symbol)
        {
            throw new RuntimeException("Syntax error !");
        }
        position++;
    }
    
    //dunno why it exists
    private void assertEndOfInput()
    {
        if (position < eingabe.length())
        {
            throw new RuntimeException("No end of input reached !");
        }
    }
}
