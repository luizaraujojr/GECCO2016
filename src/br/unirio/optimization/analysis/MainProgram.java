package br.unirio.optimization.analysis;

import java.util.Locale;

import unirio.experiments.multiobjective.analyzer.MultiExperimentAnalyzer;
import unirio.experiments.multiobjective.reader.ExperimentFileReader;
import unirio.experiments.multiobjective.reader.ExperimentFileReaderException;

public class MainProgram
{
	public static final void main(String[] args) throws Exception
	{
		Locale.setDefault(new Locale("en", "US"));
		//analyzeStopCriteria();
		//analyzePopulationSize();
		//analyzeRandomSearch();
		analyzeOMS();
		//analyzeNoError();
	}

	/**
	 * Gera os dados referentes ao tunning do critério de parada
	 */
	protected static void analyzeStopCriteria() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga5k", "data/result/NSGA/5K/nsga5k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga10k", "data/result/NSGA/10K/nsga10k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga20k", "data/result/NSGA/20K/nsga20k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}

	/**
	 * Gera os dados referentes ao tunning do tamanho da população
	 */
	protected static void analyzePopulationSize() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga50k2x", "data/result/BaseData/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k4x", "data/result/BaseData/NSGA/50K/4X/nsga50k4x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k8x", "data/result/BaseData/NSGA/50K/8X/nsga50k8x.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}

	/**
	 * Gera os dados referentes à RQ1
	 */
	protected static void analyzeRandomSearch() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga50k2x", "data/result/BaseData/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("rs50k", "data/result/BaseData/RS/50K/rs50k.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}

	/**
	 * Gera os dados referentes à RQ2
	 */
	protected static void analyzeOMS() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("GA", "data/result/BaseData/NSGA/50K/2X/nsga50k2xerror1.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("MAR", "data/result/BaseData/OMS/Margarine/Margarine_error1.txt", 6, 1, 3));
		analyzer.addExperimentResult(reader.execute("SH", "data/result/BaseData/OMS/SecondHalf/SecondHalf_error1.txt", 6, 1, 3));
		analyzer.addExperimentResult(reader.execute("CPM", "data/result/BaseData/OMS/CPM/CPM_error1.txt", 6, 1, 3));
		analyzer.analyzeCycleFrontiers();
	}

	/**
	 * Gera os dados referentes à RQ3
	 */
	protected static void analyzeNoError() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("NSGANE", "data/result/BaseData/NSGA/50K/2X/nsga50k2xnoError.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("NSGA", "data/result/BaseData/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}
}