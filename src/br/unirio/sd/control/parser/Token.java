package br.unirio.sd.control.parser;

import lombok.Getter;

/**
 * Classe que representa um token gerado pelo processador léxico
 * 
 * @author Marcio
 */
public class Token
{
	private @Getter int tipo;
	private @Getter String nome;
	private @Getter double valor;
	
	public Token(double valor)
	{
		this.tipo = Parser.CONST;
		this.nome = null;
		this.valor = valor;
	}
	
	public Token(String nome)
	{
		this.tipo = Parser.ID;
		this.nome = nome;
		this.valor = 0.0;
	}

	public Token(int tipo)
	{
		this.tipo = tipo;
		this.nome = null;
		this.valor = 0.0;
	}
}