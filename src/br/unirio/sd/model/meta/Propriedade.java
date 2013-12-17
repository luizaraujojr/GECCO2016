package br.unirio.sd.model.meta;

import lombok.Data;

public @Data class Propriedade
{
	private String id;
	private double valor;
	private Visibilidade visibilidade;
	
	public Propriedade(String id, double valor)
	{
		this.id = id;
		this.valor = valor;
		this.visibilidade = Visibilidade.PUBLIC;
	}
}