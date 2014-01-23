package br.unirio.simulator;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Project extends ObjetoSimulacao
{
	private @Getter double Concluded;
	private @Getter double ProjectTime;
	private @Getter double RTProjectTime;
	private List<Activity> Activity;

	public Project()
	{
		this.Activity = new ArrayList<Activity>();
	}

	public void init()
	{
		ProjectTime = 0.0;
	}

	@Override
	public void step()
	{
		Concluded = groupmin(Activity, "Concluded");
		RTProjectTime = (Concluded > 0.001 ? 0.0 : 1.0);
	}

	@Override
	public List<ObjetoSimulacao> getDependencies()
	{
		List<ObjetoSimulacao> result = new ArrayList<ObjetoSimulacao>();
		result.addAll(Activity);
		return result;
	}

	@Override
	public void update()
	{
	}
}
