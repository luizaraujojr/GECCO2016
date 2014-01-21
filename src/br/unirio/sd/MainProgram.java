package br.unirio.sd;

import br.unirio.sd.control.semantics.VerificaMetamodelo;
import br.unirio.sd.model.cenario.Cenario;
import br.unirio.sd.model.cenario.Conexao;
import br.unirio.sd.model.cenario.Restricao;
import br.unirio.sd.model.meta.Classe;
import br.unirio.sd.model.meta.Metamodelo;

public class MainProgram
{
	public static final void main(String[] args) throws Exception
	{
//		Project project = new ProjectReader().execute("data\\project.xml");
//		System.out.println(project.getFunctionPoints());
		
		MainProgram programa = new MainProgram();
		Metamodelo metamodelo = programa.criaMetamodelo();
		new VerificaMetamodelo().executa(metamodelo);
	}
	
	public Metamodelo criaMetamodelo()
	{
		return new Metamodelo()
			.adicionaClasse(criaClasseDesenvolvedor())
			.adicionaClasse(criaClasseAtividade())
			.adicionaClasse(criaClasseProjeto())
			.adicionaRelacionamentoMultiplo("Precedences", "Activity", "Activity", "Successors")
			.adicionaRelacionamentoSimples("Team", "Activity", "Developer")
			.adicionaCenario(criaCenarioTrabalhoHorasExtras())
			.adicionaCenario(criaCenarioExaustao());
	}

	private Classe criaClasseDesenvolvedor()
	{
		return new Classe("Developer")
			// Activity to which the developer is associated
			.adicionaProcesso("AssociatedTask", "Groupmin(Bound([Activity], Team), DeveloperNeed)")
			
			// Developer's Productivity for each activity
			.adicionaProcesso("Productivity", "1")
		
			// Developer's error generation rate for each activity
			.adicionaProcesso("ErrorGenerationRate", "1");
	}

	private Classe criaClasseAtividade()
	{
		return new Classe("Activity")
			.adicionaPropriedade("TestingTask", 0)
			.adicionaPropriedade("Duration", 0)
			.adicionaPropriedade("Order", 0)
			
			// DURACAO = FP / 27.80
			// ERROS = 2.4 erros/FP
			
			// Set the activity execution time
			.adicionaRepositorio("ExecutionTime", "Duration")
		
			// Determine if precedent activities are concluded
			.adicionaProcesso("PrecConcluded", "AND (GroupMax (Precedences, PrecConcluded) >= 0, GroupMax (Precedences, RemainingTime) < 0.001)")

			// Determine if the activity is concluded
			.adicionaProcesso("Concluded", "RemainingTime < 0.001")

			// Determine if the activity is ready to run
			.adicionaProcesso("Ready", "AND (PrecConcluded, NOT(Concluded))")

			// Determine if there are resources available for the activity
			.adicionaProcesso("DeveloperNeed", "IF (Ready, Order, 1000)")
			.adicionaProcesso("Executing", "AND (Ready, Team.AssociatedTask = Order)")

			// Determine developer's productivity
			.adicionaProcesso("Productivity", "Team.Productivity * DT")

			// Determine activity executed time
			.adicionaRepositorio("ExecutedTime", "0")
			.adicionaTaxa("RTExecTime", "ExecutedTime", "IF (Executing, if (TestingTask>0, MIN(RemainingTime, DT), MIN(RemainingTime, Productivity)), 0) / DT")
			.adicionaProcesso("RemainingTime", "ExecutionTime - ExecutedTime")

			// Calculates conclusion time for an activity
			.adicionaRepositorio("ConclusionTime", "0")
			.adicionaTaxa("RTConclusionTime", "ConclusionTime", "if(AND(ConclusionTime < 0.01, RemainingTime-RTExecTime*DT < 0.01), TIME/DT+1, 0)")

			// Errors latent in the activity
			.adicionaRepositorio("Errors", "0")
			.adicionaTaxa("RTErrors", "Errors", "0");
		
//			.adicionaProcesso("SuccessorCount", "COUNT(Successors)")
//			.adicionaProcesso("ErrorsToGo", "if (SuccessorCount > 0, (Errors + RTErrors*DT) /  SuccessorCount, 0)")
//			.adicionaProcesso("Initializing", "AND (ExecutionTime > 0.0, ExecutedTime < 0.001, Executing)")
//			.adicionaProcesso("ReadyToInherit", "OR (AND(ExecutionTime < 0.001, PrecConcluded, Errors < 0.001), Initializing)")
//			.adicionaProcesso("InheritedErrors", "if (ReadyToInherit, GROUPSUM (Precedences, ErrorsToGo), 0)")
//			.adicionaAjuste("RTErrors", "RTErrors + InheritedErrors / DT"));

//			.adicionaPropriedade("Target", 95.0)
//			
//			// Average errors per function point
//			.adicionaProcesso("AvgErrorFP", "2.4")
//	
//			// Errors corrected acummulator for the activity
//			.adicionaRepositorio("ErrorsCorrected", "0")
//			.adicionaTaxa("RTCorrection", "ErrorsCorrected", "CorrErrors")
//	
//			// Error correction in the activity
//			.adicionaTaxa("RTCorrErrors", "Errors", "-CorrErrors")
//			.adicionaProcesso("CorrErrors", "(InspectionTask + TestingTask) * RTExecTime * Productivity / (DetectionCost * DT)")
//	
//			// Cost to correct an error
//			.adicionaProcesso("DetectionCost", "0.28")
//	
//			// Adjustment of time for testng activities
//			.adicionaTaxa("RTTesting", "ExecutionTime", "if (AND(Executing, TestingTask > 0), -ExecutionTime+ExecutedTime+TestingEffort, 0) / DT")
//			.adicionaProcesso("TestingEffort", "TestingDifference * DetectionCost")
//			.adicionaProcesso("TestingDifference", "Max(Errors + InheritedErrors - CorrErrors - TestingTarget, 0)")
//			.adicionaProcesso("TestingTarget", "FunctionPoints * AvgErrorFP * (1 - Target / 100.0)"));
	}
	
