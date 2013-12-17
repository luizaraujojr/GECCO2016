package br.unirio.sd.model.modelo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Ativacao
{
	private @Getter @Setter String idCenario;
	private List<Conexao> conexoes;
	
	public Ativacao()
	{
		this.idCenario = "";
		this.conexoes = new ArrayList<Conexao>();
	}
	
	public Ativacao adicionaConexao(Conexao c)
	{
		this.conexoes.add(c);
		return this;
	}
	
	public Iterable<Conexao> getConexoes()
	{
		return conexoes;
	}
}
