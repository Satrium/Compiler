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

    public Visitable Start()
    {
        if(eingabe.charAt(position) == '#')
        {
            match('#');
            return new OperandNode("#");
        }
        else if (eingabe.charAt(position) == '(')
        {
            Visitable param = null;
            match('(');
            return new BinOpNode("°", RegExp(param), new OperandNode("#"));
        }
        else
        {
            throw new RuntimeException("no acceptet Syntax");
        }
    }

    private Visitable RegExp(Visitable parameter)
    {
        Visitable param = null;
        return RE(Term(param));
    }

    private Visitable RE(Visitable parameter)
    {
        if(eingabe.charAt(position) == '|')
        {
            Visitable param = null;
            match('|');
            param = new BinOpNode("|", parameter, Term(null));
            return RE(param);
        }
        else
        {
            return parameter;
        }
    }


    private Visitable Term(Visitable parameter)
    {
        if(IsAlphaNum() || eingabe.charAt(position) == '(')
        {
            Visitable param = null;
            if(parameter != null)
            {
                param = new BinOpNode("°", parameter, Factor(null));
            }
            else 
            {
                param = Factor(null);
            }
            return Term(param);
        }
        else
        {
            return parameter;
        }
        
    }

    private Visitable Factor(Visitable parameter)
    {
        return HOp(Elem(null));
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
        else
        {
            return parameter;
        }
    }

    private Visitable Elem(Visitable parameter)
    {
        Visitable param = null;
        if(IsAlphaNum())
        {
            return AlphaNum(null);
        }
        else
        {
            
            match('(');
            param = RegExp(null);
            match(')');
            return param;
        }
    }

    private Visitable AlphaNum(Visitable parameter)
    {
        if(IsAlphaNum())
        {
            match(eingabe.charAt(position));
            return new OperandNode(Character.toString(eingabe.charAt(position-1)));
        }
        else
        {
            throw new RuntimeException("Syntaxerror unacceted sign");
        }
    }

    private boolean IsAlphaNum()
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
