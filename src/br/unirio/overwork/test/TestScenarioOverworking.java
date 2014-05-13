package br.unirio.overwork.test;

import junit.framework.TestCase;
import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.model.Project;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

public class TestScenarioOverworking extends TestCase 
{
	private static Project createProject()
	{
		WorkPackageProjectBuilder builder = new WorkPackageProjectBuilder();
		
		builder.addWorkPackage("Usu�rios")
			.addDataFunction("usuario", 7)
			.addTransactionalFunction("Cadastro de usu�rio", 3)
			.addTransactionalFunction("Lista de usu�rios ", 4)
			.addTransactionalFunction("Consulta de usu�rio", 4);

		builder.addWorkPackage("Professores")
			.addDataFunction("professor", 7)
			.addTransactionalFunction("Cadastro de professor", 4)
			.addTransactionalFunction("Lista de professores", 4)
			.addTransactionalFunction("Consulta de professor", 5);
		
		builder.addWorkPackage("�reas")
			.addDataFunction("area", 7)
			.addTransactionalFunction("Cadastro de �rea", 4)
			.addTransactionalFunction("Lista de �reas", 4)
			.addTransactionalFunction("Lista de sub-�reas", 4)
			.addTransactionalFunction("Consulta de �rea", 4);
		
		builder.addWorkPackage("Aluno")
			.addDataFunction("aluno", 10)
			.addTransactionalFunction("Cadastro de aluno", 6)
			.addTransactionalFunction("Consulta de aluno", 7);
		
		builder.addWorkPackage("Disciplinas")
			.addDataFunction("disciplina", 7)
			.addTransactionalFunction("Cadastro de disciplina", 4)
			.addTransactionalFunction("Lista de disciplinas", 4)
			.addTransactionalFunction("Consulta de disciplina", 5);
		
		builder.addWorkPackage("Turmas")
			.addDataFunction("turma", 7)
			.addDataFunction("turmasolicitada", 7)
			.addTransactionalFunction("Cadastro de turma", 6)
			.addTransactionalFunction("Cadastro de turma solicitada", 6)
			.addTransactionalFunction("Lista de turmas", 4)
			.addTransactionalFunction("Consulta de turma", 7)
			.addTransactionalFunction("Lista de turmas solicitadas", 4);
		
		builder.addWorkPackage("Inscri��es")
			.addDataFunction("inscricao", 7)
			.addTransactionalFunction("Cadastro de inscri��o", 6)
			.addTransactionalFunction("Consulta de inscri��o", 7)
			.addTransactionalFunction("Gera��o de comprovante", 7);
		
		return builder.execute();
	}

