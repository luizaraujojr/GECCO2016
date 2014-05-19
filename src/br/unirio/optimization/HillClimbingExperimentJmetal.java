package br.unirio.optimization;

import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.base.visitor.neighborhood.DeepIntegerNeighborVisitor;
import jmetal.base.visitor.neighborhood.NeighborVisitor;
import jmetal.metaheuristics.randomSearch.HillClimbing;
import br.unirio.overwork.model.base.Project;

public class HillClimbingExperimentJmetal extends GenericExperiment{
	@Override
	protected Solution runCycle(Project instance, int instanceNumber) throws Exception
	{
		ProjectProblem problem = createProblem(instance);
		int variableSize = instance.countActivities();

		int populationSize = 3 * variableSize;
		int maxEvaluations = 5;//0 * populationSize;// * populationSize;
		
		NeighborVisitor visitor = new DeepIntegerNeighborVisitor(problem, 10);  
		HillClimbing hc = new HillClimbing(problem, visitor, maxEvaluations);
		hc.setInputParameter("populationSize", populationSize);
		hc.setInputParameter("maxEvaluations", maxEvaluations);

		
		SolutionSet solutions = hc.execute();
		return solutions.get(0);
	}
}