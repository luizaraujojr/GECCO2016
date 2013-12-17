package br.unirio.sd.model.cenario;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.sd.model.meta.Comportamento;
import br.unirio.sd.model.meta.Propriedade;
import br.unirio.sd.model.meta.TipoComportamento;

public class Conexao
{
	private @Getter @Setter String id;
	private @Getter @Setter String idClasse;
	private List<Comportamento> comportamentos;
	private List<Propriedade> propriedades;
	private List<Ajuste> ajustes;
	
	public Conexao(String id, String idClasse)
	{
		this.id = id;
		this.idClasse = idClasse;
		this.comportamentos = new ArrayList<Comportamento>();
		this.propriedades = new ArrayList<Propriedade>();
		this.ajustes = new ArrayList<Ajuste>();
	}

	public Conexao adicionaTabela(String id, String expressao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.TABLE, id, expressao));
	}

	public Conexao adicionaProcesso(String id, String expressao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.PROCESS, id, expressao));
	}

	public Conexao adicionaRepositorio(String id, String expressao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.STOCK, id, expressao));
	}

	public Conexao adicionaTaxa(String id, String repositorio, String expressao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.RATE, id, repositorio, expressao));
	}
	
	private Conexao adicionaComportamento(Comportamento c)
	{
		this.comportamentos.add(c);
		return this;
	}

	public Comportamento pegaComportamentoNome(String id)
	{
		for (Comportamento comportamento : comportamentos)
			if (comportamento.getId().compareToIgnoreCase(id) == 0)
				return comportamento;
		
		return null;
	}
	
	public Iterable<Comportamento> getComportamentos()
	{
		return comportamentos;
	}
	
	public Conexao adicionaPropriedade(String id, double valor)
	{
		this.propriedades.add(new Propriedade(id, valor));
		return this;
	}

	public Conexao adicionaPropriedade(Propriedade p)
	{
		this.propriedades.add(p);
		return this;
	}

	public Propriedade pegaPropriedadeNome(String id)
	{
		for (Propriedade propriedade : propriedades)
			if (propriedade.getId().compareToIgnoreCase(id) == 0)
				return propriedade;
		
		return null;
	}
	
	public Iterable<Propriedade> getPropriedades()
	{
		return propriedades;
	}
	
	public Conexao adicionaAjuste(Ajuste a)
	{
		this.ajustes.add(a);
		return this;
	}

	public Conexao adicionaAjuste(String id, String equacao)
	{
		return adicionaAjuste(new Ajuste(id, equacao));
	}

	public Ajuste pegaAjusteNome(String id)
	{
		for (Ajuste ajuste : ajustes)
			if (ajuste.getId().compareToIgnoreCase(id) == 0)
				return ajuste;
		
		return null;
	}
	
	public Iterable<Ajuste> getAjustes()
	{
		return ajustes;
	}
}