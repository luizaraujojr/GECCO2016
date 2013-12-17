package br.unirio.sd.model.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma parte de uma expressão em um modelo dinâmico
 */
public class Expressao
{
	private @Getter TipoOperacao tipoOperacao;
	private @Getter Expressao esq;
	private @Getter Expressao dir;
	private @Getter @Setter String nome;
	private @Getter @Setter double valor;
	private @Getter @Setter double smooth;
	private @Getter @Setter double qart1;
	private @Getter @Setter double qart2;
	private @Getter @Setter double qart3;

	/**
	 * Cria um no da arvore de expressoes com uma operacao binaria
	 */
	public Expressao(TipoOperacao tipo, Expressao esq, Expressao dir)
	{
		this.tipoOperacao = tipo;
		this.esq = esq;
		this.dir = dir;
		this.nome = null;
		this.valor = 0.0;
		this.smooth = 0.0;
		this.qart1 = 0.0;
		this.qart2 = 0.0;
		this.qart3 = 0.0;
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
		if (tipoOperacao == TipoOperacao.VARIAVEL)
			if (this.nome.compareToIgnoreCase(nome) == 0)
				return this;

		Expressao expressao;

		if ((expressao = esq.pegaNoVariavel(nome)) != null)
			return expressao;

		if ((expressao = dir.pegaNoVariavel(nome)) != null)
			return expressao;

		return null;
	}

	/**
	 * Gera um clone da expressão
	 */
	public Expressao clone()
	{
		Expressao esqClone = (esq != null) ? esq.clone() : null;
		Expressao dirClone = (dir != null) ? dir.clone() : null;
	
		Expressao expressao = new Expressao(tipoOperacao, esqClone, dirClone);
		expressao.setNome(this.nome);
		expressao.setValor(this.valor);
		expressao.setSmooth(this.smooth);
		expressao.setQart1(this.qart1);
		expressao.setQart2(this.qart2);
		expressao.setQart3(this.qart3);
		return expressao;
	}	
}