package br.unirio.optimization;

import br.unirio.overwork.model.base.Project;

public class HillClimbingExperiment //extends GenericExperiment 
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
	public void run (Project instance, int cycles, int maxEvaluations) throws Exception
	{
		ProjectProblem projectProblem = new ProjectProblem(instance, maxEvaluations);
		int evaluations = 0;
		
		projectProblem.publishTitle();
		
		for (int cycle = 0; cycle < cycles; cycle++)
		{
			long initTime = System.currentTimeMillis();			
			ProjectSolution bestSolution = new ProjectSolution(projectProblem, "HC");
			bestSolution.randomizeSolution();
			projectProblem.evaluate(bestSolution);
			bestSolution.setExecutionTime(System.currentTimeMillis() - initTime);
			bestSolution.setCycle(cycle);
			
			ProjectSolution projectSolution = new ProjectSolution(projectProblem, "HC");
			projectSolution = bestSolution.clone();
			
//			for (; evaluations < projectProblem.getMaxEvaluations(); evaluations++)
//			{
				for (int index = 0; index < projectProblem.getProject().countActivities(); index++)
				{
					int currentState = projectSolution.getActivityOverworkAllocation(index);
					for (int value = 0; value <= 9; value++)
					{
						if (evaluations >=  projectProblem.getMaxEvaluations()) 
							{
							break;
							}
						
						projectSolution.setActivitiesOverworkAllocation(index, projectSolution.getActivityOverworkAllocation(index) == 9 ? 1 : projectSolution.getActivityOverworkAllocation(index)+1);
						
						projectProblem.evaluate(projectSolution);
						projectSolution.setExecutionTime(System.currentTimeMillis() - initTime);
						projectSolution.setCycle(cycle);
						
						if (projectSolution.getFitness() > bestSolution.getFitness()) 
						{
							bestSolution = projectSolution.clone(); 
							//restartRequired = false;
							
							//stopping when a best result is found in a activity
							break;
						} 
				//		else 
					///		projectSolution.setActivitiesOverworkAllocation(index, currentState);
						evaluations++;
					}
				}			
//			}
			bestSolution.publishResult();	
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