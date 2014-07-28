
# Load data - micro do Marcio
data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Load data - micro do Luiz
# data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

 # Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);
data$config <- factor(data$config, configs);

instanceCodelist <- c("I0", "I1", "I2", "I3", "I4", "I5");
instanceNamelist <- c("ACAD", "PSOA", "PARM", "OPMET", "WEBMET", "WEBAMHS");

windows(10, 4);
par(mfrow=c(1,1), mai=c(0.4, 0.4, 0.2, 0.2), mgp=c(0.5, 0.5, 0), cex.axis=0.75);
yrange <- c(0, 0);

for (instancia_ in instances)
{
	subdata <- subset(data, inst == instancia_ & config == "GA");
	mar <- subset(data, inst == instancia_ & config == "CPM")$gd;
	cpm <- subset(data, inst == instancia_ & config == "CPM")$gd;
	sh <- subset(data, inst == instancia_ & config == "SH")$gd;
	yrange <- c(min(subdata$gd, mar, cpm, sh, yrange[1]), max(subdata$gd, mar, cpm, sh, yrange[2]));
}

subdata <- subset(data, inst == "I0" & config == "GA");
mar <- subset(data, inst == "I0" & config == "CPM")$gd;
cpm <- subset(data, inst == "I0" & config == "CPM")$gd;
sh <- subset(data, inst == "I0" & config == "SH")$gd;
boxplot(subdata$gd, outline=FALSE, ylim=yrange, xlim=c(.8,6.2), cex.lab=0.75);
text(.9, mar, 'x');	
text(1, cpm, '+');	
text(1.1, sh, '*');

subdata <- subset(data, inst == "I1" & config == "GA");
mar <- subset(data, inst == "I1" & config == "CPM")$gd;
cpm <- subset(data, inst == "I1" & config == "CPM")$gd;
sh <- subset(data, inst == "I1" & config == "SH")$gd;
boxplot(subdata$gd, add=TRUE, at=2);	
text(1.9, mar, 'x');	
text(2, cpm, '+');	
text(2.1, sh, '*');

subdata <- subset(data, inst == "I2" & config == "GA");
mar <- subset(data, inst == "I2" & config == "CPM")$gd;
cpm <- subset(data, inst == "I2" & config == "CPM")$gd;
sh <- subset(data, inst == "I2" & config == "SH")$gd;
boxplot(subdata$gd, add=TRUE, at=3);	
text(2.9, mar, 'x');	
text(3, cpm, '+');	
text(3.1, sh, '*');

subdata <- subset(data, inst == "I3" & config == "GA");
mar <- subset(data, inst == "I3" & config == "CPM")$gd;
cpm <- subset(data, inst == "I3" & config == "CPM")$gd;
sh <- subset(data, inst == "I3" & config == "SH")$gd;
boxplot(subdata$gd, add=TRUE, at=4);	
text(3.9, mar, 'x');	
text(4, cpm, '+');	
text(4.1, sh, '*');

subdata <- subset(data, inst == "I4" & config == "GA");
mar <- subset(data, inst == "I4" & config == "CPM")$gd;
cpm <- subset(data, inst == "I4" & config == "CPM")$gd;
sh <- subset(data, inst == "I4" & config == "SH")$gd;
boxplot(subdata$gd, add=TRUE, at=5);	
text(4.9, mar, 'x');	
text(5, cpm, '+');	
text(5.1, sh, '*');

subdata <- subset(data, inst == "I5" & config == "GA");
mar <- subset(data, inst == "I5" & config == "CPM")$gd;
cpm <- subset(data, inst == "I5" & config == "CPM")$gd;
sh <- subset(data, inst == "I5" & config == "SH")$gd;
boxplot(subdata$gd, add=TRUE, at=6);	
text(5.9, mar, 'x');	
text(6, cpm, '+');	
text(6.1, sh, '*');

axis(1, 1:6, instanceNamelist, cex=0.6);
	
#for (instancia_ in instances)
#{
#	subdata <- subset(data, inst == instancia_ & config == "GA");
#	mar <- subset(data, inst == instancia_ & config == "CPM")$gd;
#	cpm <- subset(data, inst == instancia_ & config == "CPM")$gd;
#	sh <- subset(data, inst == instancia_ & config == "SH")$gd;
#	
#	#yrange <- c(min(c(subdata$gd, mar, cpm, sh)), max(c(subdata$gd, mar, cpm, sh)));
#	
#	boxplot(subdata$gd, main=instanceNamelist[which(instanceCodelist==instancia_)], outline=FALSE, ylim=yrange, add=TRUE);	
#	text(.9, mar, 'x');	
#	text(1, cpm, '+');	
#	text(1.1, sh, '*');
#}
