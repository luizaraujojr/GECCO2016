package br.unirio.old;

import br.unirio.optimization.ProjectProblem;
import br.unirio.optimization.ProjectSolution;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the hill climbing experiment
 * 
 * @author Luiz Araujo Jr
 */
public class HillClimbingExperimentold  
{

	/**
	 * Runs the experiment
	 */
	public void run (Project instance, int cycles, int maxEvaluations) throws Exception
	{
		int evaluations = 0;
		
		/**
		 * creates the problem
		 */
		ProjectProblem projectProblem = new ProjectProblem(instance, maxEvaluations);
				
		/**
		 * Prints out the information for the title of the results
		 */
		projectProblem.publishTitle();
		
		for (int cycle = 0; cycle < cycles; cycle++)
		{
			if (evaluations >=  projectProblem.getMaxEvaluations()) 
			{
			break;
			}
			/**
			 * gets the initial time of the experiment´s cycle
			 */
			long initTime = System.currentTimeMillis();		
			
			ProjectSolution bestSolution = new ProjectSolution(projectProblem, "HC");
			
			/**
			 * generate a initial random solution
			 */
			bestSolution.randomizeSolution();
			/**
			 * evaluate the solution and registry the information about the solution
			 */
			projectProblem.evaluate(bestSolution);
			
			/**
			 * creates a new solution to continuously compare to actual best solution
			 */
			ProjectSolution projectSolution = new ProjectSolution(projectProblem, "HC");
			projectSolution = bestSolution.clone();
			/**
			 * increment the value of one value of the vector each loop and then evaluate the new solution  
			 */
			for (int index = 0; index < projectProblem.getProject().getActivitiesCount(); index++)
			{
				if (evaluations >=  projectProblem.getMaxEvaluations()) 
				{
				break;
				}
				@SuppressWarnings("unused")
				int currentState = projectSolution.getActivityOverworkAllocation(index);
				for (int value = 0; value <= 9; value++)
				{
					if (evaluations >=  projectProblem.getMaxEvaluations()) 
						{
						break;
						}
					
					projectSolution.setActivitiesOverworkAllocation(index, projectSolution.getActivityOverworkAllocation(index) == 9 ? 1 : projectSolution.getActivityOverworkAllocation(index)+1);
					
					projectProblem.evaluate(projectSolution);
					
					if (projectSolution.getFitness() > bestSolution.getFitness()) 
					{
						bestSolution = projectSolution.clone(); 
						
						/**
						 * Stops when a best result is found in a activity   
						 */
						break;
					} 
					evaluations++;
				}
			}			
			/**
			 * Prints out the result of the best solution of the cycle
			 */
			bestSolution.publishResult();	
		}	
	}
}