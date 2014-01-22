package br.unirio.sd.model.meta;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.sd.model.cenario.Cenario;

public class Metamodelo
{
	private @Getter @Setter String id;
	private List<Classe> classes;
	private List<Relacionamento> relacionamentos;
	private List<Cenario> cenarios;

	public Metamodelo()
	{
		this.id = "";
		this.classes = new ArrayList<Classe>();
		this.relacionamentos = new ArrayList<Relacionamento>();
		this.cenarios = new ArrayList<Cenario>();
	}
	
	public Metamodelo adicionaClasse(Classe c)
	{
		this.classes.add(c);
		return this;
	}

	public Classe pegaClasseNome(String id)
	{
		for (Classe classe : classes)
			if (classe.getId().compareToIgnoreCase(id) == 0)
				return classe;
		
		return null;
	}
	
	public Iterable<Classe> getClasses()
	{
		return classes;
	}
	
	public Metamodelo adicionaRelacionamento(Relacionamento r)
	{
		this.relacionamentos.add(r);
		return this;
	}

	public Metamodelo adicionaRelacionamentoMultiplo(String id, Classe origem, Classe destino, String papelDestino)
	{
		return adicionaRelacionamento(new Relacionamento(TipoRelacionamento.MULTIPLE, id, origem, destino, papelDestino));
	}

	public Metamodelo adicionaRelacionamentoSimples(String id, Classe origem, Classe destino)
	{
		return adicionaRelacionamento(new Relacionamento(TipoRelacionamento.SINGLE, id, origem, destino, ""));
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
	
	public Metamodelo adicionaCenario(Cenario c)
	{
		this.cenarios.add(c);
		return this;
	}

	public Cenario pegaCenarioNome(String id)
	{
		for (Cenario cenario : cenarios)
			if (cenario.getId().compareToIgnoreCase(id) == 0)
				return cenario;
		
		return null;
	}
	
	public Iterable<Cenario> getCenarios()
	{
		return cenarios;
	}
	
	public void verificaNomeValidoElemento(String id) throws Exception
	{
		if (pegaClasseNome (id) != null)
			throw new Exception("The metamodel has an class with the same name");
	
		if (pegaRelacionamentoNome(id) != null)
			throw new Exception("The metamodel has an relationship with the same name or role");
	}
}