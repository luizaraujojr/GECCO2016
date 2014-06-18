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
		WorkPackage workPackage = new WorkPackage(nome);
		this.workPackages.add(workPackage);
		return workPackage;
	}	
	
	/**
	 * Adds a software system to the project
	 * @return 
	 */
	public void addSoftwareSystem(SoftwareSystem softwareSystem)
	{
		this.softwareSystem = softwareSystem;
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
		project.setName("Test");

		Developer developer = new Developer("Developer", 60);
		project.addDeveloper(developer);
//		project.addDeveloper(developer);
//		project.addDeveloper(developer);
//		project.addDeveloper(developer);
//		project.addDeveloper(developer);

		double totalFunctionPoints = 0;
		
		for (WorkPackage wp : softwareSystem.getWorkPackages())
			totalFunctionPoints += wp.getFunctionPoints();
		
		Configuration configuration = Configuration.getConfigurationForFunctionPoints(totalFunctionPoints);		
		double errorCorrectionEffort = configuration.getTestingEffort() * Constants.DAYS_IN_MONTH / configuration.getAverageProductivity() / 4.0;
		
		for (WorkPackage wp : softwareSystem.getWorkPackages())
		{
			double functionPoints = wp.getFunctionPoints();
			
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
			
			// TODO: Adicionar as dependências entre as tarefas de mesmo tipo em função das depeências de work packages

			project.addActivity(requirements);
			project.addActivity(design);
			project.addActivity(codificacao);
			project.addActivity(testes);
			
		}
		
		/*
		 * Including the dependencies
		 */
		for (WorkPackage wp : softwareSystem.getWorkPackages())
		{							
			Activity r = project.getActivity("Requisitos " + wp.getName());
			Activity p = project.getActivity("Projeto " + wp.getName());
			Activity c = project.getActivity("Codificacao " + wp.getName());
			Activity t = project.getActivity("Testes " + wp.getName());
			
			for (WorkPackage dependencyWorkPackage : wp.getDependencies())
			{
				Activity r1 = project.getActivity("Requisitos " + dependencyWorkPackage.getName());
				r.addPrecedent(r1);
				
				Activity p1 = project.getActivity("Projeto " + dependencyWorkPackage.getName());
				p.addPrecedent(p1);
				
				Activity c1 = project.getActivity("Codificacao " + dependencyWorkPackage.getName());
				c.addPrecedent(c1);
				
				Activity t1 = project.getActivity("Testes " + dependencyWorkPackage.getName());
				t.addPrecedent(t1);
			}
		}
				
		return project;
	}
}