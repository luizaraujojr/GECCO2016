package br.unirio.sd.control.lex;

import lombok.Getter;

/**
 * Classe que representa um token gerado pelo processador léxico
 * 
 * @author Marcio
 */
public class Token
{
	private @Getter TipoToken tipo;
	private @Getter String nome;
	private @Getter double valor;
	
	public Token(double valor)
	{
		this.tipo = TipoToken.CONST;
		this.nome = null;
		this.valor = valor;
	}
	
	public Token(String nome)
	{
		this.tipo = TipoToken.ID;
		this.nome = nome;
		this.valor = 0.0;
	}

	public Token(TipoToken tipo)
	{
		this.tipo = tipo;
		this.nome = null;
		this.valor = 0.0;
	}
}