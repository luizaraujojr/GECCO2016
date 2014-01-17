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
			.adicionaCenario(criaCenarioProdutividadeExperiencia())
			.adicionaCenario(criaCenarioErrosExperiencia())
			.adicionaCenario(criaCenarioProdutividadeAprendizado())
			.adicionaCenario(criaCenarioTrabalhoHorasExtras())
			.adicionaCenario(criaCenarioExaustao())
			.adicionaCenario(criaCenarioPontosFuncao())
			.adicionaCenario(criaCenarioEstimativaPontosFuncao())
			.adicionaCenario(criaCenarioGeracaoErroPontosFuncao())
			.adicionaCenario(criaCenarioPropragacaoErros())
			.adicionaCenario(criaCenarioRegeneracaoErros())
			.adicionaCenario(criaCenarioCorrecaoErros())
			.adicionaCenario(criaCenarioCorrecaoDensidadeErros());
	}

	private Classe criaClasseDesenvolvedor()
	{
		return new Classe("Developer")
			.adicionaPropriedade("ExpAnalysis", 0)
			.adicionaPropriedade("ExpDesign", 0)
			.adicionaPropriedade("ExpCoding", 0)
			.adicionaPropriedade("ExpTesting", 0)
			.adicionaPropriedade("ExpInspection", 0)
			.adicionaPropriedade("HourlyCost", 0)
			
			// Activity to which the developer is associated
			.adicionaProcesso("AssociatedTask", "Groupmin(Bound([Activity], Team), DeveloperNeed)")
			
			// Developer's Productivity for each activity
			.adicionaProcesso("Cost", "HourlyCost * 8")
				
			// Developer's Productivity for each activity
			.adicionaProcesso("Productivity", "1")
			.adicionaProcesso("ProdAnalysis", "Productivity")
			.adicionaProcesso("ProdDesign", "Productivity")
			.adicionaProcesso("ProdCoding", "Productivity")
			.adicionaProcesso("ProdTesting", "Productivity")
			.adicionaProcesso("ProdInspection", "Productivity")
		
			// Developer's error generation rate for each activity
			.adicionaProcesso("ErrorGenerationRate", "1")
			.adicionaProcesso("ErrorRateAnalysis", "ErrorGenerationRate")
			.adicionaProcesso("ErrorRateDesign", "ErrorGenerationRate")
			.adicionaProcesso("ErrorRateCoding", "ErrorGenerationRate");
	}

	private Classe criaClasseAtividade()
	{
		return new Classe("Activity")
			.adicionaPropriedade("AnalysisTask", 0)
			.adicionaPropriedade("ArchitecTask", 0)
			.adicionaPropriedade("DesignTask", 0)
			.adicionaPropriedade("CodingTask", 0)
			.adicionaPropriedade("TestingTask", 0)
			.adicionaPropriedade("InspectionTask", 0)
			.adicionaPropriedade("MinimumDuration", 0)
			.adicionaPropriedade("ExpectedDuration", 0)
			.adicionaPropriedade("MaximumDuration", 0)
			.adicionaPropriedade("Order", 0)
			
			// Set the activity execution time
			.adicionaProcesso("ExpectedTime", "ExpectedDuration")
			.adicionaProcesso("MaximumTime", "MaximumDuration")
			.adicionaProcesso("MinimumTime", "MinimumDuration")
			.adicionaRepositorio("ExecutionTime", "ExpectedTime")
		
			// Determine if precedent activities are concluded
			.adicionaProcesso("PrecConcluded", "AND (GroupMax (Precedences, PrecConcluded) >= 0, GroupMax (Precedences, RemainingTime) < 0.001)")

			// Determine if the activity is concluded
			.adicionaProcesso("Concluded", "RemainingTime < 0.001")

			// Determine if the activity is ready to run
			.adicionaProcesso("Ready", "AND (PrecConcluded, NOT(Concluded))")

			// Determine if there are resources available for the activity
			.adicionaProcesso("DeveloperNeed", "IF (Ready, Order, 1000)")
			.adicionaProcesso("Executing", "AND (Ready, Team.AssociatedTask = Order)")

			// Determine developer producticity
			.adicionaProcesso("Productivity", "((AnalysisTask * Team.ProdAnalysis)  + (ArchitecTask * Team.ProdDesign) + (DesignTask * Team.ProdDesign) + (CodingTask * Team.ProdCoding) + (TestingTask * Team.ProdTesting) + (InspectionTask * Team.ProdInspection)) * DT")

			// Determine activity executed time
			.adicionaRepositorio("ExecutedTime", "0")
			.adicionaTaxa("RTExecTime", "ExecutedTime", "IF (Executing, if (OR(InspectionTask>0, TestingTask>0), MIN(RemainingTime, DT), MIN(RemainingTime, Productivity)), 0) / DT")
			.adicionaProcesso("RemainingTime", "ExecutionTime - ExecutedTime")

			// Calculates conclusion time for an activity
			.adicionaRepositorio("ConclusionTime", "0")
			.adicionaTaxa("RTConclusionTime", "ConclusionTime", "if(AND(ConclusionTime < 0.01, RemainingTime-RTExecTime*DT < 0.01), TIME/DT+1, 0)")

			// Accumulates activity cost
			.adicionaRepositorio("Cost", "0")
			.adicionaTaxa("RTCost", "Cost", "if(Executing, Team.Cost, 0)")

			// Errors latent in the activity
			.adicionaRepositorio("Errors", "0")
			.adicionaTaxa("RTErrors", "Errors", "0");
	}
	
	private Classe criaClasseProjeto()
	{
		return new Classe("Project")
			.adicionaProcesso("Concluded", "GroupMin ([Activity], Concluded)")	
			.adicionaRepositorio("ProjectTime", "0")
			.adicionaTaxa("RTProjectTime", "ProjectTime", "IF (Concluded, 0, 1)")
			.adicionaProcesso("ProjectCost", "GROUPSUM([Activity], Cost)");
	}
	
	private Cenario criaCenarioProdutividadeExperiencia()
	{
		Cenario cenario = new Cenario("ProductivityDueExpertise");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaAjuste("ProdAnalysis", "(0.667 + ExpAnalysis * 0.666) * ProdAnalysis")
			.adicionaAjuste("ProdDesign", "(0.667 + ExpDesign * 0.666) * ProdDesign")
			.adicionaAjuste("ProdCoding", "(0.667 + ExpCoding * 0.666) * ProdCoding")
			.adicionaAjuste("ProdTesting", "(0.667 + ExpTesting * 0.666) * ProdTesting")
			.adicionaAjuste("ProdInspection", "(0.667 + ExpInspection * 0.666) * ProdInspection"));
		
		return cenario;
	}

	private Cenario criaCenarioErrosExperiencia()
	{
		Cenario cenario = new Cenario("ErrorGenerationDueExpertise");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaAjuste("ErrorRateAnalysis", "(0.667 + (1 - ExpAnalysis) * 0.666) * ErrorRateAnalysis")
			.adicionaAjuste("ErrorRateDesign", "(0.667 + (1 - ExpDesign) * 0.666) * ErrorRateDesign")
			.adicionaAjuste("ErrorRateCoding", "(0.667 + (1 - ExpCoding) * 0.666) * ErrorRateCoding"));
		
		return cenario;
	}

	private Cenario criaCenarioProdutividadeAprendizado()
	{
		Cenario cenario = new Cenario("ErrorGenerationDueExpertise");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaRepositorio("DaysInProject", "0")
			.adicionaTaxa("DaysInProjectCounter", "DaysInProject", "1")
			.adicionaProcesso("DIPFactor", "MIN (DaysInProject / 20, 1.0)")
			.adicionaProcesso("DIPModifier", "LOOKUP (LearningTable, DIPFactor, 0, 1)")
			.adicionaTabela("LearningTable", "VALUES(1.0, 1.0125, 1.0325, 1.055, 1.09, 1.15, 1.2, 1.22, 1.245, 1.25, 1.25)")
			.adicionaAjuste("Productivity", "Productivity * DIPModifier"));

		return cenario;
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
			.adicionaAjuste("Cost", "Cost * DailyWorkHours / 8")
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

	private Cenario criaCenarioPontosFuncao()
	{
		Cenario cenario = new Cenario("FunctionPointMeasure");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			.adicionaProcesso("FunctionPoints", "0"));
		
		return cenario;
	}

	private Cenario criaCenarioEstimativaPontosFuncao()
	{
		Cenario cenario = new Cenario("EstimationFunctionPoints");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			
			// Developer's productivity
			.adicionaProcesso("AvgFPMonth", "27.80")
			.adicionaProcesso("MinFPMonth", "15.41")
			.adicionaProcesso("MaxFPMonth", "40.19")
	
			// Evaluate the contribution of the activity type to the project
			.adicionaProcesso("ContrAnalysis", "AnalysisTask * 4.114")
			.adicionaProcesso("ContrArchitec", "ArchitecTask * 3.6958")
			.adicionaProcesso("ContrDesign", "DesignTask * 11.7052")
			.adicionaProcesso("ContrCoding", "CodingTask * 20.5459")
			.adicionaProcesso("ContrTesting", "TestingTask * 59.9416")
			.adicionaProcesso("Contribution", "ContrAnalysis + ContrArchitec + ContrDesign + ContrCoding + ContrTesting")
	
			// Evaluate the expected activity execution time
			.adicionaAjuste("ExpectedTime", "(ExpectedTime * 0.0) + FunctionPoints * (30.0 / AvgFPMonth) * (Contribution / 100)")
			.adicionaAjuste("MaximumTime", "(MaximumTime * 0.0) + FunctionPoints * (30.0 / MinFPMonth) * (Contribution / 100)")
			.adicionaAjuste("MinimumTime", "(MinimumTime * 0.0) + FunctionPoints * (30.0 / MaxFPMonth) * (Contribution / 100)"));

		cenario.adicionaRestricao(new Restricao("TheActivity", "FunctionPointMeasure", "TheActivity"));
		return cenario;
	}

	private Cenario criaCenarioGeracaoErroPontosFuncao()
	{
		Cenario cenario = new Cenario("ErrorGeneration");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			.adicionaProcesso("NewErrors", "(AnalysisTask + ArchitecTask + DesignTask + CodingTask) * ErrorGeneration")
			.adicionaProcesso("ErrorGen", "((AnalysisTask * Team.ErrorRateAnalysis)  + (ArchitecTask * Team.ErrorRateDesign) + (DesignTask * Team.ErrorRateDesign) + (CodingTask * Team.ErrorRateCoding))")
			.adicionaProcesso("ErrorGeneration", "if (ExecutionTime > 0, ErrorsFP * FunctionPoints * ErrorGen * RTExecTime / ExecutionTime, 0)")
			.adicionaProcesso("ErrorsFP", "(AnalysisTask * 0.6)  + (ArchitecTask * 0.18) + (DesignTask * 0.57) + (CodingTask * 1.05)")
			.adicionaAjuste("RTErrors", "RTErrors + NewErrors"));

		cenario.adicionaRestricao(new Restricao("TheActivity", "FunctionPointMeasure", "TheActivity"));
		return cenario;
	}

	private Cenario criaCenarioPropragacaoErros()
	{
		Cenario cenario = new Cenario("ErrorPropagation");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			.adicionaProcesso("SuccessorCount", "COUNT(Successors)")
			.adicionaProcesso("ErrorsToGo", "if (SuccessorCount > 0, (Errors + RTErrors*DT) /  SuccessorCount, 0)")
			.adicionaProcesso("Initializing", "AND (ExecutionTime > 0.0, ExecutedTime < 0.001, Executing)")
			.adicionaProcesso("ReadyToInherit", "OR (AND(ExecutionTime < 0.001, PrecConcluded, Errors < 0.001), Initializing)")
			.adicionaProcesso("InheritedErrors", "if (ReadyToInherit, GROUPSUM (Precedences, ErrorsToGo), 0)")
			.adicionaAjuste("RTErrors", "RTErrors + InheritedErrors / DT"));

		cenario.adicionaRestricao(new Restricao("TheActivity", "Precedences", "FunctionPointMeasure", "TheActivity"));
		return cenario;
	}

	private Cenario criaCenarioRegeneracaoErros()
	{
		Cenario cenario = new Cenario("ErrorRegeneration");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			.adicionaProcesso("InheritedDensity", "InheritedErrors / FunctionPoints")
			.adicionaProcesso("RegenErrors", "(AnalysisTask + ArchitecTask + DesignTask + CodingTask) * InheritedErrors * 0.24 * RegenFactor")
			.adicionaProcesso("RegenFactor", "Max (1, LOOKUP (ActiveErrosDens, InheritedDensity , 0, 10))")
			.adicionaTabela("ActiveErrosDens", "VALUES(1, 1.1, 1.2, 1.325, 1.45, 1.6, 2.0, 2.5, 3.25, 4.35, 6.0)")
			.adicionaAjuste("RTErrors", "RTErrors + RegenErrors / DT"));

		cenario.adicionaRestricao(new Restricao("TheActivity", "ErrorPropagation", "TheActivity"));
		cenario.adicionaRestricao(new Restricao("TheActivity", "FunctionPointMeasure", "TheActivity"));
		return cenario;
	}

	private Cenario criaCenarioCorrecaoErros()
	{
		Cenario cenario = new Cenario("ErrorCorretion");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			.adicionaPropriedade("Target", 95.0)
	
			// Average errors per function point
			.adicionaProcesso("AvgErrorFP", "2.4")
	
			// Errors corrected acummulator for the activity
			.adicionaRepositorio("ErrorsCorrected", "0")
			.adicionaTaxa("RTCorrection", "ErrorsCorrected", "CorrErrors")
	
			// Error correction in the activity
			.adicionaTaxa("RTCorrErrors", "Errors", "-CorrErrors")
			.adicionaProcesso("CorrErrors", "(InspectionTask + TestingTask) * RTExecTime * Productivity / (DetectionCost * DT)")
	
			// Cost to correct an error
			.adicionaProcesso("DetectionCost", "0.28")
	
			// Adjustment of time for testng activities
			.adicionaTaxa("RTTesting", "ExecutionTime", "if (AND(Executing, TestingTask > 0), -ExecutionTime+ExecutedTime+TestingEffort, 0) / DT")
			.adicionaProcesso("TestingEffort", "TestingDifference * DetectionCost")
			.adicionaProcesso("TestingDifference", "Max(Errors + InheritedErrors - CorrErrors - TestingTarget, 0)")
			.adicionaProcesso("TestingTarget", "FunctionPoints * AvgErrorFP * (1 - Target / 100.0)"));

		cenario.adicionaRestricao(new Restricao("TheActivity", "FunctionPointMeasure", "TheActivity"));
		return cenario;
	}

	private Cenario criaCenarioCorrecaoDensidadeErros()
	{
		Cenario cenario = new Cenario("ErrorCorrectionDensity");
		
		cenario.adicionaConexao(new Conexao("TheActivity", "Activity")
			.adicionaProcesso("ErrorDensityMultiplier", "Max (1, LOOKUP (TableErrorDensityMultiplier, ErrorDensity, 0, 1))")
			.adicionaTabela("TableErrorDensityMultiplier", "VALUES(8, 6.75, 5.25, 4, 3, 2, 1.8, 1.6, 1.2, 1.1, 1)")
			.adicionaProcesso("ErrorDensity", "(Errors + RTErrors*DT) / FunctionPoints")
			.adicionaAjuste("DetectionCost", "DetectionCost * ErrorDensityMultiplier"));

		cenario.adicionaRestricao(new Restricao("TheActivity", "ErrorCorrection", "TheActivity"));
		return cenario;
	}

}