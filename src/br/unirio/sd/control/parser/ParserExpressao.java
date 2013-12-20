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
	private Stack<Expressao> pilhaExpressoes;
	private Stack<Expressao> pilhaOperadores;
	private Lexico lexico;
	
	public ParserExpressao(String sExpressao)
	{
		this.sExpressao = sExpressao;
		this.pilhaExpressoes = new Stack<Expressao>();
		this.pilhaOperadores = new Stack<Expressao>();
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

		processaPilha();
	
		if (pilhaExpressoes.isEmpty())
			throw new ParserException("Invalid expression: " + sExpressao);
		
		Expressao expressao = pilhaExpressoes.pop();
		
		if (!pilhaExpressoes.isEmpty())
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
				pilhaExpressoes.add(new Expressao(token.getValor()));
				break;
				
			case ID:
				pilhaExpressoes.add(new Expressao(token.getNome()));
				break;
				
			case LT: 
//				processaOperador(TipoOperacao.MENOR);
				break;
				
			case LE: 
//				processaOperador(TipoOperacao.MENORIG);
				break;
				
			case DIF: 
//				processaOperador(TipoOperacao.DIFERENTE);
				break;
				
			case EQ:
//				processaOperador(TipoOperacao.IGUAL);
				break;
				
			case GE: 
//				processaOperador(TipoOperacao.MAIORIG);
				break;
				
			case GT: 
//				processaOperador(TipoOperacao.MAIOR);
				break;
				
			case ASTER:
				adicionaOperador(TipoOperacao.PRODUTO);
//				processaOperador(TipoOperacao.PRODUTO);
				break;
				
			case PLUS:
				adicionaOperador(TipoOperacao.SOMA);
//				processaOperador(TipoOperacao.SOMA);
				break;
				
			case MINUS:
				adicionaOperador(TipoOperacao.SUBTRACAO);
//				processaOperadorSubtracao();
				break;
				
			case DIV:
				adicionaOperador(TipoOperacao.DIVISAO);
//				processaOperador(TipoOperacao.DIVISAO);
				break;
				
			case POWER:
//				processaOperador(TipoOperacao.POTENC);
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
			
			case ROUND:
				break;
			
			case LOGN:
				break;
			
			case EXPN:
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

	private void adicionaOperador(TipoOperacao operador) throws ParserException 
	{
		if (pilhaOperadores.isEmpty())
		{
			pilhaOperadores.add(new Expressao(operador, null, null));
			return;
		}
		
		Expressao topo = pilhaOperadores.peek();
		
		if (topo.getTipo().getPrioridade() <= operador.getPrioridade())
		{
			pilhaOperadores.add(new Expressao(operador, null, null));
			return;
		}
		
		processaPilha();
		pilhaOperadores.add(new Expressao(operador, null, null));
	}
	
	private void processaPilha() throws ParserException
	{
		while (!pilhaOperadores.isEmpty())
		{
			Expressao operador = pilhaOperadores.pop();
			
			if (operador == null)
				throw new ParserException("Operator expected");
			
			Expressao direita = pilhaExpressoes.pop();
			
			if (direita == null)
				throw new ParserException("Operand expected in the right-side of " + operador.getTipo().name());
	
			if (operador == TipoOperacao.SUBTRACAO && pilhaExpressoes)
			Expressao esquerda = pilhaExpressoes.pop();
			
			if (esquerda == null)
				throw new ParserException("Operand expected in the left-side of " + operador.getTipo().name());
			
			pilhaExpressoes.add(new Expressao(operador.getTipo(), esquerda, direita));
		}
	}
}