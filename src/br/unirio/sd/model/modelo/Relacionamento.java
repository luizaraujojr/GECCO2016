package br.unirio.sd.model.modelo;

import br.unirio.sd.model.common.Expressao;
import lombok.Data;

public @Data class Relacionamento
{
	private String id;
	private Expressao elementos;
}