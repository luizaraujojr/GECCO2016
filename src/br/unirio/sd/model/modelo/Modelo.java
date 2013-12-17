package br.unirio.sd.model.modelo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Modelo
{
	private @Getter @Setter String id;
	private @Getter @Setter String idMetaModelo;
	private @Getter @Setter double passo;
	private List<Objeto> objetos;
	private List<Ativacao> ativacoes;
	
	public Modelo()
	{
		this.id = "";
		this.idMetaModelo = "";
		this.passo = 0.01;
		this.objetos = new ArrayList<Objeto>();
		this.ativacoes = new ArrayList<Ativacao>();
	}
	
	public Modelo adicionaObjeto(Objeto o)
	{
		this.objetos.add(o);
		return this;
	}

	public Objeto pegaObjetoNome(String id)
	{
		for (Objeto objeto : objetos)
			if (objeto.getId().compareToIgnoreCase(id) == 0)
				return objeto;
		
		return null;
	}
	
	public Iterable<Objeto> getObjetos()
	{
		return objetos;
	}
	
	public Modelo adicionaAtivacao(Ativacao a)
	{
		this.ativacoes.add(a);
		return this;
	}
	
	public Iterable<Ativacao> getAtivacoes()
	{
		return ativacoes;
	}
}