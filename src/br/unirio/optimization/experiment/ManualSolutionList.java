package br.unirio.optimization.experiment;

/**
 * NonDominatedSolutionList.java 
 *
 * @author Juan J. Durillo
 * @version 1.0
 */

import jmetal.base.Solution;
import jmetal.util.NonDominatedSolutionList;

/**
 * This class implements an unbound list of non-dominated solutions
 */
public class ManualSolutionList extends NonDominatedSolutionList
{
	public boolean add(Solution solution)
	{
		// At this point, the solution is inserted into the list
		solutionsList_.add(solution);
		return true;
	}
}