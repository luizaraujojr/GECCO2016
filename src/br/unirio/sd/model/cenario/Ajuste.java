package br.unirio.sd.model.cenario;

import lombok.Data;
import br.unirio.sd.model.common.Expressao;

public @Data class Ajuste
{
	private String id;
	private Expressao expressao;

	public Ajuste(String id, String equacao)
	{
		this.id = id;
		this.expressao = null; // parse equacao
	}
}
