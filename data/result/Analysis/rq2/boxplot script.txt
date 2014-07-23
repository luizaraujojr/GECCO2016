# Load data
	data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);
	
	replace (data$config, data$config=="nsga50k2x","NS");
	configs <- unique(as.character(data$config));
	data$config <- factor(data$config,configs);
	
	

	# Separate sequence generators and instances
	configs <- unique(as.character(data$config));
	instances <- unique(data$inst);
	indicator_ <- 'gd';

	instanceCodelist <- c("I0", "I1", "I2", "I3", "I4", "I5");
	instanceNamelist <- c("ACAD", "PSOA", "PARM", "OPMET", "WEBMET", "WEBAMHS");
	#estrategyCodelist <- c("nsga502x", "SecondHalf", "CPM", "Margarine");
	#estrategyNamelist <- c("NS", "SH", "CP", "MG");
	
	estrategyNamelist[which(estrategyCodelist=="CPM")];
		
	windows(21, 3.3);
	par(mfrow=c(1,6), mai=c(0.5, 0.2, 0.2, 0.1), mgp=c(3, 0.5, 0), cex.main=0.8, xaxt="2");
	#, font.main=1, cex.axis=0.7, xaxt="n", yaxt="n"); , las=2 , xaxt="n"
	#par(mar=c(.5,.4,.4,.2)) 

	for (instancia_ in instances)
	{
		subdata <- subset(data, inst == instancia_);
		boxplot(hv~config, data=subdata, main=instanceNamelist[which(instanceCodelist==instancia_)], outline=FALSE);
		axis(1, at = 1:4, labels=estrategyNamelist);
	}
