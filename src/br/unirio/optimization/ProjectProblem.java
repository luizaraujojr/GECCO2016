package br.unirio.optimization;

import br.unirio.overwork.model.Project;
import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.solutionType.BinarySolutionType;
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
		numberOfVariables_ = 1;
		solutionType_ = new BinarySolutionType(this);
		length_ = new int[numberOfVariables_];
		length_[0] = project.countActivities();
	}
	
	@Override
	public void evaluate(Solution arg0) throws JMException {
		// TODO Auto-generated method stub

	}

}
