package br.unirio.sd.model.meta;

import lombok.Data;
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
		this.id = id;
		this.tipo = tipo;
		this.visibilidade = Visibilidade.PUBLIC;
		this.expressao = null; 	// processar a equacao
		this.repositorio = null;
	}

	public Comportamento(TipoComportamento tipo, String id, String repositorio, String equacao)
	{
		this.id = id;
		this.tipo = tipo;
		this.visibilidade = Visibilidade.PUBLIC;
		this.expressao = null; 	// processar a equacao
		this.repositorio = null;	// processar a equacao
	}
}