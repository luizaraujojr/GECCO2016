package br.unirio.simulator;

import java.util.ArrayList;
import java.util.List;

import br.unirio.support.OrdenadorTopologico;

public class Simulador
{
	private List<IObjetoSimulacao> objetos;
	private List<IObjetoSimulacao> objetosOrdenados;
	
	public Simulador()
	{
		this.objetos = new ArrayList<IObjetoSimulacao>();
		this.objetosOrdenados = null;
	}
	
	public void adiciona(IObjetoSimulacao objeto)
	{
		this.objetos.add(objeto);
	}
	
	public void prepara() throws Exception
	{
		objetosOrdenados = new OrdenadorTopologicoSimulacao().sort(objetos);
		
		for (IObjetoSimulacao objeto : objetosOrdenados)
			objeto.init();
	}
	
	private void performSingleStep()
	{
		for (IObjetoSimulacao objeto : objetosOrdenados)
			objeto.step();
	}
	
	public void run(int steps)
	{
		for (int i = 0; i < steps; i++)
			performSingleStep();
	}
	
	public void run()
	{
		run(1);
	}
}

class OrdenadorTopologicoSimulacao extends OrdenadorTopologico<IObjetoSimulacao>
{
	@Override
	protected List<IObjetoSimulacao> getDependencies(IObjetoSimulacao item)
	{
		return item.getDependencies();
	}
}