	public void testAll() throws Exception
	{
		Project project = createProject();
		
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		new ScenarioOverworking(12).connect(project.getActivities());
		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();
		
		assertEquals(0, project.getActivity("Requisitos Inscri��es").getStartExecutionTime(), 0.1);	
		assertEquals(0.83, project.getActivity("Requisitos Inscri��es").getFinishingTime(), 0.1);	
		assertEquals(40.5, project.getActivity("Requisitos Inscri��es").getErrors(), 0.1);
		
		assertEquals(0.83, project.getActivity("Requisitos Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(2.11, project.getActivity("Requisitos Turmas").getFinishingTime(), 0.1);	
		assertEquals(61.5, project.getActivity("Requisitos Turmas").getErrors(), 0.1);
		
		assertEquals(2.11, project.getActivity("Requisitos Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(2.73, project.getActivity("Requisitos Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(30, project.getActivity("Requisitos Disciplinas").getErrors(), 0.1);
		
		assertEquals(2.73, project.getActivity("Requisitos Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(3.44, project.getActivity("Requisitos Aluno").getFinishingTime(), 0.1);	
		assertEquals(34.47, project.getActivity("Requisitos Aluno").getErrors(), 0.1);
		
		assertEquals(3.45, project.getActivity("Requisitos �reas").getStartExecutionTime(), 0.1);	
		assertEquals(4.16, project.getActivity("Requisitos �reas").getFinishingTime(), 0.1);	
		assertEquals(34.5, project.getActivity("Requisitos �reas").getErrors(), 0.1);
		
		assertEquals(4.16, project.getActivity("Requisitos Professores").getStartExecutionTime(), 0.1);	
		assertEquals(4.78, project.getActivity("Requisitos Professores").getFinishingTime(), 0.1);	
		assertEquals(30, project.getActivity("Requisitos Professores").getErrors(), 0.1);
		
		assertEquals(4.78, project.getActivity("Requisitos Usu�rios").getStartExecutionTime(), 0.1);	
		assertEquals(5.34, project.getActivity("Requisitos Usu�rios").getFinishingTime(), 0.1);	
		assertEquals(27, project.getActivity("Requisitos Usu�rios").getErrors(), 0.1);
		
		assertEquals(5.34, project.getActivity("Projeto Inscri��es").getStartExecutionTime(), 0.1);	
		assertEquals(6.35, project.getActivity("Projeto Inscri��es").getFinishingTime(), 0.1);	
		assertEquals(96.19, project.getActivity("Projeto Inscri��es").getErrors(), 0.1);
		
		assertEquals(6.35, project.getActivity("Projeto Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(7.88, project.getActivity("Projeto Turmas").getFinishingTime(), 0.1);	
		assertEquals(146.06, project.getActivity("Projeto Turmas").getErrors(), 0.1);
		
		assertEquals(7.88, project.getActivity("Projeto Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(8.62, project.getActivity("Projeto Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(71.25, project.getActivity("Projeto Disciplinas").getErrors(), 0.1);
		
		assertEquals(8.62, project.getActivity("Projeto Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(9.48, project.getActivity("Projeto Aluno").getFinishingTime(), 0.1);	
		assertEquals(81.9, project.getActivity("Projeto Aluno").getErrors(), 0.1);
		
		assertEquals(9.48, project.getActivity("Projeto �reas").getStartExecutionTime(), 0.1);	
		assertEquals(10.34, project.getActivity("Projeto �reas").getFinishingTime(), 0.1);	
		assertEquals(81.94, project.getActivity("Projeto �reas").getErrors(), 0.1);
		
		assertEquals(10.34, project.getActivity("Projeto Professores").getStartExecutionTime(), 0.1);	
		assertEquals(11.09, project.getActivity("Projeto Professores").getFinishingTime(), 0.1);	
		assertEquals(71.25, project.getActivity("Projeto Professores").getErrors(), 0.1);
		
		assertEquals(11.09, project.getActivity("Projeto Usu�rios").getStartExecutionTime(), 0.1);	
		assertEquals(11.76, project.getActivity("Projeto Usu�rios").getFinishingTime(), 0.1);	
		assertEquals(64.13, project.getActivity("Projeto Usu�rios").getErrors(), 0.1);
		
		assertEquals(11.76, project.getActivity("Codificacao Inscri��es").getStartExecutionTime(), 0.1);	
		assertEquals(18.47, project.getActivity("Codificacao Inscri��es").getFinishingTime(), 0.1);	
		assertEquals(184.3, project.getActivity("Codificacao Inscri��es").getErrors(), 0.1);
		
		assertEquals(18.47, project.getActivity("Codificacao Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(28.67, project.getActivity("Codificacao Turmas").getFinishingTime(), 0.1);	
		assertEquals(279.86, project.getActivity("Codificacao Turmas").getErrors(), 0.1);
		assertEquals(28.67, project.getActivity("Codificacao Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(33.64, project.getActivity("Codificacao Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(136.52, project.getActivity("Codificacao Disciplinas").getErrors(), 0.1);
		
		assertEquals(33.64, project.getActivity("Codificacao Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(39.36, project.getActivity("Codificacao Aluno").getFinishingTime(), 0.1);	
		assertEquals(156.94, project.getActivity("Codificacao Aluno").getErrors(), 0.1);
		
		assertEquals(39.36, project.getActivity("Codificacao �reas").getStartExecutionTime(), 0.1);	
		assertEquals(45.08, project.getActivity("Codificacao �reas").getFinishingTime(), 0.1);	
		assertEquals(157, project.getActivity("Codificacao �reas").getErrors(), 0.1);
		
		assertEquals(45.08, project.getActivity("Codificacao Professores").getStartExecutionTime(), 0.1);	
		assertEquals(50.06, project.getActivity("Codificacao Professores").getFinishingTime(), 0.1);	
		assertEquals(136.52, project.getActivity("Codificacao Professores").getErrors(), 0.1);
		
		assertEquals(50.06, project.getActivity("Codificacao Usu�rios").getStartExecutionTime(), 0.1);	
		assertEquals(54.53, project.getActivity("Codificacao Usu�rios").getFinishingTime(), 0.1);	
		assertEquals(122.87, project.getActivity("Codificacao Usu�rios").getErrors(), 0.1);
		
		assertEquals(54.53, project.getActivity("Testes Inscri��es").getStartExecutionTime(), 0.1);	
		assertEquals(63.13, project.getActivity("Testes Inscri��es").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Inscri��es").getErrors(), 0.1);
		
		assertEquals(63.13, project.getActivity("Testes Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(76.18, project.getActivity("Testes Turmas").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Turmas").getErrors(), 0.1);
		
		assertEquals(76.18, project.getActivity("Testes Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(82.54, project.getActivity("Testes Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Disciplinas").getErrors(), 0.1);
		
		assertEquals(82.54, project.getActivity("Testes Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(89.86, project.getActivity("Testes Aluno").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Aluno").getErrors(), 0.1);
		
		assertEquals(89.86, project.getActivity("Testes �reas").getStartExecutionTime(), 0.1);	
		assertEquals(97.18, project.getActivity("Testes �reas").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes �reas").getErrors(), 0.1);
		
		assertEquals(97.18, project.getActivity("Testes Professores").getStartExecutionTime(), 0.1);	
		assertEquals(103.55, project.getActivity("Testes Professores").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Professores").getErrors(), 0.1);
		
		assertEquals(103.55, project.getActivity("Testes Usu�rios").getStartExecutionTime(), 0.1);	
		assertEquals(109.27, project.getActivity("Testes Usu�rios").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Usu�rios").getErrors(), 0.1);
	}
}