package	 br.unirio.optimization.experiment;

import jmetal.base.Algorithm;
import unirio.experiments.multiobjective.controller.Experiment;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the manual input experiment
 * 
 * @author Luiz Araujo Jr
 */
public class ManualExperiment  extends Experiment<Project>
{
	private int value;
		
	public ManualExperiment(int value) 
	{
		this.value = value;
	}

	@Override
	protected Algorithm createAlgorithm(Project instance) throws Exception
	{		
		ProjectProblem problem = new ProjectProblem(instance);
		ManualSolution algorithm = new ManualSolution(problem);
		algorithm.setInputParameter("value", value);
		return algorithm;
	}
}