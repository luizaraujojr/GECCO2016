package br.unirio.simulator;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Activity extends ObjetoSimulacao
{
	private @Getter double TestingTask = 0.0;
	private @Getter double Duration = 0.0;
	private @Getter double Concluded;
	private @Getter double Ready;
	private @Getter double WorkAvailable;
	private @Getter double WorkUsed;
	private @Getter double RTWorkUsed;
	private @Getter double ExecutedTime;
	private @Getter double RTExecTime;
	private @Getter double RemainingTime;
	private @Getter double ConclusionTime;
	private @Getter double RTConclusionTime;
	private @Getter double Errors;
	private @Getter double RTErrorsCreated;
	private @Getter double RTErrorsInherited;
	private @Getter double RTErrorsCorrected;
	private List<Activity> Precedence;
	private Developer Team;

	public Activity()
	{
		this.Precedence = new ArrayList<Activity>();
	}

	public void init()
	{
		ExecutedTime = 0.0;
		ConclusionTime = 0.0;
		Errors = 0.0;
	}

	@Override
	public void step()
	{
		Concluded = (less(RemainingTime, 0.001) > 0.001 ? 1.0 : 0.0);
		Ready = and(greaterEqual(groupmax(Precedence, "Concluded"), 0.0), not(Concluded));
		WorkAvailable = Team.getWorkAvailable();
		WorkUsed = min(WorkAvailable, RemainingTime);
		RTWorkUsed = -WorkUsed;
		RTExecTime = (Ready > 0.001 ? WorkUsed : 0.0);
		RemainingTime = (TestingTask > 0.001 ? Errors * 0.13 : Duration - ExecutedTime);
		RTConclusionTime = (not(Concluded) > 0.001 ? 1.0 : 0.0);
		RTErrorsCreated = (TestingTask > 0.001 ? 0.0 : Team.getErrorGenerationRate());
		RTErrorsInherited = (and(Ready, ExecutedTime) > 0.001 ? groupsum(Precedence, "Errors") / DT : 0.0);
		RTErrorsCorrected = (TestingTask > 0.001 ? -WorkUsed / 0.13 : 0.0);
	}

	@Override
	public List<ObjetoSimulacao> getDependencies()
	{
		List<ObjetoSimulacao> result = new ArrayList<ObjetoSimulacao>();
		result.addAll(Precedence);
		result.add(Team);
		return result;
	}

	@Override
	public void update()
	{
	}
}
