package br.unirio.optimization;

import java.lang.reflect.Array;
import java.util.Arrays;

import br.unirio.overwork.model.base.Project;
import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.solutionType.IntSolutionType;
import jmetal.util.JMException;

public class ProjectProblem extends Problem {
	
	private static final double MinimumValue = 0;
	
	private Project project;
	private int evaluations;

	public ProjectProblem(Project project) throws ClassNotFoundException
	
	{	
		this.project = project;
		this.evaluations = 0;

		numberOfObjectives_ = 1;
		numberOfVariables_ = project.countActivities();
		solutionType_ = new IntSolutionType(this);
		length_ = new int[numberOfVariables_];
		length_[0] =  1;
		
		double[] lLimit= new double [numberOfVariables_];
		Arrays.fill(lLimit, 1.0);
		double[] uLimit= new double [numberOfVariables_];
		Arrays.fill(uLimit, 9.0);
		
		lowerLimit_ = lLimit;
		upperLimit_ = uLimit;
		//upperLimit_ = 9;
	}
	
	@Override
	public void evaluate(Solution solution) throws JMException
	{
		solution.setObjective(0, solution.getFitness());
		solution.setLocation(evaluations);
		
		evaluations++;
	}


}
