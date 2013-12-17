package br.unirio.sd.model.cenario;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Cenario
{
	private @Getter @Setter String id;
	private List<Conexao> conexoes;
	private List<Restricao> restricoes;

	public Cenario(String id)
	{
		this.id = id;
		this.conexoes = new ArrayList<Conexao>();
		this.restricoes = new ArrayList<Restricao>();
	}
	
	public Cenario adicionaConexao(Conexao c)
	{
		this.conexoes.add(c);
		return this;
	}

	public Conexao pegaConexaoNome(String id)
	{
		for (Conexao conexao : conexoes)
			if (conexao.getId().compareToIgnoreCase(id) == 0)
				return conexao;
		
		return null;
	}
	
	public Iterable<Conexao> getConexoes()
	{
		return conexoes;
	}
	
	public Cenario adicionaRestricao(Restricao r)
	{
		this.restricoes.add(r);
		return this;
	}
	
	public Iterable<Restricao> getRestricoes()
	{
		return restricoes;
	}
}