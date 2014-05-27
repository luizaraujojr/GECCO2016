package br.unirio.overwork.optimizer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.unirio.overwork.model.base.Project;

public class RandomSampling 
{
	public void run(Project project, int samples) throws Exception
	{
		NumberFormat nf2 = new DecimalFormat("0.00");
		
		for (int i = 0; i < samples; i++)
		{
			Solution solution = new Solution(project);
			EvaluationResults results = solution.evaluate();
			System.out.println(nf2.format(results.getMakespan()) + "\t" + nf2.format(results.getCost()) + "\t" + nf2.format(results.getOverworking()));
		}
	}
}