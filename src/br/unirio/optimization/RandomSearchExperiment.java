package br.unirio.optimization;

import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the random search experiment
 * 
 * @author Luiz Araujo Jr
 */

public class RandomSearchExperiment
{
	/**
	 * Runs the experiment
	 */
	public void run (Project instance, int cycles, int maxEvaluations) throws Exception
	{
		ProjectProblem projectProblem = new ProjectProblem(instance, maxEvaluations);
		
		/**
		 * Prints out the information for the title of the results
		 */
		projectProblem.publishTitle();
		
		/**
		 * Runs the experiment for cycles number
		 */
		for (int cycle = 0; cycle < cycles; cycle++)
		{
			long initTime = System.currentTimeMillis();
			
			ProjectSolution projectSolution = new ProjectSolution(projectProblem, "RS");
			/**
			 * generate a random solution that satisfy the project problem
			 */
			projectSolution.randomizeSolution();
			
			/**
			 * evaluate the solution and registry the information about the solution
			 */
			projectProblem.evaluate(projectSolution, cycle, initTime);
		
			/**
			 * Prints out the result of the experiment´s cycle
			 */
			projectSolution.publishResult();	
		}	
	}
} 