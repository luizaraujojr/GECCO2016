package br.unirio.sd.model.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma parte de uma expressão em um modelo dinâmico
 */
public class Expressao
{
	private @Getter TipoOperacao tipo;
	private @Getter Expressao esquerda;
	private @Getter Expressao direita;
	private @Getter @Setter String nome;
	private @Getter @Setter double valor;

	/**
	 * Cria um no da arvore de expressoes com uma operacao binaria
	 */
	public Expressao(TipoOperacao tipo, Expressao esq, Expressao dir)
	{
		this.tipo = tipo;
		this.esquerda = esq;
		this.direita = dir;
		this.nome = null;
		this.valor = 0.0;
	}

	/**
	 * Cria um no da arvore de expressoes com uma constante
	 */
	public Expressao (double valor)
	{
		this(TipoOperacao.CONSTANTE, null, null);
		this.valor = valor;
	}

	/**
	 * Cria um no da arvore de expressoes com uma variavel
	 */
	public Expressao (String nome)
	{
		this(TipoOperacao.VARIAVEL, null, null);
		this.nome = nome;
	}
	
	/**
	 * Retorna o nó que contém uma determinada variável em uma expressão
	 */
	public Expressao pegaNoVariavel(String nome)
	{
		if (tipo == TipoOperacao.VARIAVEL)
			if (this.nome.compareToIgnoreCase(nome) == 0)
				return this;

		Expressao expressao;

		if ((expressao = esquerda.pegaNoVariavel(nome)) != null)
			return expressao;

		if ((expressao = direita.pegaNoVariavel(nome)) != null)
			return expressao;

		return null;
	}

	/**
	 * Gera um clone da expressão
	 */
	public Expressao clone()
	{
		Expressao esqClone = (esquerda != null) ? esquerda.clone() : null;
		Expressao dirClone = (direita != null) ? direita.clone() : null;
	
		Expressao expressao = new Expressao(tipo, esqClone, dirClone);
		expressao.setNome(this.nome);
		expressao.setValor(this.valor);
		return expressao;
	}	
}