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
	 * Executes the scenario before the start of an object's life-cycle
	 */
	@Override
	public void afterStart(ActivityDevelopment activity) 
	{
		// pega o acumulado de erros ativos das atividades precedentes
		double sum = 0.0;
		
		for (SimulationObject precedent : activity.getDependencies())
			sum += precedent.getLocalState("activeErrors", 0);
		
		// pega o acumulado de erros das atividades precedentes
		double sum2 = activity.getErrors();

		activity.setLocalState("inheritedActiveErrors", sum);
		activity.setLocalState("errorDensity", (sum2 != 0.0) ? sum / sum2 : 0.0);
		activity.setLocalState("activeErrors", sum);
		
		activity.setLocalState("errorsBeforeStep", sum2);
	}

	/**
	 * Executes the scenario after the simulation step
	 */
	@Override
	public void afterStep(ActivityDevelopment activity)
	{
		// E0 = pega o número de erros antes do passo na atividade, que está guardado em um estado
		double errorsBeforeStep = activity.getLocalState("errorsBeforeStep", 0);

		// E1 = pega o número de erros atual da atividade em um estado
		double errorsAfterStep = activity.getErrors();
		
		// calcula a diferença no número de erros: D = E1 - E0
		double difference = errorsAfterStep - errorsBeforeStep;
		
		// atualiza o número de erros ativos: EA = EA + D * FatorRegeneracao 
		double activeErrors = activity.getLocalState("activeErrors", 0);
		activeErrors += difference * activeErrorsFactor;
		activity.setLocalState("activeErrors", activeErrors);
		
		// calcula o fator de densidade (FD)
//		double inheritedActiveErrors = activity.getLocalState("inheritedActiveErrors", 0);
//		double density = (errorsAfterStep > 0.0) ? inheritedActiveErrors / errorsAfterStep : 0.0;
		double density = activity.getLocalState("errorDensity", 0.0);
		double densityFactor = Tables.lookup(ACTIVE_ERRORS_DENSITY, density, 0, 1);

// Fazer com que seja cenário de ActivityDevelopment ...
// Deveria regenerar apenas os erros ativos
		
		// E1 = E1 + D * (FD - 1)
		activity.setErrors(errorsAfterStep + difference * (densityFactor - 1.0));

		activity.setLocalState("errorsBeforeStep", activity.getErrors());
	}
}