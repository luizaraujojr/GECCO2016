package br.unirio.sd.control.lex;

/**
 * Classe que representa uma exceção gerada pelo processador léxico
 * 
 * @author Marcio
 */
public class LexicoException extends Exception
{
	private static final long serialVersionUID = 2541324404904001755L;

	public LexicoException(String message)
	{
		super(message);
	}
}
