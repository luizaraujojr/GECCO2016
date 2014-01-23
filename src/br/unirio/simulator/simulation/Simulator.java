package br.unirio.simulator;

import java.util.ArrayList;
import java.util.List;

import br.unirio.support.OrdenadorTopologico;

public class Simulador
{
	private List<ObjetoSimulacao> objetos;
	private List<ObjetoSimulacao> objetosOrdenados;
	
	public Simulador()
	{
		this.objetos = new ArrayList<ObjetoSimulacao>();
		this.objetosOrdenados = null;
	}
	
	public void adiciona(ObjetoSimulacao objeto)
	{
		this.objetos.add(objeto);
	}
	
	public void prepara() throws Exception
	{
		objetosOrdenados = new OrdenadorTopologicoSimulacao().sort(objetos);
		
		for (ObjetoSimulacao objeto : objetosOrdenados)
			objeto.init();
	}
	
	private void performSingleStep()
	{
		for (ObjetoSimulacao objeto : objetosOrdenados)
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

class OrdenadorTopologicoSimulacao extends OrdenadorTopologico<ObjetoSimulacao>
{
	@Override
	protected List<ObjetoSimulacao> getDependencies(ObjetoSimulacao item)
	{
		return item.getDependencies();
	}
}