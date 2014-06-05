package br.unirio.optimization.heuristic;

import jmetal.base.Algorithm;
import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.Ranking;

/**
 * This class implements the Random Search algorithm.
 */
public class RandomSearch extends Algorithm
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
	public RandomSearch(Problem problem)
	{
		this.problem_ = problem;
	} 
	

	/**
	 * Runs the NSGA-II algorithm.
	 * 
	 * @return a <code>SolutionSet</code> that is a set of non dominated
	 *         solutions as a result of the algorithm execution
	 * @throws JMException
	 */
	public SolutionSet execute() throws JMException, ClassNotFoundException
	{
		// Read the parameters
		int populationSize = ((Integer) getInputParameter("populationSize")).intValue();
		int maxEvaluations = ((Integer) getInputParameter("maxEvaluations")).intValue();
//		indicators = (QualityIndicator) getInputParameter("indicators");

		// Initialize the variables
		SolutionSet population = new SolutionSet(populationSize);
		Distance distance = new Distance();
		int evaluations = 0;
		int requiredEvaluations = 0;

		// Create the initial solutionSet
		for (int i = 0; i < populationSize; i++)
		{
			Solution newSolution = new Solution(problem_);
			newSolution.randomize();
			problem_.evaluate(newSolution);
			evaluations++;
			population.add(newSolution);
		}

		// Generations ...
		while (evaluations < maxEvaluations)
		{
			// Ranking the union
			Ranking ranking = new Ranking(population);
			int remain = populationSize;
			int index = 0;
			population.clear();

			// Obtain the next front
			SolutionSet front = ranking.getSubfront(index);

			while ((remain > 0) && (remain >= front.size()))
			{
				// Assign crowding distance to individuals
				distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());

				// Add the individuals of this front
				for (int k = 0; k < front.size(); k++)
					population.add(front.get(k));

				// Decrement remain
				remain = remain - front.size();

				// Obtain the next front
				index++;

				if (remain > 0)
					front = ranking.getSubfront(index);
			}

			// Remain is less than front(index).size, insert only the best one
			if (remain > 0)
			{
				distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
				front.sort(new jmetal.base.operator.comparator.CrowdingComparator());
				
				for (int k = 0; k < remain; k++)
					population.add(front.get(k));

				remain = 0;
			}
		}

		// Return as output parameter the required evaluations
		setOutputParameter("evaluations", requiredEvaluations);

		// Return the first non-dominated front
		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);
	}
}