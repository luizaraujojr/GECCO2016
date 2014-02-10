package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.ActivityDevelopment;
import br.unirio.overwork.simulation.Scenario;
import br.unirio.overwork.simulation.SimulationObject;
import br.unirio.overwork.simulation.Tables;

/**
 * Scenario that represents the error regeneration dynamics for activities
 * 
 * @author Marcio Barros
 */
public class ScenarioErrorRegeneration extends Scenario<ActivityDevelopment>
{
	/**
	 * Table that determines ...
	 */
	private static double[] ACTIVE_ERRORS_DENSITY = {1.00, 1.10, 1.20, 1.325, 1.45, 1.60, 2.00, 2.50, 3.25, 4.35, 6.00};
	
	private double activeErrorsFactor;
	
	/**
	 * Initializes the scenario
	 */
	public ScenarioErrorRegeneration(double factor)
	{
		// Parâmetro: FatorRegeneracao (0.85 para análise e projeto, 0.33 para codificação)
		this.activeErrorsFactor = factor;
	}
	
	/**
	 * Prepares the scenario for execution
	 */
	@Override
	public void init(ActivityDevelopment activity)
	{
	}

	/**
	 * Calculate the number of active errors inherited from precedent activities
	 */
	private double countInheritedActiveErrors(ActivityDevelopment activity)
	{
		double sum = 0.0;
		
		for (SimulationObject precedent : activity.getDependencies())
			sum += precedent.getLocalState("activeErrors", 0);

		return sum;
	}

	/**
	 * Executes the scenario before the start of an object's life-cycle
	 */
	@Override
	public void afterStart(ActivityDevelopment activity) 
	{
		double inheritedActiveErrors = countInheritedActiveErrors(activity);
		double inheritedErrors = activity.getErrors();

		activity.setLocalState("inheritedActiveErrors", inheritedActiveErrors);
		activity.setLocalState("activeErrors", inheritedActiveErrors);

		double errorDensity = (inheritedErrors != 0.0) ? inheritedActiveErrors / inheritedErrors : 0.0;
		activity.setLocalState("errorDensity", errorDensity);
		
		activity.setLocalState("workPerformedSoFar", activity.getWorkPerformed());
		activity.setLocalState("errorsGeneratedSoFar", activity.getErrors());
	}

	/**
	 * Executes the scenario after the simulation step
	 */
	@Override
	public void afterStep(ActivityDevelopment activity)
	{
		// Trabalho realizado na atividade antes e depois deste passo
		double workBeforeStep = activity.getLocalState("workPerformedSoFar", 0);
		double workAfterStep = activity.getWorkPerformed();

		// Erros gerados na atividade antes e depois deste passo
		double errorsBeforeStep = activity.getLocalState("errorsGeneratedSoFar", 0);
		double errorsAfterStep = activity.getErrors();
		
		// Calcula a proporção do trabalho realizado nesta atividade neste passo
		double workFraction = (workAfterStep - workBeforeStep) / activity.getWorkRequired();

		// Calcula o número de erros gerados neste passo
		double difference = errorsAfterStep - errorsBeforeStep;
		
		// Atualiza o número de erros ativos: EA = EA + D * FatorRegeneracao 
		double activeErrors = activity.getLocalState("inheritedActiveErrors", 0);
		activeErrors += difference * activeErrorsFactor;
		activity.setLocalState("activeErrors", activeErrors);
		
		// Calcula o fator de densidade (FD)
		double density = activity.getLocalState("errorDensity", 0.0);
		double densityFactor = Tables.lookup(ACTIVE_ERRORS_DENSITY, density, 0, 1);

		// E1 = E1 + D * (FD - 1)
		double activeErrors1 = activity.getLocalState("inheritedActiveErrors", 0);
		errorsAfterStep += activeErrors1 * workFraction * (densityFactor - 1.0);
		activity.setErrors(errorsAfterStep);

		// Salva o estado atual do trabalho
		activity.setLocalState("workPerformedSoFar", workAfterStep);
		activity.setLocalState("errorsGeneratedSoFar", errorsAfterStep);
	}
}