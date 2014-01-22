package br.unirio.sd.model.meta;

import lombok.Data;

public @Data class Relacionamento
{
	private String id;
	private TipoRelacionamento tipo;
	private Classe classeOrigem;
	private Classe classeDestino;
	private String papelDestino;

	public Relacionamento(TipoRelacionamento tipo, String id, Classe origem, Classe destino, String papelDestino)
	{
		this.id = id;
		this.tipo = tipo;
		this.classeOrigem = origem;
		this.classeDestino = destino;
		this.papelDestino = papelDestino;
	}
}