package br.unirio.optimization.resultInterpretation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.Project;


public class ResultInterpreter 
{
	static final String TAB = "\t";
	static final String LINE_SEPARATOR = System.getProperty("line.separator");


	/**
	 * Analyze the results of an experiment, publishing a file to be evaluated in R Stat
	 * @throws Exception 
	 */
	public void analyze(String path, Vector<Project> instances, int cycleCount, int objectiveCount) throws Exception
	{
		ArrayList <ProjectProblem> pps = new ArrayList <ProjectProblem>(); 
		ResultReader reader = new ResultReader();		
		pps = reader.execute(new FileInputStream(path), instances, cycleCount, objectiveCount);
	
		Scanner scanner = new Scanner(new FileInputStream(path));
		
		try
		{		
			FileOutputStream fos = new FileOutputStream(path+"_interpretation.txt");
			OutputStreamWriter bw = new OutputStreamWriter(fos);
			
			for (int i = 0; i < instances.size(); i++){
				PublishInstance(i, bw);
				ResultReader rr = new ResultReader(); 
				rr.readInstance(pps, instances.get(i),  i, cycleCount, scanner);
		
				PublishProjectActivities(pps, bw);	
			}			
		}
		finally
		{
			scanner.close();
		}
	}

	private void PublishInstance(int i, OutputStreamWriter bw)	throws IOException {
		println(bw, "=============================================================");
		println(bw, "Instance #" + i);
		println(bw, "=============================================================");
		println(bw, "");
	}
	
	private void PublishProjectActivities(ArrayList<ProjectProblem> pps, OutputStreamWriter bw) throws IOException {
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