	private Classe criaClasseProjeto()
	{
		return new Classe("Project")
			.adicionaProcesso("Concluded", "GroupMin ([Activity], Concluded)")	
			.adicionaRepositorio("ProjectTime", "0")
			.adicionaTaxa("RTProjectTime", "ProjectTime", "IF (Concluded, 0, 1)");
	}
	
	private Cenario criaCenarioTrabalhoHorasExtras()
	{
		Cenario cenario = new Cenario("Overworking");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaPropriedade("WorkHours", 8)
			.adicionaRepositorio("DailyWorkHours", "WorkHours")
			.adicionaProcesso("WHModifier", "1 + (DailyWorkHours - 8) / (12 - 8)")
			.adicionaProcesso("SEModifier", "LOOKUP (SchErrorsTable, WHModifier-1, 0, 1)")
			.adicionaTabela("SchErrorsTable", "VALUES(0.9, 0.94, 1, 1.05, 1.14, 1.24, 1.36, 1.5)")
			/*.adicionaAjuste("Cost", "Cost * DailyWorkHours / 8")*/
			.adicionaAjuste("Productivity", "Productivity * WHModifier")
			.adicionaAjuste("ErrorGenerationRate", "ErrorGenerationRate * SEModifier"));

		return cenario;
	}

	private Cenario criaCenarioExaustao()
	{
		Cenario cenario = new Cenario("Exhaustion");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaRepositorio("Exhaustion", "0")
			.adicionaProcesso("MaxExhaustion", "50")
			.adicionaTaxa("ExhaustionRate", "Exhaustion", "if(Resting = 1, -MaxExhaustion / 20.0, EXModifier)")
			.adicionaProcesso("EXModifier", "LOOKUP (ExaustionTable, DedicationFactor, 0, 1.5)")
			.adicionaProcesso("DedicationFactor", "1 - (1 - Dedication) / 0.4")
			.adicionaProcesso("Dedication", "0.6 + (WHModifier - 1) * (1.2 - 0.6)")
			.adicionaTabela("ExaustionTable", "VALUES(0.0, 0.0, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.15, 1.3, 1.6, 1.9, 2.2, 2.5)")
			.adicionaRepositorio("Resting", "0")
			.adicionaTaxa("RestingRate1", "Resting", "IF (InitResting, 1 / DT, 0)")
			.adicionaTaxa("RestingRate2", "Resting", "IF (QuitResting, -1 / DT, 0)")
			.adicionaTaxa("DWHRate", "DailyWorkHours", "IF (Resting = 1, (8 - DailyWorkHours) / DT, 0)")
			.adicionaProcesso("InitResting", "AND(Resting = 0, Exhaustion > MaxExhaustion)")
			.adicionaProcesso("QuitResting", "AND(Resting = 1, Exhaustion < 0.1)"));
		
		cenario.adicionaRestricao(new Restricao("TheDeveloper", "Overworking", "TheDeveloper"));
		return cenario;
	}
}