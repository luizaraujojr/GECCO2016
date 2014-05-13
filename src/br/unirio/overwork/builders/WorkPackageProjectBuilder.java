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
		Developer developer = new Developer("Developer", 60);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);
		project.addDeveloper(developer);

		double totalFunctionPoints = 0;
		
		for (WorkPackage pacote : workPackages)
			totalFunctionPoints += pacote.calculateFunctionPoints();
		
		Configuration configuration = Configuration.getConfigurationForFunctionPoints(totalFunctionPoints);
		
		double errorCorrectionEffort = configuration.getTestingEffort() * Constants.DAYS_IN_MONTH / configuration.getAverageProductivity() / 4.0;
		
		for (WorkPackage pacote : workPackages)
		{
			double functionPoints = pacote.calculateFunctionPoints();
			
			double effortRequirement = functionPoints * configuration.getRequirementsEffort() * (Constants.DAYS_IN_MONTH / configuration.getAverageProductivity());
			double errorsRequirement = functionPoints;
			
			Activity requirements = new ActivityDevelopment(project, "Requisitos " + pacote.getName(), effortRequirement, errorsRequirement, 0.0)
				.setDeveloper(developer);
			
			double effortDesign = functionPoints * configuration.getDesignEffort() * (Constants.DAYS_IN_MONTH / configuration.getAverageProductivity());
			double errorsDesign = functionPoints;

			Activity design = new ActivityDevelopment(project, "Projeto " + pacote.getName(), effortDesign, errorsDesign, Constants.DESIGN_ERROR_REGENERATION)
				.addPrecedent(requirements)
				.setDeveloper(developer);
			
			double effortCoding = functionPoints * configuration.getCodingEffort() * (Constants.DAYS_IN_MONTH / configuration.getAverageProductivity());
			double errorsCoding = functionPoints;

			Activity codificacao = new ActivityDevelopment(project, "Codificacao " + pacote.getName(), effortCoding, errorsCoding, Constants.CODING_ERROR_REGENERATION)
				.addPrecedent(design)
				.setDeveloper(developer);

			Activity testes = new AtivityTesting(project, "Testes " + pacote.getName(), errorCorrectionEffort)
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