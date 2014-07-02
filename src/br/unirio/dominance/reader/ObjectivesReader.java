package br.unirio.dominance.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import jmetal.base.Solution;
import jmetal.util.JMException;
import unirio.experiments.multiobjective.reader.ExperimentFileReaderException;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Project;

public class ObjectivesReader
{
	static final String TAB = "\t";
	static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private int currentLineNumber;
	
	public ArrayList <Solution> execute (InputStream stream, Vector<Project> instances, int cycleCount, int objectiveCount) throws Exception
	{
		currentLineNumber = 0;
		Scanner scanner = new Scanner(stream);
		
		//ArrayList <ProjectProblem> pps = new ArrayList <ProjectProblem>();
		
		ArrayList <Solution> solutions = new ArrayList <Solution>();
		
		try
		{			
			for (int i = 0; i < instances.size(); i++){					
				solutions.clear();
				//ArrayList <ProjectProblem> pps1 =  new ArrayList <ProjectProblem>();
				readInstance(solutions, instances.get(i),  i, cycleCount, scanner);
				// verificar como organizar para varias instâncias e definir onde colocar o publish dos resultados.
				
				//;				
			}
			return solutions;
		}
		finally
		{
			scanner.close();
		}
	}
	

	private String readLine (Scanner scanner)
	{
		String result = scanner.nextLine();
		currentLineNumber++;
		return result;
	}
	
	private void throwException (String message) throws ExperimentFileReaderException
	{
		throw new ExperimentFileReaderException(currentLineNumber, message);
	}

	void readInstance (List <Solution> solutions, Project project, int instanceCount, int cycleCount, Scanner scanner) throws Exception
	{
		checkInstanceHeader (instanceCount, scanner);
		
		for (int i = 0; i < cycleCount; i++)
			checkCycle (i, 3, scanner);

		readParetoFrontier(solutions, project, 3, scanner);
	}
	
	private void readParetoFrontier(List <Solution> solutions, Project project, int objectiveCount, Scanner scanner) throws ExperimentFileReaderException, Exception
	{
		String firstHeaderLine = readLine(scanner);
		
		boolean mustCalculate = (firstHeaderLine.compareTo("Calculate Best Frontier:") == 0);
		boolean isPresented = firstHeaderLine.compareTo("Final Pareto Frontier:") == 0;

		if (!mustCalculate && !isPresented)
			throwException("expected final Pareto Frontier header or calculation header");

		if (isPresented)
		{
			readParetoFrontierValues(solutions, project, objectiveCount, scanner);
		}
		else
		{
			if (scanner.hasNextLine())
				readLine(scanner);
		}
	}

	private void checkInstanceHeader(int instanceCount, Scanner scanner) throws ExperimentFileReaderException
	{
		String firstHeaderLine = readLine(scanner);
		
		if (firstHeaderLine.compareTo("=============================================================") != 0)
			throwException("expected header start");

		String secondHeaderLine = readLine(scanner);
		
		String headerStart = "Instance #" + instanceCount;
		
		if (!secondHeaderLine.startsWith(headerStart))
			throwException("expected header instance count");
	
		String thirdHeaderLine = readLine(scanner);
		
		if (thirdHeaderLine.compareTo("=============================================================") != 0)
			throwException("expected header finish");

		String fourthHeaderLine = readLine(scanner);
		
		if (fourthHeaderLine.length() != 0)
			throwException("expected blank line after header finish");
	}

	private void checkCycle (int cycleCount, int objectiveCount, Scanner scanner) throws ExperimentFileReaderException
	{
		checkCycleHeader (cycleCount, scanner);
		checkCycleValues(objectiveCount, scanner);
	}

	private void checkCycleHeader(int cycleCount, Scanner scanner) throws ExperimentFileReaderException
	{
		String line = readLine(scanner);
		String cycleHeader = "Cycle #" + cycleCount;
		
		if (!line.startsWith(cycleHeader))
			throwException("expected 'Cycle #" + cycleCount + "'");
		
		line = line.substring(cycleHeader.length());
		
		if (!line.startsWith(" ("))
			throwException("expected opening parenthesis after cycle header");

		line = line.substring(2);

		int position = line.indexOf(" ms");
		
		if (position <= 0)
			throwException("expected execution time in miliseconds");
		
		line = line.substring(position + 3);
		
		if (!line.startsWith("; "))
			throwException("expected semicolon after execution time");

		line = line.substring(2);

		position = line.indexOf(" best solutions");
		
		if (position <= 0)
			throwException("expected number of best solutions");
	}

	private void readParetoFrontierValues(List <Solution> solutions, Project project, int objectiveCount, Scanner scanner) throws ExperimentFileReaderException, ClassNotFoundException, NumberFormatException, JMException
	{		
		String s = readLine(scanner);
		
		while (s.length() > 0)
		{	
			s.replaceAll(",", ".");
			
			int initialPosition = 0;
			int finalPosition = s.indexOf("[") - 3;
			
			s = s.substring(initialPosition, finalPosition);
			
			ProjectProblem projectProblem =  new ProjectProblem(project);
			Solution solution = new Solution(projectProblem);
			
			//s = "9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9";
			//s = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
			
			String extractedValues[] = s.split(";");			
			
			for (int i = 0; i < extractedValues.length; i++){
				//solution.getDecisionVariables()[i].setValue(Double.parseDouble(extractedValues[i]));
				solution.setObjective(i, Double.parseDouble(extractedValues[i]));
			}			
			
			//projectProblem.evaluate(solution);
			solutions.add(solution);			
			
			s = (scanner.hasNextLine()) ? readLine(scanner) : "";
		}
	}
	
	private void checkCycleValues(int objectiveCount, Scanner scanner) throws ExperimentFileReaderException
	{
		String s = readLine(scanner);
		
		while (s.length() > 0)
		{	
			int initialPosition = s.indexOf("[") +1;
			int finalPosition = s.indexOf("]");
			
			s = s.substring(initialPosition, finalPosition);
		
			s = (scanner.hasNextLine()) ? readLine(scanner) : "";
		}
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