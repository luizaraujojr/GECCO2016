package br.unirio.sd.model.meta;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Classe
{
	private @Getter @Setter String id;
	private List<Comportamento> comportamentos;
	private List<Propriedade> propriedades;
	
	public Classe(String id)
	{
		this.id = id;
		this.comportamentos = new ArrayList<Comportamento>();
		this.propriedades = new ArrayList<Propriedade>();
	}

	public Classe adicionaProcesso(String id, String equacao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.PROCESS, id, equacao));
	}

	public Classe adicionaRepositorio(String id, String equacao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.STOCK, id, equacao));
	}

	public Classe adicionaTaxa(String id, String repositorio, String equacao)
	{
		return adicionaComportamento(new Comportamento(TipoComportamento.RATE, id, repositorio, equacao));
	}
	
	public Classe adicionaComportamento(Comportamento c)
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
	
	public Classe adicionaPropriedade(String id, double valor)
	{
		this.propriedades.add(new Propriedade(id, valor));
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
	
	public void verificaNomeValidoElemento(String id) throws Exception
	{
		if (pegaPropriedadeNome(id) != null)
			throw new Exception("The class has a property with the same name");
	
		if (pegaComportamentoNome(id) != null)
			throw new Exception("The class has a behavior with the same name");
	}
}