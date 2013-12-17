package br.unirio.sd.model.cenario;

import lombok.Data;

public @Data class Restricao
{
	private String idConexaoOrigem;
	private String idRelacao;
	private String idCenario;
	private String idConexaoDestino;

	public Restricao(String idConexaoOrigem, String idRelacao, String idCenario, String idConexaoDestino)
	{
		this.idConexaoOrigem = idConexaoOrigem;
		this.idRelacao = idRelacao;
		this.idCenario = idCenario;
		this.idConexaoDestino = idConexaoDestino;
	}

	public Restricao(String idConexaoOrigem, String idCenario, String idConexaoDestino)
	{
		this(idConexaoOrigem, "", idCenario, idConexaoDestino);
	}
}