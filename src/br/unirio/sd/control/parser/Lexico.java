package br.unirio.sd.control.parser;

import java.util.HashMap;

/**
 * Classe que representa um processador léxico para parser de expressões
 * 
 * @author Marcio
 */
public class Lexico
{
	private String conteudo;
	private HashMap<String, Short> palavrasReservadas;

	/**
	 * Inicializa o processador léxico
	 */
	public Lexico(String s)
	{
		this.conteudo = s;
		this.palavrasReservadas = new HashMap<String, Short>();
		this.palavrasReservadas.put("max", Parser.MAX);
		this.palavrasReservadas.put("min", Parser.MIN);
		this.palavrasReservadas.put("if", Parser.IF);
		this.palavrasReservadas.put("and", Parser.AND);
		this.palavrasReservadas.put("or", Parser.OR);
		this.palavrasReservadas.put("not", Parser.NOT);
		this.palavrasReservadas.put("lookup", Parser.LOOKUP);
		this.palavrasReservadas.put("round", Parser.ROUND);
		this.palavrasReservadas.put("ln", Parser.LN);
		this.palavrasReservadas.put("exp", Parser.EXP);
		this.palavrasReservadas.put("dt", Parser.DT);
		this.palavrasReservadas.put("time", Parser.TIME);
		this.palavrasReservadas.put("groupsum", Parser.GROUPSUM);
		this.palavrasReservadas.put("groupmax", Parser.GROUPMAX);
		this.palavrasReservadas.put("groupmin", Parser.GROUPMIN);
		this.palavrasReservadas.put("count", Parser.COUNT);
		this.palavrasReservadas.put("bound", Parser.BOUND);
		this.palavrasReservadas.put("select", Parser.SELECT);
		this.palavrasReservadas.put("values", Parser.VALUES);
	}
	
	/**
	 * Lê e retorna o proximo caractere do arquivo de entrada
	 */
	private char leCaractere()
	{
		if (conteudo.length() > 0)
		{
			char c = conteudo.charAt(0);
			conteudo = conteudo.substring(1);
			return c;
		}
		
		return 0;
	}
	
	/**
	 * Retorna um caractere parao arquivo de entrada
	 */
	private void volta(char c)
	{
		conteudo = c + conteudo;
	}
	
	/**
	 * Descarta nulos e comentarios do arquivo de entrada
	 */
	private char pulaNulos()
	{
		char c = 0;
	
		while(conteudo.length() > 0 && c <= 32)
			c = leCaractere();
	
		return (c > 32) ? c : 0;
	}

	/**
	 * Trata identificadores e palavras reservadas
	 */
	private Token processaLetra(char c)
	{
		// Captura o identificador ou palavra reservada
		String s = "" + c;

		while (Character.isLetterOrDigit(c = leCaractere()) || (c == '_'))
			s += c;

		// Retorna o caractere nao utilizado para o arquivo de entrada
		if (c != 0)
			volta(c);

		// Faz busca na tabela de primitivas
		for (String palavraReservada : palavrasReservadas.keySet())
			if (palavraReservada.compareToIgnoreCase(s) == 0)
				return new Token(palavrasReservadas.get(palavraReservada));
		
		return new Token(s);
	}
	
	/**
	 * Trata numeros em ponto flutuante			
	 */
	private Token processaNumero(char c)
	{
		int npontos = 0;
		String sValor = "" + c;
	
		while ((Character.isDigit(c = leCaractere()) || c == '.'))
		{
			if (c == '.')
				if (++npontos > 1)
				{
					ErrorManager.getInstance().add("Invalid floating point number");
					return null;
				}
	
			sValor += c;
		}	
	
		if (c != 0)
			volta(c);
		
		return new Token(Double.parseDouble(sValor));
	}
	
	/**
	 * Processa o caractere '<' 
	 */
	private Token processaMenor()
	{
		char c = leCaractere();
	
		switch (c)
		{
		    case '>' :
		    	return new Token(Parser.DIF);
	
		    case '=' :
		    	return new Token(Parser.LE);
		}

		if (c != 0)
			volta (c);
		
		return new Token(Parser.LT);
	}
	
	/**
	 * Processa o caractere '.'
	 */
	private Token processaPonto()
	{
		char c = leCaractere();

		if (c != 0)
			volta (c);
	
		if (c >= '0' && c <= '9')
			return processaNumero('.');
	
		return new Token(Parser.POINT);
	}
	
	/**
	 * Processa o caractere '>'
	 */
	private Token processaMaior()
	{
		char c = leCaractere();
	
		if (c == '=')
		    return new Token(Parser.GE);
	
		if (c != 0)
			volta (c);
		
		return new Token(Parser.GT);
	}

	/**
	 * Pega a próxima operação na expressão
	 */
	public Token proximo()
	{
		char c = pulaNulos();

		if (Character.isLetter(c))
			return processaLetra(c);

		if (Character.isDigit(c))
			return processaNumero(c);

		switch (c)
		{
			case 0:
				return null;
				
		    case '[':
		    	return new Token(Parser.L_COLCH);

		    case ']':
		    	return new Token(Parser.R_COLCH);

		    case '(':
		    	return new Token(Parser.L_PARENT);

		    case ')':
		    	return new Token(Parser.R_PARENT);

		    case '*':
		    	return new Token(Parser.ASTER);

		    case '+':
		    	return new Token(Parser.PLUS);

		    case '-':
		    	return new Token(Parser.MINUS);

		    case '/':
		    	return new Token(Parser.DIV);

		    case '^':
		    	return new Token(Parser.POTENC);

		    case ',':
		    	return new Token(Parser.COMMA);

		    case '.':
		    	return processaPonto();

		    case '=':
		    	return new Token(Parser.EQUAL);

		    case '<':
		    	return processaMenor();

		    case '>':
		    	return processaMaior();

		    case '_':
		    	return processaLetra('_');

		    default :
		    	ErrorManager.getInstance().add("Invalic token: " + c);
		    	return null;
		}
	}
}