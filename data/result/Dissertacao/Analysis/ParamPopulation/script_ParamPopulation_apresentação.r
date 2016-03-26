# limpa todas as variaveis
rm(list = ls());
	
data <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/Analysis/ParamStopCriteria/data.txt", header=TRUE);

configs <- unique(as.character(data$config));
	instances <- unique(data$inst);
	
	data$config <- factor(data$config, configs);

	# Correct order of presentation
	instanceCodelist <- c("I0", "I5", "I4", "I3", "I1", "I2");
	instanceNamelist <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");
	
	instanceCodelist <- c("I0", "I1", "I2", "I3", "I4", "I5");
	instanceNamelist <- c("ACAD", "OMET", "PARM", "PSOA", "WAMS", "WMET");

	windows(7.7, 5);
	par(mfrow=c(2,3), mai=c(0.4, 0.4, 0.2, 0.2), mgp=c(0.5, 0.5, 0), cex.axis=1.0);
	yrange <- c(0, 0);
	
	
	
	subdata1 <- subset(data, inst == "I0");
	yrange <- c(min(subdata1$gd), max(subdata1$gd));

	subdata <- subset(subdata1, config == "nsga05k2x");
	boxplot(subdata$gd, main="ACAD", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	
	subdata <- subset(subdata1, config == "nsga10k2x");
	boxplot(subdata$gd, add=TRUE, at=2);	
	
	subdata <- subset(subdata1, config == "nsga20k2x");
	boxplot(subdata$gd, add=TRUE, at=3);	
	
	subdata <- subset(subdata1, config == "nsga50k2x");
	boxplot(subdata$gd, add=TRUE, at=4);	
	
	subdata <- subset(subdata1, config == "nsga100k2x");
	boxplot(subdata$gd, add=TRUE, at=5);	
	
	subdata <- subset(subdata1, config == "nsga150k2x");
	boxplot(subdata$gd, add=TRUE, at=6);	

	configs <- c("5K", "10k", "20K", "50K", "100k", "150K");
	axis(1, 1:6, configs, cex=0.6);
	
	
	subdata1 <- subset(data, inst == "I1");
	yrange <- c(min(subdata1$gd), max(subdata1$gd));

	subdata <- subset(subdata1, config == "nsga05k2x");
	boxplot(subdata$gd, main="OMET", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	
	subdata <- subset(subdata1, config == "nsga10k2x");
	boxplot(subdata$gd, add=TRUE, at=2);	
	
	subdata <- subset(subdata1, config == "nsga20k2x");
	boxplot(subdata$gd, add=TRUE, at=3);	
	
	subdata <- subset(subdata1, config == "nsga50k2x");
	boxplot(subdata$gd, add=TRUE, at=4);	
	
	subdata <- subset(subdata1, config == "nsga100k2x");
	boxplot(subdata$gd, add=TRUE, at=5);	
	
	subdata <- subset(subdata1, config == "nsga150k2x");
	boxplot(subdata$gd, add=TRUE, at=6);	

	configs <- c("5K", "10k", "20K", "50K", "100k", "150K");
	axis(1, 1:6, configs, cex=0.6);
	
	
	
	subdata1 <- subset(data, inst == "I2");
	yrange <- c(min(subdata1$gd), max(subdata1$gd));

	subdata <- subset(subdata1, config == "nsga05k2x");
	boxplot(subdata$gd, main="PARM", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	
	subdata <- subset(subdata1, config == "nsga10k2x");
	boxplot(subdata$gd, add=TRUE, at=2);	
	
	subdata <- subset(subdata1, config == "nsga20k2x");
	boxplot(subdata$gd, add=TRUE, at=3);	
	
	subdata <- subset(subdata1, config == "nsga50k2x");
	boxplot(subdata$gd, add=TRUE, at=4);	
	
	subdata <- subset(subdata1, config == "nsga100k2x");
	boxplot(subdata$gd, add=TRUE, at=5);	
	
	subdata <- subset(subdata1, config == "nsga150k2x");
	boxplot(subdata$gd, add=TRUE, at=6);	

	configs <- c("5K", "10k", "20K", "50K", "100k", "150K");
	axis(1, 1:6, configs, cex=0.6);
	
	
	
	subdata1 <- subset(data, inst == "I3");
	yrange <- c(min(subdata1$gd), max(subdata1$gd));

	subdata <- subset(subdata1, config == "nsga05k2x");
	boxplot(subdata$gd, main="PSOA", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	
	subdata <- subset(subdata1, config == "nsga10k2x");
	boxplot(subdata$gd, add=TRUE, at=2);	
	
	subdata <- subset(subdata1, config == "nsga20k2x");
	boxplot(subdata$gd, add=TRUE, at=3);	
	
	subdata <- subset(subdata1, config == "nsga50k2x");
	boxplot(subdata$gd, add=TRUE, at=4);	
	
	subdata <- subset(subdata1, config == "nsga100k2x");
	boxplot(subdata$gd, add=TRUE, at=5);	
	
	subdata <- subset(subdata1, config == "nsga150k2x");
	boxplot(subdata$gd, add=TRUE, at=6);	

	configs <- c("5K", "10k", "20K", "50K", "100k", "150K");
	axis(1, 1:6, configs, cex=0.6);
	
	
	
	subdata1 <- subset(data, inst == "I4");
	yrange <- c(min(subdata1$gd), max(subdata1$gd));

	subdata <- subset(subdata1, config == "nsga05k2x");
	boxplot(subdata$gd, main="WAMS", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	
	subdata <- subset(subdata1, config == "nsga10k2x");
	boxplot(subdata$gd, add=TRUE, at=2);	
	
	subdata <- subset(subdata1, config == "nsga20k2x");
	boxplot(subdata$gd, add=TRUE, at=3);	
	
	subdata <- subset(subdata1, config == "nsga50k2x");
	boxplot(subdata$gd, add=TRUE, at=4);	
	
	subdata <- subset(subdata1, config == "nsga100k2x");
	boxplot(subdata$gd, add=TRUE, at=5);	
	
	subdata <- subset(subdata1, config == "nsga150k2x");
	boxplot(subdata$gd, add=TRUE, at=6);	

	configs <- c("5K", "10k", "20K", "50K", "100k", "150K");
	axis(1, 1:6, configs, cex=0.6);
	
	
	subdata1 <- subset(data, inst == "I5");
	yrange <- c(min(subdata1$gd), max(subdata1$gd));

	subdata <- subset(subdata1, config == "nsga05k2x");
	boxplot(subdata$gd, main="WMET", outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
	
	subdata <- subset(subdata1, config == "nsga10k2x");
	boxplot(subdata$gd, add=TRUE, at=2);	
	
	subdata <- subset(subdata1, config == "nsga20k2x");
	boxplot(subdata$gd, add=TRUE, at=3);	
	
	subdata <- subset(subdata1, config == "nsga50k2x");
	boxplot(subdata$gd, add=TRUE, at=4);	
	
	subdata <- subset(subdata1, config == "nsga100k2x");
	boxplot(subdata$gd, add=TRUE, at=5);	
	
	subdata <- subset(subdata1, config == "nsga150k2x");
	boxplot(subdata$gd, add=TRUE, at=6);	

	configs <- c("5K", "10k", "20K", "50K", "100k", "150K");
	axis(1, 1:6, configs, cex=0.6);
	
	