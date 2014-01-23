package br.unirio.simulator;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Developer extends ObjetoSimulacao
{
	private @Getter double Productivity;
	private @Getter double ErrorGenerationRate;
	private @Getter double WorkAvailable;
	private @Getter double RTWorkAvailable;

	public Developer()
	{
	}

	public void init()
	{
		WorkAvailable = 0.0;
	}

	@Override
	public void step()
	{
		Productivity = 1.0;
		ErrorGenerationRate = 1.0;
		RTWorkAvailable = Productivity;
	}

	@Override
	public List<ObjetoSimulacao> getDependencies()
	{
		List<ObjetoSimulacao> result = new ArrayList<ObjetoSimulacao>();
		return result;
	}

	@Override
	public void update()
	{
	}
}
