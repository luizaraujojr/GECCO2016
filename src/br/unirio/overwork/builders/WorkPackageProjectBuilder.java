package br.unirio.overwork.builders;

import java.util.ArrayList;
import java.util.List;

import br.unirio.overwork.model.Activity;
import br.unirio.overwork.model.ActivityDevelopment;
import br.unirio.overwork.model.AtivityTesting;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.model.Project;

/**
 * Class that builds a project from a function points oriented description
 * 
 * @author Marcio Barros
 */
public class WorkPackageProjectBuilder
{
	/**
	 * Average number of function points produced by a developer in a month
	 */
	private static final double PRODUCTIVITY = 27.80;
	
	/**
	 * Number of working-days in a month
	 */
	private static final double DAYS_IN_MONTH = 21;
	
	/**
	 * Average percentile of project effort dedicated to requirements (from Capers Jones, 2000)
	 */
	private static final double EFFORT_REQUIREMENTS = 0.078;
	
	/**
	 * Average percentile of project effort dedicated to design (from Capers Jones, 2000)
	 */
	private static final double EFFORT_DESIGN = 0.099;
	
	/**
	 * Average percentile of project effort dedicated to coding (from Capers Jones, 2000)
	 */
	private static final double EFFORT_CODING = 0.409;
	
	/**
	 * Average number of errors per developed function point
	 */
	private static final double ERRORS_FUNCTION_POINT = 2.4;
	
	/**
	 * List of work packages comprising the project
	 */
	private List<WorkPackage> workPackages;
	
	/**
	 * Initializes the project builder
	 */
	public WorkPackageProjectBuilder()
	{
		this.workPackages = new ArrayList<WorkPackage>();
	}
	
	/**
	 * Adds a work package to the project
	 */
	public WorkPackage addWorkPackage(String nome)
	{
		WorkPackage pt = new WorkPackage(nome);
		this.workPackages.add(pt);
		return pt;
	}
	
	/**
	 * Executes the project generation procedure
	 */
	public Project execute()
	{
		Project project = new Project();
		double developmentEffort = EFFORT_REQUIREMENTS + EFFORT_DESIGN + EFFORT_CODING;

		Developer developer = new Developer("Developer");
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		
		for (WorkPackage pacote : workPackages)
		{
			double functionPoints = pacote.calculateFunctionPoints();
			
			double effortRequirement = functionPoints * EFFORT_REQUIREMENTS * (DAYS_IN_MONTH / PRODUCTIVITY);
			double errorsRequirement = functionPoints * EFFORT_REQUIREMENTS / developmentEffort * ERRORS_FUNCTION_POINT;
			
			Activity requirements = new ActivityDevelopment(project, "Requisitos " + pacote.getName(), effortRequirement, errorsRequirement)
				.setDeveloper(developer);
			
			double effortDesign = functionPoints * EFFORT_DESIGN * (DAYS_IN_MONTH / PRODUCTIVITY);
			double errorsDesign = functionPoints * EFFORT_DESIGN / developmentEffort * ERRORS_FUNCTION_POINT;

			Activity design = new ActivityDevelopment(project, "Projeto " + pacote.getName(), effortDesign, errorsDesign)
				.addPrecedent(requirements)
				.setDeveloper(developer);
			
			double effortCoding = functionPoints * EFFORT_CODING * (DAYS_IN_MONTH / PRODUCTIVITY);
			double errorsCoding = functionPoints * EFFORT_CODING / developmentEffort * ERRORS_FUNCTION_POINT;

			Activity codificacao = new ActivityDevelopment(project, "Codificacao " + pacote.getName(), effortCoding, errorsCoding)
				.addPrecedent(design)
				.setDeveloper(developer);

			Activity testes = new AtivityTesting(project, "Testes " + pacote.getName())
				.addPrecedent(codificacao)
				.setDeveloper(developer);

			project.addActivity(requirements);
			project.addActivity(design);
			project.addActivity(codificacao);
			project.addActivity(testes);
		}
		
		return project;
	}
}