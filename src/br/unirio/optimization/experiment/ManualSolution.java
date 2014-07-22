package br.unirio.optimization.experiment;

import java.util.ArrayList;
import java.util.List;

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
	
	static final String TAB = "\t";

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
	@SuppressWarnings("unused")
	public SolutionSet execute() throws JMException, ClassNotFoundException
	{
		int value;

		value = ((Integer) getInputParameter("value")).intValue();
		Solution newSolution; 
		
		ManualSolutionList ndl = new ManualSolutionList();
		
		/*
		 * 0 - Generate 8 solutions with the same overtime number defined for all activities from 1 to 9  
		 * 50 - Generate 8 solutions with the same overtime number defined for the second half activities from 1 to 9
		 * 99 - Generate 8 solutions with the same overtime number defined for the critical path activities from 1 to 9
		 * 1..9 - Generate 1 solution 	with the same overtime number defined for the critical path activities from 1 to 9 
		 */
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
				
				Iterable<Activity> a =  this.problem_.getProject().getActivities();
			    
			    List<Activity> criticalPath = new ArrayList<Activity>();
				
			    criticalPath = UpdateCriticalPath(this.problem_.getProject().getActivities());
			    
			    for (int j=0; j< newSolution.getDecisionVariables().length;j++) {
			    	if (criticalPath.contains (this.problem_.getProject().getActivity(j)))
			    	{
			    		newSolution.getDecisionVariables()[j].setValue(9);	
			    	}
			    	else{
			    		newSolution.getDecisionVariables()[j].setValue(1);
			    	}
			    		
				}
			   			    
			    problem_.evaluate(newSolution);			      
			    				
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

	private List<Activity> UpdateCriticalPath( Iterable<Activity> act) {
		
		for (Activity a : act)
		{
			for (Activity b : a.getPrecedences())
			{
				a.setPredecessorCount(a.getPredecessorCount()+1);
				b.setSucessorCount(b.getSucessorCount()+1);
				for (Activity c : b.getPrecedences())
		    	{
					a.setPredecessorCount(a.getPredecessorCount()+1);
		    		b.setPredecessorCount(b.getPredecessorCount()+1);
		    		c.setSucessorCount(c.getSucessorCount()+1);
		    		for (Activity d : c.getPrecedences())
			    	{
		    			a.setPredecessorCount(a.getPredecessorCount()+1);
			    		b.setPredecessorCount(b.getPredecessorCount()+1);
			    		c.setPredecessorCount(c.getPredecessorCount()+1);
			    		d.setSucessorCount(d.getSucessorCount()+1);
			    		for (Activity e : d.getPrecedences())
				    	{
			    			a.setPredecessorCount(a.getPredecessorCount()+1);
				    		b.setPredecessorCount(b.getPredecessorCount()+1);
				    		c.setPredecessorCount(c.getPredecessorCount()+1);
				    		d.setPredecessorCount(d.getPredecessorCount()+1);
				    		e.setSucessorCount(e.getSucessorCount()+1);
				    		for (Activity f : e.getPrecedences())
					    	{
				    			a.setPredecessorCount(a.getPredecessorCount()+1);
					    		b.setPredecessorCount(b.getPredecessorCount()+1);
					    		c.setPredecessorCount(c.getPredecessorCount()+1);
					    		d.setPredecessorCount(d.getPredecessorCount()+1);
					    		e.setPredecessorCount(e.getPredecessorCount()+1);
					    		f.setSucessorCount(f.getSucessorCount()+1);
					    		for (Activity g : f.getPrecedences())
						    	{
					    			a.setPredecessorCount(a.getPredecessorCount()+1);
						    		b.setPredecessorCount(b.getPredecessorCount()+1);
						    		c.setPredecessorCount(c.getPredecessorCount()+1);
						    		d.setPredecessorCount(d.getPredecessorCount()+1);
						    		e.setPredecessorCount(e.getPredecessorCount()+1);
						    		f.setPredecessorCount(f.getPredecessorCount()+1);
						    		g.setSucessorCount(g.getSucessorCount()+1);
						    		for (Activity h : g.getPrecedences())
							    	{
						    			a.setPredecessorCount(a.getPredecessorCount()+1);
							    		b.setPredecessorCount(b.getPredecessorCount()+1);
							    		c.setPredecessorCount(c.getPredecessorCount()+1);
							    		d.setPredecessorCount(d.getPredecessorCount()+1);
							    		e.setPredecessorCount(e.getPredecessorCount()+1);
							    		f.setPredecessorCount(f.getPredecessorCount()+1);
							    		g.setPredecessorCount(g.getPredecessorCount()+1);
							    		h.setSucessorCount(h.getSucessorCount()+1);
							    	}
						    	}
					    	}
				    	}
			    	}
		    	}
			}
		}
				
		Iterable<Activity> a =  this.problem_.getProject().getActivities();
	    			    
	    List<Activity> ordenado = new ArrayList<Activity>();
		
		for (int k = 0; k < this.problem_.getProject().getActivitiesCount();k++)
		{
			ordenado.add(getMostCritical(ordenado, act));
		}
		
		boolean finished= false;
		
		for (int y = 0; y< ordenado.size(); y++)
		{
			
			if (finished) {
				ordenado.remove(ordenado.get(y));
				y--;
			}
			
			if (ordenado.get(y).getSucessorCount()==0 || finished) {
				finished=true;
			}
		}
			
		return ordenado;
	}
	private Activity getMostCritical( List<Activity> ordenado, Iterable<Activity> base) {
		Activity aux = null;
		
		int max = 0;
		
		for (Activity a : base)
		{
				
			
			if (a.getPredecessorCount() + a.getSucessorCount() > max && !ordenado.contains(a)){
				max = a.getPredecessorCount() + a.getSucessorCount();
				aux = a; 
			}
		}		
		
		return aux;
	}
	
}
