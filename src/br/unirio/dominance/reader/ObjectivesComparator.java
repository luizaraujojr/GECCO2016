package br.unirio.dominance.reader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;

import jmetal.base.Solution;
import br.unirio.dominance.model.Objective;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.Project;

public class ObjectivesComparator 
{
	static final String TAB = "\t";
	static final String LINE_SEPARATOR = System.getProperty("line.separator");


	/**
	 * Analyze the results of an experiment, publishing a file to be evaluated in R Stat
	 * @throws Exception 
	 */
	public void analyze(String f1, String f2, Vector<Project> instances, int cycleCount, int objectiveCount) throws Exception
	{
		//ArrayList <ProjectProblem> pps1 = new ArrayList <ProjectProblem>();
		ArrayList<Solution> solutions1 = new ArrayList <Solution>();
		ObjectivesReader reader1 = new ObjectivesReader();		
		solutions1 = reader1.execute(new FileInputStream(f1), instances, cycleCount, objectiveCount);
		
		//ArrayList <ProjectProblem> pps2 = new ArrayList <ProjectProblem>();
		ArrayList <Solution> solutions2 = new ArrayList <Solution>();
		ObjectivesReader reader2 = new ObjectivesReader();		
		solutions2 = reader2.execute(new FileInputStream(f2), instances, cycleCount, objectiveCount);
		
		ArrayList<br.unirio.dominance.model.Solution> ss1 = ConvertDominanceSolution(solutions1);
		
		ArrayList<br.unirio.dominance.model.Solution> ss2 = ConvertDominanceSolution(solutions2);
		
		Compare (ss1, ss2);
					
		try
		{		
			FileOutputStream fos = new FileOutputStream(f1 + "_");
			OutputStreamWriter bw = new OutputStreamWriter(fos);
			
			for (br.unirio.dominance.model.Solution solution1 : ss1)
			{
				println(bw, "=============================================================obj, bigger, smaller");
				for (int i = 0; i < solution1.countObjectives(); i++)
				{
					println(bw, String.valueOf(solution1.getObjective(i).getValue() + TAB + solution1.getObjective(i).countBigger() + TAB + solution1.getObjective(i).countSmaller()));		
				}
				
			}
			
			for (br.unirio.dominance.model.Solution solution2 : ss2)
			{
				println(bw, "=============================================================obj, bigger, smaller");
				for (int i = 0; i < solution2.countObjectives(); i++)
				{
					println(bw, String.valueOf(solution2.getObjective(i).getValue() + TAB + solution2.getObjective(i).countBigger() + TAB + solution2.getObjective(i).countSmaller()));
					println(bw, "");
				}
				
			}
			
//			for (int i = 0; i < instances.size(); i++){
//				PublishInstance(i, bw);
//				ObjectivesReader rr1 = new ObjectivesReader(); 
//				rr1.readInstance(solutions1 , instances.get(i),  i, cycleCount, scanner1);
//				
//				PublishInstance(i, bw);
//				ObjectivesReader rr2 = new ObjectivesReader(); 
//				rr2.readInstance(solutions2, instances.get(i),  i, cycleCount, scanner2);	
//			}			
		}
		finally
		{
			
		}
	}
	
	private void Compare (ArrayList<br.unirio.dominance.model.Solution> ss1, ArrayList<br.unirio.dominance.model.Solution> ss2){
			
		for (br.unirio.dominance.model.Solution sol1 : ss1)
		{
			for (br.unirio.dominance.model.Solution sol2 : ss2)
			{
				for (int i = 0; i < sol2.countObjectives(); i++)
				{	
//					Objective ob1 = new Objective("",sol1.getObjective(i).getValue());
//					Objective ob2 = new Objective("",sol2.getObjective(i).getValue());
					
					Objective ob1 = sol1.getObjective(i);
					Objective ob2 = sol2.getObjective(i);
				
					if (ob1.getValue() > ob2.getValue()){
						ob2.addBigger(ob1);							
						ob1.addSmaller(ob2);
					}

					if (ob2.getValue() > ob1.getValue()){
						ob1.addBigger(ob2);							
						ob2.addSmaller(ob1);
					}
				}
			}
		}
		System.out.println("s11.addObjective(ob1);");
	}

	private ArrayList<br.unirio.dominance.model.Solution> ConvertDominanceSolution(
			ArrayList<Solution> solutions1) {
		ArrayList <br.unirio.dominance.model.Solution> ss1 = new ArrayList <br.unirio.dominance.model.Solution>();
		
		for (Solution sol1 : solutions1)
		{	
			br.unirio.dominance.model.Solution s1 = new br.unirio.dominance.model.Solution();
			
			for (int i = 0; i < sol1.numberOfObjectives(); i++)
			{
				s1.addObjective(new Objective("", sol1.getObjective(i)));
			}
			
			ss1.add(s1);
		}
		return ss1;
	}

	protected void PublishInstance(int i, OutputStreamWriter bw)	throws IOException {
		println(bw, "=============================================================");
		println(bw, "Instance #" + i);
		println(bw, "=============================================================");
		println(bw, "");
	}
	
	protected void PublishProjectActivities(ArrayList<ProjectProblem> pps, OutputStreamWriter bw) throws IOException {
		for (ProjectProblem pp :  pps){
			println(bw, "=============================================================");
			println(bw, pp.getProject().getName());
			println(bw, "");
			for (Activity a :  pp.getProject().getActivities()){
				println(bw, a.getName() + TAB + a.getStartingTime() + TAB + a.getFinishingTime() + TAB + a.getErrors()  + TAB + a.getOverworkHours());	
			}
			println(bw, "");
		}
		pps.clear();
	}
	
	public void print(Writer bw, String s) throws IOException
	{
		System.out.print(s);
		bw.write(s);
		bw.flush();
	}

	public void println(Writer bw, String s) throws IOException
	{
		print(bw, s + LINE_SEPARATOR);
	}	
}