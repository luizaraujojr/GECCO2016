package br.unirio.overwork.builders.controller;

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
public class WorkPackageProjectBuilder
{
	public Project execute(SoftwareSystem system)
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
		
		for (WorkPackage pacote : system.getWorkPackages())
			totalFunctionPoints += pacote.calculateFunctionPoints();
		
		Configuration configuration = Configuration.getConfigurationForFunctionPoints(totalFunctionPoints);
		
		double errorCorrectionEffort = configuration.getTestingEffort() * Constants.DAYS_IN_MONTH / configuration.getAverageProductivity() / 4.0;
		
		for (WorkPackage pacote : system.getWorkPackages())
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