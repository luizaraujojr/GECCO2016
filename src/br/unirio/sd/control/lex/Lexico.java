package br.unirio.sd.control.lex;

/**
 * Classe que representa um processador léxico para parser de expressões
 * 
 * @author Marcio
 */
public class Lexico
{
	private String conteudo;

	/**
	 * Inicializa o processador léxico
	 */
	public Lexico(String s)
	{
		this.conteudo = s;
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
		for (TipoToken tipo : TipoToken.values())
			if (tipo.getNome() != null && tipo.getNome().equalsIgnoreCase(s))
				return new Token(tipo);
		
		return new Token(s);
	}
	
	/**
	 * Trata numeros em ponto flutuante			
	 */
	private Token processaNumero(char c) throws LexicoException
	{
		int npontos = 0;
		String sValor = "" + c;
	
		while ((Character.isDigit(c = leCaractere()) || c == '.'))
		{
			if (c == '.')
				if (++npontos > 1)
					throw new LexicoException("Invalid floating point number");
	
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
		    	return new Token(TipoToken.DIF);
	
		    case '=' :
		    	return new Token(TipoToken.LE);
		}

		if (c != 0)
			volta (c);
		
		return new Token(TipoToken.LT);
	}
	
	/**
	 * Processa o caractere '.'
	 */
	private Token processaPonto() throws LexicoException
	{
		char c = leCaractere();

		if (c != 0)
			volta (c);
	
		if (c >= '0' && c <= '9')
			return processaNumero('.');
	
		return new Token(TipoToken.POINT);
	}
	
	/**
	 * Processa o caractere '>'
	 */
	private Token processaMaior()
	{
		char c = leCaractere();
	
		if (c == '=')
		    return new Token(TipoToken.GE);
	
		if (c != 0)
			volta (c);
		
		return new Token(TipoToken.GT);
	}

	/**
	 * Pega a próxima operação na expressão
	 */
	public Token proximo() throws LexicoException
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
		    	return new Token(TipoToken.OPEN_INDEX);

		    case ']':
		    	return new Token(TipoToken.CLOSE_INDEX);

		    case '(':
		    	return new Token(TipoToken.OPEN_PARENTHESIS);

		    case ')':
		    	return new Token(TipoToken.CLOSE_PARENTHESIS);

		    case '*':
		    	return new Token(TipoToken.ASTER);

		    case '+':
		    	return new Token(TipoToken.PLUS);

		    case '-':
		    	return new Token(TipoToken.MINUS);

		    case '/':
		    	return new Token(TipoToken.DIV);

		    case '^':
		    	return new Token(TipoToken.POWER);

		    case ',':
		    	return new Token(TipoToken.COMMA);

		    case '.':
		    	return processaPonto();

		    case '=':
		    	return new Token(TipoToken.EQ);

		    case '<':
		    	return processaMenor();

		    case '>':
		    	return processaMaior();

		    case '_':
		    	return processaLetra('_');

		    default :
		    	throw new LexicoException("Invalic token: " + c);
		}
	}
}