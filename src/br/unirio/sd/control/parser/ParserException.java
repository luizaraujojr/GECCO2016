package br.unirio.sd.control.parser;

/**
 * Classe que representa uma exce��o de analisador sint�tico
 * 
 * @author Marcio
 */
public class ParserException extends Exception
{
	private static final long serialVersionUID = 9111950737421774356L;

	public ParserException(String message)
	{
		super(message);
	}
}
