package br.unirio.sd.model.modelo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Objeto
{
	private @Getter @Setter String id;
	private @Getter @Setter String idClasse;
	private List<Propriedade> propriedades;
	private List<Relacionamento> relacionamentos;
	//private List<Object> comportamentos;
	//private List<Propriedade> propriedades2;
	
	public Objeto()
	{
		this.id = "";
		this.idClasse = "";
		this.propriedades = new ArrayList<Propriedade>();
		this.relacionamentos = new ArrayList<Relacionamento>();
	}
	
	public Objeto adicionaPropriedade(Propriedade p)
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
	
	public Objeto adicionaRelacionamento(Relacionamento r)
	{
		this.relacionamentos.add(r);
		return this;
	}

	public Relacionamento pegaRelacionamentoNome(String id)
	{
		for (Relacionamento relacionamento : relacionamentos)
			if (relacionamento.getId().compareToIgnoreCase(id) == 0)
				return relacionamento;
		
		return null;
	}
	
	public Iterable<Relacionamento> getRelacionamentos()
	{
		return relacionamentos;
	}
}