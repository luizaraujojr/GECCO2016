package br.unirio.optimization.experiment;

import java.util.ArrayList;

import jmetal.base.Algorithm;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.util.JMException;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Activity;

/**
 * This class implements the manual input algorithm.
 */
public class ManualSolution extends Algorithm
{

	/**
	 * stores the problem to solve
	 */
	private ProjectProblem problem_;

	/**
	 * Constructor
	 * 
	 * @param problem Problem to solve
	 */
	public ManualSolution(ProjectProblem problem)
	{
		this.problem_ = problem;
	}

	/**	
	 * Runs the Manual input algorithm.
	 * 
	 * @return a <code>SolutionSet</code> that is a set of solutions as a result
	 *         of the algorithm execution
	 * @throws JMException
	 */
	public SolutionSet execute() throws JMException, ClassNotFoundException
	{
		int value;

		value = ((Integer) getInputParameter("value")).intValue();
		Solution newSolution; 
		
		ManualSolutionList ndl = new ManualSolutionList();
		switch (value){		
			case 0:
				for (int i=1; i<= 9;i++) {
//					Create the initial solutionSet
					newSolution = new Solution(problem_);
					for (int j=0; j< newSolution.getDecisionVariables().length;j++) {
						newSolution.getDecisionVariables()[j].setValue(i);
					}
					problem_.evaluate(newSolution);
					ndl.add(newSolution);
				}
				break;
			case 50: 
			//	Create the initial solutionSet
				newSolution = new Solution(problem_);
				for (int j=0; j< newSolution.getDecisionVariables().length;j++) {
					if (this.problem_.getProject().getActivity(j).getName().substring(0,6).equalsIgnoreCase("Codifi")) {
						newSolution.getDecisionVariables()[j].setValue(9);
					}
					else if (this.problem_.getProject().getActivity(j).getName().substring(0,6).equalsIgnoreCase("Testes")) {
						newSolution.getDecisionVariables()[j].setValue(9);
					}	
					else newSolution.getDecisionVariables()[j].setValue(1);
				}
				problem_.evaluate(newSolution);
				ndl.add(newSolution);				
				break;
			case 99:
//				Create the initial solutionSet
				newSolution = new Solution(problem_);
				for (int j=0; j< newSolution.getDecisionVariables().length;j++) {
					newSolution.getDecisionVariables()[j].setValue(1);
				}
				problem_.evaluate(newSolution);
				
				
				Activity[] a = CriticalPath.criticalPath((ArrayList) this.problem_.getProject().getActivities());
								
				CriticalPath.print(a);
				
				ndl.add(newSolution);				
				
				
				
				 
				break;
				
			case 1 : case 2 : case 3 : case 4 : case 5 : case 6 : case 7 : case 8 : case 9:
//				Create the initial solutionSet
				newSolution = new Solution(problem_);
				for (int j=0; j< newSolution.getDecisionVariables().length;j++) {
					newSolution.getDecisionVariables()[j].setValue(value);
				}
				problem_.evaluate(newSolution);
				ndl.add(newSolution);
		}
				
		return ndl;
	}
}
