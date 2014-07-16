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
		analyzePopulationSize();
	}

	protected static void analyzeStopCriteria() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga5k", "data/result/NSGA/5K/nsga5k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga10k", "data/result/NSGA/10K/nsga10k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga20k", "data/result/NSGA/20K/nsga20k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("rs5k", "data/result/RS/5K/rs5k.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("rs10k", "data/result/RS/10K/rs10k.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("rs20k", "data/result/RS/20K/rs20k.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("rs50k", "data/result/RS/50K/rs50k.txt", 6, 50, 3));
		analyzer.analyzeInstanceFrontiers();
	}

	protected static void analyzePopulationSize() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga50k2x", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k4x", "data/result/NSGA/50K/4X/nsga50k4x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k8x", "data/result/NSGA/50K/8X/nsga50k8x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k16x", "data/result/NSGA/50K/16X/nsga50k16x.txt", 6, 50, 3));
		analyzer.analyzeInstanceFrontiers();
	}
}