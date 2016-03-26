# clear all objects in memory
rm(list = ls());

# Load data - micro do Marcio
#gaOvertime <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/Analysis/RQ4/nsga_error1.txt", header=TRUE);
#gaOvertime <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/RQ4/nsga_overtime.txt", header=TRUE);
#gaNoError <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/Analysis/RQ4/nsga_noerror.txt", header=TRUE);

gaOvertime <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_error1_frontier_obj.txt", header=TRUE);
gaNoError <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_noerror_frontier_obj.txt", header=TRUE);

# Separate instanceNames
#instances <- c("I0", "I5", "I4", "I3", "I1", "I2");
instances <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");

# Creates and fills the correlation matrices
instanceTitles <- c();

for (instance_ in instances)
{
	for (i in 1:10)
	{
		#instanceName <- instanceNames[which(instances==instance_)];
		instanceTitles <- c(instanceTitles, paste(instance_, " #", i, sep=""));
	}
}
	
result <- matrix(nrow=length(instanceTitles), ncol=6, dimnames=list(instanceTitles, c("NOHmin", "NOHmax", "MKSne", "MKSot", "CSTne", "CSTot")));

for (instance_ in instances)
{
	#instanceName <- instanceNames[which(instances==instance_)];
	newdataOT <- subset(gaOvertime, inst == instance_);
	newdataNE <- subset(gaNoError, inst == instance_);

	min_noh <- min(newdataOT$noh, newdataNE$noh);
	max_noh <- max(newdataOT$noh, newdataNE$noh) + 0.01;
	step <- (max_noh - min_noh) / 10;
	
	for (i in 1:10)
	{
		lower <- min_noh + (i-1) * step;
		upper <- lower + step;
		
		subdataOT <- subset(newdataOT, noh >= lower & noh < upper);
		subdataNE <- subset(newdataNE, noh >= lower & noh < upper);
		
		lineName <- paste(instance_, " #", i, sep="");
		result[lineName, 1] <- lower;
		result[lineName, 2] <- upper;
		result[lineName, 3] <- mean(subdataNE$mks);
		result[lineName, 4] <- mean(subdataOT$mks);
		result[lineName, 5] <- mean(subdataNE$cst);
		result[lineName, 6] <- mean(subdataOT$cst);
	}
}

result;

aaaa <- subset(gaOvertime, inst == "PARM");
gaOvertime