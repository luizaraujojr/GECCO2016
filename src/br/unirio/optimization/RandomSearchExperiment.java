package br.unirio.optimization;

import br.unirio.overwork.model.base.Project;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.metaheuristics.randomSearch.RandomSearch;

public class RandomSearchExperiment extends GenericExperiment {

	@Override
	protected Solution runCycle(Project instance, int instanceNumber) throws Exception
	{
		ProjectProblem problem = createProblem(instance);
		int variableSize = instance.countActivities();

		int populationSize = 3 * variableSize;
		int maxEvaluations = 5;//0 * populationSize;// * populationSize;

		RandomSearch rs = new RandomSearch(problem);
		rs.setInputParameter("populationSize", populationSize);
		rs.setInputParameter("maxEvaluations", maxEvaluations);
		
		SolutionSet solutions = rs.execute();
		return solutions.get(0);
	}
}