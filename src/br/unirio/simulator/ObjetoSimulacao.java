package br.unirio.simulator;

import java.util.List;

public interface IObjetoSimulacao
{
	public void init();
	
	public List<IObjetoSimulacao> getDependencies();
	
	public void step();
	
	public void update();
}