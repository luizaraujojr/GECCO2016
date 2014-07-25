
# Load data - micro do Marcio
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

 # Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);
data$config <- factor(data$config,configs);

instanceCodelist <- c("I0", "I1", "I2", "I3", "I4", "I5");
instanceNamelist <- c("ACAD", "PSOA", "PARM", "OPMET", "WEBMET", "WEBAMHS");

windows(16, 3.3);
par(mfrow=c(1,6), mai=c(0.5, 0.2, 0.2, 0.1), mgp=c(3, 0.5, 0), cex.main=0.8);

for (instancia_ in instances)
{
	subdata <- subset(data, inst == instancia_);
	boxplot(gd~config, data=subdata, main=instanceNamelist[which(instanceCodelist==instancia_)], outline=FALSE);
}
