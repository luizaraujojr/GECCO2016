package	 br.unirio.optimization.experiment;

import jmetal.base.Algorithm;
import jmetal.metaheuristics.randomSearch.RandomSearch;
import unirio.experiments.multiobjective.controller.Experiment;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the random search experiment
 * 
 * @author Luiz Araujo Jr
 */
public class RandomSearchExperiment extends Experiment<Project>
{
	private int maxEvaluations;
		
	public RandomSearchExperiment(int maxEvaluations) 
	{
		this.maxEvaluations = maxEvaluations;
	}

	@Override
	protected Algorithm createAlgorithm(Project instance) throws Exception
	{		
		ProjectProblem problem = new ProjectProblem(instance);
		RandomSearch algorithm = new RandomSearch(problem);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations);
		return algorithm;
	}
}