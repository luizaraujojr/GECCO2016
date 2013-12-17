package br.unirio.sd.control.lex;

/**
 * Classe que representa uma exce��o gerada pelo processador l�xico
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
