package br.unirio.optimization.experiment;

import jmetal.base.Algorithm;
import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.util.JMException;
import jmetal.util.NonDominatedSolutionList;

/**
 * This class implements the manual input algorithm.
 */
public class ManualSolution extends Algorithm
{

	/**
	 * stores the problem to solve
	 */
	private Problem problem_;

	/**
	 * Constructor
	 * 
	 * @param problem Problem to solve
	 */
	public ManualSolution(Problem problem)
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

		NonDominatedSolutionList ndl = new NonDominatedSolutionList();

		// Create the initial solutionSet
		Solution newSolution;
		newSolution = new Solution(problem_);
		
		for (int i=0; i< newSolution.getDecisionVariables().length;i++) {
			newSolution.getDecisionVariables()[i].setValue(value);
		}
		
		problem_.evaluate(newSolution);
		
		ndl.add(newSolution);
		return ndl;
	}
}
