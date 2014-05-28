package	 br.unirio.optimization;

import java.io.Writer;
import java.text.DecimalFormat;
import java.util.List;

import unirio.experiments.multiobjective.controller.Experiment;
import jmetal.base.Algorithm;
import jmetal.base.Solution;
import br.unirio.optimization.heuristic.RandomSearch;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the random search experiment
 * 
 * @author Luiz Araujo Jr
 */

public class RandomSearchExperiment extends Experiment<Project>
{
	/**
	 * Runs the experiment
	 */
//	public void run (Project instance, int cycles, int maxEvaluations) throws Exception
//	{
//		ProjectProblem projectProblem = new ProjectProblem(instance, maxEvaluations);
//		
//		/**
//		 * Prints out the information for the title of the results
//		 */
//		projectProblem.publishTitle();
//		
//		/**
//		 * Runs the experiment for cycles number
//		 */
//		for (int cycle = 0; cycle < cycles; cycle++)
//		{
//			long initTime = System.currentTimeMillis();
//			SolutionSet solutionSet = new SolutionSet(projectProblem.getMaxEvaluations());
//			
//			for (int eval = 0; eval < projectProblem.getMaxEvaluations(); eval++)
//			{
//				//ProjectSolution projectSolution = new ProjectSolution(projectProblem, "RS");
//				Solution solution = new Solution(projectProblem);
//				solution.randomize();
//				/**
//				 * generate a random solution that satisfy the project problem
//				 */
//				//projectSolution.randomize();
//				/**
//				 * evaluate the solution and registry the information about the solution
//				 */
//				projectProblem.evaluate(solution);
//				
//				solutionSet.add(solution);		
//			}						
//					
//			/**
//			 * Prints out the result of the experiment´s cycle
//			 */
//						
//			NumberFormat nf2 = new DecimalFormat("0.0000");
//			
//			System.out.println("=============================================================");
//			System.out.println("Instance #0");
//			System.out.println("=============================================================");
//			System.out.println();
//			
//			System.out.println("Cycle #" + cycle + "(" + (System.currentTimeMillis() - initTime) + " ms; " + solutionSet.size() + " best solutions)");
//			for (int y = 0; y < solutionSet.size(); y++)
//			{
//				System.out.println(nf2.format(solutionSet.get(y).getObjective(0)) + "; " + nf2.format(solutionSet.get(y).getObjective(1)) + "; " + nf2.format(solutionSet.get(y).getObjective(2))+ ";  "+ solutionSet.get(y).getLocation());
//			}
//			
////		nf2.format(projectProblem.getProject().getOverworking()) + "; " + nf2.format(projectProblem.getProject().getMakespan()) + "; " + nf2.format(projectProblem.getProject().getCost())+ ";  "+ this.getLocation() + "; [");
////		for (int i = 1; i < this.activitiesOverworkAllocation.length; i++)
////			System.out.print(" " + this.activitiesOverworkAllocation[i]);
////		
////		System.out.println("]");
//			
//		}	
//	}
	
	@Override
	protected Algorithm createAlgorithm(Project instance) throws Exception
	{		
		ProjectProblem problem = new ProjectProblem(instance);

		int population = 10;// * instance.getActivitiesCount();
		
		RandomSearch algorithm = new RandomSearch(problem);
		algorithm.setInputParameter("populationSize", population);
		algorithm.setInputParameter("maxEvaluations", problem.getMaxEvaluations());

		return algorithm;
	}
	
	public void printParetoFrontier(Writer bw, Project instance, List<Solution> uniques) throws Exception
	{
		DecimalFormat dc = new DecimalFormat("0.####");
	
		for (int j = 0; j < uniques.size(); j++)
		{
			Solution solution = uniques.get(j);
			print(bw, dc.format (solution.getObjective(0)));
			
			for (int k = 1; k < 3; k++)
				print(bw, "; " + dc.format(solution.getObjective(k)));
					
			print(bw, "; " + dc.format(solution.getLocation()));
			println(bw, "");
		}
	}
	
	
} 