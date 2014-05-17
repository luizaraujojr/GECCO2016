package br.unirio.optimization;

import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.metaheuristics.randomSearch.RandomSearch;
import br.unirio.overwork.model.base.Project;

public class RandomSearchExperiment //extends GenericExperiment 
{
//	private Project project;
//	
//	public RandomSearchExperiment (Project project)
//	{
//		this.project = project;
//	}
//	
//	public Solution runCycle()
//	{
//		ProjectSolution projectSolution = new ProjectSolution(project);
//		projectSolution.randomize();
//		
//		return projectSolution;
//	}
//}
	public void run (Project instance, int cycles) throws Exception
	{
		ProjectProblem pp = new ProjectProblem(instance);
		
		for (int cycle = 0; cycle < cycles; cycle++)
		{
			long initTime = System.currentTimeMillis();			
			ProjectSolution ps = new ProjectSolution(pp, "RS");
			ps.randomizeSolution();
			pp.evaluate(ps);
			ps.setExecutionTime(System.currentTimeMillis() - initTime);
			ps.setCycle(cycle);
			ps.publishResult();	
		}
		
	}

//	@Override
//	protected Solution runCycle(Project instance, int instanceNumber) throws Exception
//	{
//		ProjectProblem problem = createProblem(instance);
//		int variableSize = instance.countActivities();
//
//		int populationSize = 3 * variableSize;
//		int maxEvaluations = 5;//0 * populationSize;// * populationSize;
//
//		RandomSearch rs = new RandomSearch(problem);
//		rs.setInputParameter("populationSize", populationSize);
//		rs.setInputParameter("maxEvaluations", maxEvaluations);
//		
//		SolutionSet solutions = rs.execute();
//		solutions.add(solution)
//		return solutions.get(0);
//	}
	
//	public Solution[] run(int )
//	{
//		Solution bestSolution = new Solution(catalog, limits);
//		bestSolution.randomize();
//		int bestFitness = bestSolution.getFitness();
//		
//		for (int i = 0; i < fitnessEvaluationBudget; i++)
//		{
//			Solution solution = new Solution(catalog, limits);
//			solution.randomize();
//			int currentFitness = solution.getFitness();
//			
//			if (currentFitness > bestFitness)
//			{
//				bestFitness = currentFitness;
//				bestSolution = solution.clone();
//			}
//		}
//		
//		return bestSolution;
//	}
}