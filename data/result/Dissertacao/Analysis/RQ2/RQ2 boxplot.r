	# limpa todas as variaveis
	rm(list = ls());

	# Load data - micro do Marcio
	# data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);
	#data <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/rq2/data - error1.txt", header=TRUE);

	# Load data - micro do Luiz
	data <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/Analysis/RQ2/data_experiment_rq2.txt", header=TRUE);

	 # Separate sequence configuration and instances
	configs <- unique(as.character(data$config));
	instances <- unique(data$inst);
	data$config <- factor(data$config, configs);

	# Correct order of presentation
	instanceCodelist <- c("I0", "I5", "I4", "I3", "I1", "I2");
	instanceNamelist <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");
	
	instanceCodelist <- c("I0", "I1", "I2", "I3", "I4", "I5");
	instanceNamelist <- c("ACAD", "OMET", "PARM", "PSOA", "WAMS", "WMET");

	windows(8, 6);
	par(mfrow=c(2,2), mai=c(0.4, 0.4, 0.2, 0.2), mgp=c(0.5, 0.5, 0), cex.axis=1.0);
	yrange <- c(0, 0);

	#
	# IC
	#
	for (instancia_ in instances)
	{
		subdata <- subset(data, inst == instancia_ & config == "nsga150k2x");
		mar <- subset(data, inst == instancia_ & config == "MAR")$best;
		cpm <- subset(data, inst == instancia_ & config == "CPM")$best;
		sh <- subset(data, inst == instancia_ & config == "SH")$best;
		yrange <- c(min(subdata$best, mar, cpm, sh, yrange[1]), max(subdata$best, mar, cpm, sh, yrange[2]));
	}

	subdata <- subset(data, inst == "I0" & config == "nsga150k2x");
	mar <- subset(data, inst == "I0" & config == "MAR")$best;
	cpm <- subset(data, inst == "I0" & config == "CPM")$best;
	sh <- subset(data, inst == "I0" & config == "SH")$best;
	boxplot(subdata$best, main="Contributions", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	text(.9, mar, 'x', cex=1.5);	
	text(1, cpm, '+', cex=1.5);	
	text(1.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I1" & config == "nsga150k2x");
	mar <- subset(data, inst == "I1" & config == "MAR")$best;
	cpm <- subset(data, inst == "I1" & config == "CPM")$best;
	sh <- subset(data, inst == "I1" & config == "SH")$best;
	boxplot(subdata$best, add=TRUE, at=2);	
	text(1.9, mar, 'x', cex=1.5);	
	text(2, cpm, '+', cex=1.5);	
	text(2.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I2" & config == "nsga150k2x");
	mar <- subset(data, inst == "I2" & config == "MAR")$best;
	cpm <- subset(data, inst == "I2" & config == "CPM")$best;
	sh <- subset(data, inst == "I2" & config == "SH")$best;
	boxplot(subdata$best, add=TRUE, at=3);	
	text(2.9, mar, 'x', cex=1.5);	
	text(3, cpm, '+', cex=1.5);	
	text(3.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I3" & config == "nsga150k2x");
	mar <- subset(data, inst == "I3" & config == "MAR")$best;
	cpm <- subset(data, inst == "I3" & config == "CPM")$best;
	sh <- subset(data, inst == "I3" & config == "SH")$best;
	boxplot(subdata$best, add=TRUE, at=4);	
	text(3.9, mar, 'x', cex=1.5);	
	text(4, cpm, '+', cex=1.5);	
	text(4.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I4" & config == "nsga150k2x");
	mar <- subset(data, inst == "I4" & config == "MAR")$best;
	cpm <- subset(data, inst == "I4" & config == "CPM")$best;
	sh <- subset(data, inst == "I4" & config == "SH")$best;
	boxplot(subdata$best, add=TRUE, at=5);	
	text(4.9, mar, 'x', cex=1.5);	
	text(5, cpm, '+', cex=1.5);	
	text(5.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I5" & config == "nsga150k2x");
	mar <- subset(data, inst == "I5" & config == "MAR")$best;
	cpm <- subset(data, inst == "I5" & config == "CPM")$best;
	sh <- subset(data, inst == "I5" & config == "SH")$best;
	boxplot(subdata$best, add=TRUE, at=6);	
	text(5.9, mar, 'x', cex=1.5);	
	text(6, cpm, '+', cex=1.5);	
	text(6.1, sh, '*', cex=1.5);

	axis(1, 1:6, instanceNamelist, cex=0.6);

	#
	# HV
	#
	for (instancia_ in instances)
	{
		subdata <- subset(data, inst == instancia_ & config == "nsga150k2x");
		mar <- subset(data, inst == instancia_ & config == "MAR")$hv;
		cpm <- subset(data, inst == instancia_ & config == "CPM")$hv;
		sh <- subset(data, inst == instancia_ & config == "SH")$hv;
		yrange <- c(min(subdata$hv, mar, cpm, sh, yrange[1]), max(subdata$hv, mar, cpm, sh, yrange[2]));
	}

	subdata <- subset(data, inst == "I0" & config == "nsga150k2x");
	mar <- subset(data, inst == "I0" & config == "MAR")$hv;
	cpm <- subset(data, inst == "I0" & config == "CPM")$hv;
	sh <- subset(data, inst == "I0" & config == "SH")$hv;
	boxplot(subdata$hv, main="Hypervolume", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	text(.9, mar, 'x', cex=1.5);	
	#text(1, cpm, '+', cex=1.5);	
	text(1.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I1" & config == "nsga150k2x");
	mar <- subset(data, inst == "I1" & config == "MAR")$hv;
	cpm <- subset(data, inst == "I1" & config == "CPM")$hv;
	sh <- subset(data, inst == "I1" & config == "SH")$hv;
	boxplot(subdata$hv, add=TRUE, at=2);	
	text(1.9, mar, 'x', cex=1.5);	
	text(2, cpm, '+', cex=1.5);	
	text(2.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I2" & config == "nsga150k2x");
	mar <- subset(data, inst == "I2" & config == "MAR")$hv;
	cpm <- subset(data, inst == "I2" & config == "CPM")$hv;
	sh <- subset(data, inst == "I2" & config == "SH")$hv;
	boxplot(subdata$hv, add=TRUE, at=3);
	text(2.9, mar, 'x', cex=1.5);	
	#text(3, cpm, '+', cex=1.5);	
	text(3.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I3" & config == "nsga150k2x");
	mar <- subset(data, inst == "I3" & config == "MAR")$hv;
	cpm <- subset(data, inst == "I3" & config == "CPM")$hv;
	sh <- subset(data, inst == "I3" & config == "SH")$hv;
	boxplot(subdata$hv, add=TRUE, at=4);	
	text(3.9, mar, 'x', cex=1.5);	
	#text(4, cpm, '+', cex=1.5);	
	text(4.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I4" & config == "nsga150k2x");
	mar <- subset(data, inst == "I4" & config == "MAR")$hv;
	cpm <- subset(data, inst == "I4" & config == "CPM")$hv;
	sh <- subset(data, inst == "I4" & config == "SH")$hv;
	boxplot(subdata$hv, add=TRUE, at=5);	
	text(4.9, mar, 'x', cex=1.5);	
	text(5, cpm, '+', cex=1.5);	
	text(5.1, sh, '*', cex=1.5);
		
	subdata <- subset(data, inst == "I5" & config == "nsga150k2x");
	mar <- subset(data, inst == "I5" & config == "MAR")$hv;
	cpm <- subset(data, inst == "I5" & config == "CPM")$hv;
	sh <- subset(data, inst == "I5" & config == "SH")$hv;
	boxplot(subdata$hv, add=TRUE, at=6);	
	text(5.9, mar, 'x', cex=1.5);	
	text(6, cpm, '+', cex=1.5);	
	text(6.1, sh, '*', cex=1.5);

	axis(1, 1:6, instanceNamelist, cex=0.6);

	#
	# GD
	#
	for (instancia_ in instances)
	{
		subdata <- subset(data, inst == instancia_ & config == "nsga150k2x");
		mar <- subset(data, inst == instancia_ & config == "MAR")$gd;
		cpm <- subset(data, inst == instancia_ & config == "CPM")$gd;
		sh <- subset(data, inst == instancia_ & config == "SH")$gd;
		yrange <- c(min(subdata$gd, mar, cpm, sh, yrange[1]), max(subdata$gd, mar, cpm, sh, yrange[2]));
	}

	subdata <- subset(data, inst == "I0" & config == "nsga150k2x");
	mar <- subset(data, inst == "I0" & config == "MAR")$gd;
	cpm <- subset(data, inst == "I0" & config == "CPM")$gd;
	sh <- subset(data, inst == "I0" & config == "SH")$gd;
	boxplot(subdata$gd, main="Generational Distance", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	text(.9, mar, 'x', cex=1.5);	
	#text(1, cpm, '+', cex=1.5);	
	text(1.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I1" & config == "nsga150k2x");
	mar <- subset(data, inst == "I1" & config == "MAR")$gd;
	cpm <- subset(data, inst == "I1" & config == "CPM")$gd;
	sh <- subset(data, inst == "I1" & config == "SH")$gd;
	boxplot(subdata$gd, add=TRUE, at=2);	
	text(1.9, mar, 'x', cex=1.5);	
	text(2, cpm, '+', cex=1.5);	
	text(2.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I2" & config == "nsga150k2x");
	mar <- subset(data, inst == "I2" & config == "MAR")$gd;
	cpm <- subset(data, inst == "I2" & config == "CPM")$gd;
	sh <- subset(data, inst == "I2" & config == "SH")$gd;
	boxplot(subdata$gd, add=TRUE, at=3);	
	text(2.9, mar, 'x', cex=1.5);	
	#text(3, cpm, '+', cex=1.5);	
	text(3.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I3" & config == "nsga150k2x");
	mar <- subset(data, inst == "I3" & config == "MAR")$gd;
	cpm <- subset(data, inst == "I3" & config == "CPM")$gd;
	sh <- subset(data, inst == "I3" & config == "SH")$gd;
	boxplot(subdata$gd, add=TRUE, at=4);	
	text(3.9, mar, 'x', cex=1.5);	
	#text(4, cpm, '+', cex=1.5);	
	text(4.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I4" & config == "nsga150k2x");
	mar <- subset(data, inst == "I4" & config == "MAR")$gd;
	cpm <- subset(data, inst == "I4" & config == "CPM")$gd;
	sh <- subset(data, inst == "I4" & config == "SH")$gd;
	boxplot(subdata$gd, add=TRUE, at=5);	
	text(4.9, mar, 'x', cex=1.5);	
	text(5, cpm, '+', cex=1.5);	
	text(5.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I5" & config == "nsga150k2x");
	mar <- subset(data, inst == "I5" & config == "MAR")$gd;
	cpm <- subset(data, inst == "I5" & config == "CPM")$gd;
	sh <- subset(data, inst == "I5" & config == "SH")$gd;
	boxplot(subdata$gd, add=TRUE, at=6);	
	text(5.9, mar, 'x', cex=1.5);	
	text(6, cpm, '+', cex=1.5);	
	text(6.1, sh, '*', cex=1.5);

	axis(1, 1:6, instanceNamelist, cex=0.6);


	#
	# SP
	#
	for (instancia_ in instances)
	{
		subdata <- subset(data, inst == instancia_ & config == "nsga150k2x");
		mar <- subset(data, inst == instancia_ & config == "MAR")$sp;
		cpm <- subset(data, inst == instancia_ & config == "CPM")$sp;
		sh <- subset(data, inst == instancia_ & config == "SH")$sp;
		yrange <- c(min(subdata$sp, mar, cpm, sh, yrange[1]), max(subdata$sp, mar, cpm, sh, yrange[2]));
	}

	subdata <- subset(data, inst == "I0" & config == "nsga150k2x");
	mar <- subset(data, inst == "I0" & config == "MAR")$sp;
	cpm <- subset(data, inst == "I0" & config == "CPM")$sp;
	sh <- subset(data, inst == "I0" & config == "SH")$sp;
	boxplot(subdata$sp, main="Spread", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	text(.9, mar, 'x', cex=1.5);	
	#text(1, cpm, '+', cex=1.5);	
	text(1.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I1" & config == "nsga150k2x");
	mar <- subset(data, inst == "I1" & config == "MAR")$sp;
	cpm <- subset(data, inst == "I1" & config == "CPM")$sp;
	sh <- subset(data, inst == "I1" & config == "SH")$sp;
	boxplot(subdata$sp, add=TRUE, at=2);	
	text(1.9, mar, 'x', cex=1.5);	
	text(2, cpm, '+', cex=1.5);	
	text(2.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I2" & config == "nsga150k2x");
	mar <- subset(data, inst == "I2" & config == "MAR")$sp;
	cpm <- subset(data, inst == "I2" & config == "CPM")$sp;
	sh <- subset(data, inst == "I2" & config == "SH")$sp;
	boxplot(subdata$sp, add=TRUE, at=3);	
	text(2.9, mar, 'x', cex=1.5);	
	#text(3, cpm, '+', cex=1.5);	
	text(3.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I3" & config == "nsga150k2x");
	mar <- subset(data, inst == "I3" & config == "MAR")$sp;
	cpm <- subset(data, inst == "I3" & config == "CPM")$sp;
	sh <- subset(data, inst == "I3" & config == "SH")$sp;
	boxplot(subdata$sp, add=TRUE, at=4);	
	text(3.9, mar, 'x', cex=1.5);	
	#text(4, cpm, '+', cex=1.5);	
	text(4.1, sh, '*', cex=1.5);
	
	subdata <- subset(data, inst == "I4" & config == "nsga150k2x");
	mar <- subset(data, inst == "I4" & config == "MAR")$sp;
	cpm <- subset(data, inst == "I4" & config == "CPM")$sp;
	sh <- subset(data, inst == "I4" & config == "SH")$sp;
	boxplot(subdata$sp, add=TRUE, at=5);	
	text(4.9, mar, 'x', cex=1.5);	
	text(4, cpm, '+', cex=1.5);	
	text(4.1, sh, '*', cex=1.5);

	subdata <- subset(data, inst == "I5" & config == "nsga150k2x");
	mar <- subset(data, inst == "I5" & config == "MAR")$sp;
	cpm <- subset(data, inst == "I5" & config == "CPM")$sp	;
	sh <- subset(data, inst == "I5" & config == "SH")$sp;
	boxplot(subdata$sp, add=TRUE, at=6);	
	text(4.9, mar, 'x', cex=1.5);	
	text(5, cpm, '+', cex=1.5);	
	text(5.1, sh, '*', cex=1.5);

	axis(1, 1:6, instanceNamelist, cex=0.6);
