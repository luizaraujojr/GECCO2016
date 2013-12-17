package br.unirio.sd.model.meta;

import lombok.Data;

public @Data class Relacionamento
{
	private String id;
	private TipoRelacionamento tipo;
	private String idClasseOrigem;
	private String idClasseDestino;
	private String papelDestino;

	public Relacionamento(TipoRelacionamento tipo, String id, String origem, String destino, String papelDestino)
	{
		this.id = id;
		this.tipo = tipo;
		this.idClasseOrigem = origem;
		this.idClasseDestino = destino;
		this.papelDestino = papelDestino;
	}
}