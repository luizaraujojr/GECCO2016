package br.unirio.optimization.heuristic;

import jmetal.base.Algorithm;
import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.base.Variable;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.Ranking;

/**
 * This class implements the Random Search algorithm.
 */
public class HillClimbing extends Algorithm
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
	public HillClimbing(Problem problem)
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
		int populationSize;
		int maxEvaluations;
		int evaluations;

		QualityIndicator indicators; // QualityIndicator object
		int requiredEvaluations; // Use in the example of use of the
		// indicators object (see below)

		SolutionSet population;

		Distance distance = new Distance();

		// Read the parameters
		populationSize = ((Integer) getInputParameter("populationSize")).intValue();
		maxEvaluations = ((Integer) getInputParameter("maxEvaluations")).intValue();
		indicators = (QualityIndicator) getInputParameter("indicators");

		// Initialize the variables
		population = new SolutionSet(populationSize);
		evaluations = 0;

		requiredEvaluations = 0;

		// Create the initial solutionSet
		Solution newSolution;
		Solution bestSolution;
		Variable[] newvar;
		Variable[] bestvar;
		
		for (int i = 0; i < populationSize; i++)
		{
			newSolution = new Solution(problem_);
			
			bestSolution = new Solution(problem_);
			
			newSolution.randomize();
			bestvar = newSolution.getDecisionVariables();
			newvar = newSolution.getDecisionVariables();
			
			problem_.evaluate(newSolution);
			//problem_.evaluateConstraints(newSolution);
			evaluations++;
			population.add(newSolution);
					
			/**
			 * increment the value of one value of the vector each loop and then evaluate the new solution  
			 */
			for (int index = 0; index < problem_.getNumberOfVariables(); index++)
			{
				if (evaluations >=  maxEvaluations) 
				{
				break;
				}
				@SuppressWarnings("unused")
				int currentState = (int) newSolution.getDecisionVariables()[i].getValue();//  problem_.ge .getActivityOverworkAllocation(index);
				for (int value = 0; value <= 9; value++)
				{
					if (evaluations >=  maxEvaluations) 
						{
						break;
						}
					
					
					newvar = newSolution.getDecisionVariables();
					
					if (newvar[index].getValue() == 9 )
						{
						newvar[index].setValue(1);	
						}
					else {
						newvar[index].setValue(newvar[index].getValue()+1);
					}
					
					newSolution.setDecisionVariables(newvar);
					
					problem_.evaluate(newSolution);
					
	//				if (projectSolution.getFitness() > bestSolution.getFitness()) 
	//				{
	//					bestSolution = projectSolution.clone(); 
	//					
	//					/**
	//					 * Stops when a best result is found in a activity   
	//					 */
	//					break;
	//				} 
					evaluations++;
				}
			}		
		} // for
				
		// Generations ...
		//while (evaluations < maxEvaluations)
		//{	
			// Ranking the union
			Ranking ranking = new Ranking(population);

			int remain = populationSize;
			int index = 0;
			SolutionSet front = null;
			population.clear();

			// Obtain the next front
			front = ranking.getSubfront(index);

			while ((remain > 0) && (remain >= front.size()))
			{
				// Assign crowding distance to individuals
				distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
				// Add the individuals of this front
				for (int k = 0; k < front.size(); k++)
				{
					population.add(front.get(k));
				} // for

				// Decrement remain
				remain = remain - front.size();

				// Obtain the next front
				index++;
				if (remain > 0)
				{
					front = ranking.getSubfront(index);
				} // if
			} // while

			// Remain is less than front(index).size, insert only the best one
			if (remain > 0)
			{ // front contains individuals to insert
				distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
				front.sort(new jmetal.base.operator.comparator.CrowdingComparator());
				for (int k = 0; k < remain; k++)
				{
					population.add(front.get(k));
				} // for

				remain = 0;
			} // if

			// This piece of code shows how to use the indicator object into the
			// code
			// of NSGA-II. In particular, it finds the number of evaluations
			// required
			// by the algorithm to obtain a Pareto front with a hypervolume
			// higher
			// than the hypervolume of the true Pareto front.
			if ((indicators != null) && (requiredEvaluations == 0))
			{
				double HV = indicators.getHypervolume(population);
				if (HV >= (0.98 * indicators.getTrueParetoFrontHypervolume()))
				{
					requiredEvaluations = evaluations;
				} // if
			} // if
		//} // while

		// Return as output parameter the required evaluations
		setOutputParameter("evaluations", requiredEvaluations);

		// Return the first non-dominated front
		Ranking ranking1 = new Ranking(population);
		return ranking1.getSubfront(0);
	}
} 
