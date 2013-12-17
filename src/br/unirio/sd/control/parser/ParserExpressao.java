package br.unirio.sd.control.parser;

import java.util.Stack;

import br.unirio.sd.control.lex.Lexico;
import br.unirio.sd.control.lex.LexicoException;
import br.unirio.sd.control.lex.Token;
import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.common.TipoOperacao;

public class ParserExpressao
{
	private String sExpressao;
	private Stack<Expressao> pilha;
	private Lexico lexico;
	
	public ParserExpressao(String sExpressao)
	{
		this.sExpressao = sExpressao;
		this.pilha = new Stack<Expressao>();
		this.lexico = new Lexico(sExpressao);
	}
	
	public Expressao executa() throws ParserException
	{
		Token token = proximoToken(lexico);
		
		while (token != null)
		{
			processaToken(token);
			token = proximoToken(lexico);
		}
		
		if (pilha.isEmpty())
			throw new ParserException("Invalid expression: " + sExpressao);
		
		Expressao expressao = pilha.pop();
		
		if (!pilha.isEmpty())
			throw new ParserException("Invalid expression: " + sExpressao);
		
		return expressao;
	}
	
	private Token proximoToken(Lexico lexico) throws ParserException
	{
		try
		{
			return lexico.proximo();
		}
		catch(LexicoException e)
		{
			throw new ParserException(e.getMessage());
		}
	}
	
	private void processaToken(Token token) throws ParserException
	{
		switch(token.getTipo())
		{
			case CONST:
				pilha.add(new Expressao(token.getValor()));
				break;
				
			case ID:
				pilha.add(new Expressao(token.getNome()));
				break;
				
			case LT: 
				processaOperador(TipoOperacao.MENOR);
				break;
				
			case LE: 
				processaOperador(TipoOperacao.MENORIG);
				break;
				
			case DIF: 
				processaOperador(TipoOperacao.DIFERENTE);
				break;
				
			case EQ:
				processaOperador(TipoOperacao.IGUAL);
				break;
				
			case GE: 
				processaOperador(TipoOperacao.MAIORIG);
				break;
				
			case GT: 
				processaOperador(TipoOperacao.MAIOR);
				break;
				
			case ASTER:
				processaOperador(TipoOperacao.PRODUTO);
				break;
				
			case PLUS:
				processaOperador(TipoOperacao.SOMA);
				break;
				
			case MINUS:
				processaOperadorSubtracao();
				break;
				
			case DIV:
				processaOperador(TipoOperacao.DIVISAO);
				break;
				
			case POWER:
				processaOperador(TipoOperacao.POTENC);
				break;
				
			case OPEN_PARENTHESIS:
				break;
				
			case CLOSE_PARENTHESIS:
				break;
				
			case OPEN_INDEX:
				break;
				
			case CLOSE_INDEX:
				break;
				
			case POINT: 
				break;
				
			case COMMA:
				break;
				
			case MAX:
				break;
			
			case MIN:
				break;
			
			case IF:
				break;
			
			case AND:
				break;
			
			case OR:
				break;
			
			case NOT:
				break;
			
			case LOOKUP:
				break;
			
			case NORMAL:
				break;
			
			case UNIFORM:
				break;
			
			case ROUND:
				break;
			
			case LOGN:
				break;
			
			case EXPN:
				break;
			
			case SMOOTH:
				break;
			
			case DELAY3:
				break;
			
			case DT:
				break;
			
			case TIME:
				break;
			
			case GRUPO_SOMA:
				break;
			
			case GRUPO_MAX:
				break;
			
			case GRUPO_MIN:
				break;
			
			case BETAPERT:
				break;
			
			case COUNT:
				break;
			
			case BOUND:
				break;
			
			case SELECT:
				break;
				
			default:
				throw new ParserException("Invalid token " + token.getTipo().name());
		}
	}

	private void processaOperador(TipoOperacao operador) throws ParserException
	{
		Expressao esquerda = pilha.pop();
		
		if (esquerda == null)
			throw new ParserException("Operand expected in the left-side of " + operador.name());

		processaToken(proximoToken(lexico));

		Expressao direita = pilha.pop();
		
		if (direita == null)
			throw new ParserException("Operand expected in the right-side of " + operador.name());
		
		pilha.add(new Expressao(operador, esquerda, direita));
	}

	private void processaOperadorSubtracao()
	{
		// TODO Auto-generated method stub
		
	}
}