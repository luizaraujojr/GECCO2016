package br.unirio.overwork.builders.controller;

import java.util.ArrayList;
import java.util.List;
import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.model.WorkPackage;
import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.ActivityDevelopment;
import br.unirio.overwork.model.base.AtivityTesting;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;

/**
 * Class that builds a project from a function points oriented description
 * 
 * @author Marcio Barros
 */
public class SoftwareSystemProjectBuilder
{

	/**
	 * Software System
	 */
	private SoftwareSystem softwareSystem;
	
	/**
	 * List of work packages comprising the project
	 */
	private List<WorkPackage> workPackages;
	
	/**
	 * Initializes the project builder
	 */
	public SoftwareSystemProjectBuilder()
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
	 * Adds a software system to the project
	 * @return 
	 */
	public void addSoftwareSystem(SoftwareSystem ss)
	{
		this.softwareSystem = ss;
	}
	
	
	public WorkPackage getWorkPackagebyName(String name)
	{
		for (WorkPackage wp : this.workPackages)
		{
//			System.out.println(wp.getName());
//			System.out.println(name);
			if (wp.getName().equals(name))
			{
				return wp;
			}
		}
		return null;
	}

	/**
	 * Executes the project generation procedure
	 */
	public Project execute()
	{
		Project project = new Project();
		Developer developer = new Developer("Developer", 60);
		project.setName("Test");
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);

		double totalFunctionPoints = 0;
		
		for (WorkPackage wp : softwareSystem.getWorkPackages())
			totalFunctionPoints += wp.calculateFunctionPoints();

		for (WorkPackage pacote : softwareSystem.getWorkPackages())
			totalFunctionPoints += pacote.calculateFunctionPoints();
		
		Configuration configuration = Configuration.getConfigurationForFunctionPoints(totalFunctionPoints);
		
		double errorCorrectionEffort = configuration.getTestingEffort() * Constants.DAYS_IN_MONTH / configuration.getAverageProductivity() / 4.0;
		
		for (WorkPackage wp : softwareSystem.getWorkPackages())

		for (WorkPackage pacote : softwareSystem.getWorkPackages())
		{
			double functionPoints = wp.calculateFunctionPoints();
			
			double effortRequirement = functionPoints * configuration.getRequirementsEffort() * (Constants.DAYS_IN_MONTH / configuration.getAverageProductivity());
			double errorsRequirement = functionPoints;
			
			Activity requirements = new ActivityDevelopment(project, "Requisitos " + wp.getName(), effortRequirement, errorsRequirement, 0.0)
				.setDeveloper(developer);
			
			double effortDesign = functionPoints * configuration.getDesignEffort() * (Constants.DAYS_IN_MONTH / configuration.getAverageProductivity());
			double errorsDesign = functionPoints;

			Activity design = new ActivityDevelopment(project, "Projeto " + wp.getName(), effortDesign, errorsDesign, Constants.DESIGN_ERROR_REGENERATION)
				.addPrecedent(requirements)
				.setDeveloper(developer);
			
			double effortCoding = functionPoints * configuration.getCodingEffort() * (Constants.DAYS_IN_MONTH / configuration.getAverageProductivity());
			double errorsCoding = functionPoints;

			Activity codificacao = new ActivityDevelopment(project, "Codificacao " + wp.getName(), effortCoding, errorsCoding, Constants.CODING_ERROR_REGENERATION)
				.addPrecedent(design)
				.setDeveloper(developer);

			Activity testes = new AtivityTesting(project, "Testes " + wp.getName(), errorCorrectionEffort)
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