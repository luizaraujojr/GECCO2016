package br.unirio.sd.model.meta;

import lombok.Data;
import br.unirio.sd.control.parser.Parser;
import br.unirio.sd.model.common.Expressao;

public @Data class Comportamento
{
	private String id;
	private TipoComportamento tipo;
	private Visibilidade visibilidade;
	private Expressao expressao;
	private Expressao repositorio;		// somente para RATES

	public Comportamento(TipoComportamento tipo, String id, String equacao)
	{
		this(tipo, id, null, equacao);
	}

	public Comportamento(TipoComportamento tipo, String id, String repositorio, String equacao)
	{
		this.id = id;
		this.tipo = tipo;
		this.visibilidade = Visibilidade.PUBLIC;
		this.expressao = new Parser(equacao).execute();
		this.repositorio = (repositorio != null) ? new Parser(repositorio).execute() : null;
	}
}