##################################################
#####            Research Request 3          #####
##################################################

# limpa todas as variaveis
rm(list = ls());

# Calculating the Effect Size of Varga & Delaney (A12)
vargha.delaney <- function(r1, r2) {
	m <- length(r1);
	n <- length(r2);
	return ((sum(rank(c(r1, r2))[seq_along(r1)]) / m - (m + 1) / 2) / n);
}

# Load data - micro do Marcio
# data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);
data <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/RQ3/data - error1.txt", header=TRUE);

# Load data - micro do Luiz
# data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- c("I0", "I5", "I4", "I3", "I1", "I2");
instanceNames <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");

# Inference tests on an instance basis (IC)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");

	pv <- wilcox.test(NSGA$best, NSGANE$best)$p.value;
	print(paste("IC: p-Value for Wilcox:", instanceName, "(NSGA,NSGANE)=", pv, sep=" "));
}

# Inference tests on an instance basis (HV)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");

	pv <- wilcox.test(NSGA$hv, NSGANE$hv)$p.value;
	print(paste("HV: p-Value for Wilcox:", instanceName, "(NSGA,NSGANE)=", pv, sep=" "));
}

# Inference tests on an instance basis (GD)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");

	pv <- wilcox.test(NSGA$gd, mu=NSGANE$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instanceName, "(NSGA,NSGANE)=", pv, sep=" "));
}

# Effect Size tests 
effectSizeNames <- c("ic", "hv", "gd");
effectSize <- matrix(nrow=length(instances), ncol=length(effectSizeNames), dimnames=list(instanceNames, effectSizeNames));

for (instance_ in instances)
{
	instanceName <- instanceNames[which(instances==instance_)];
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	effectSize[instanceName, "ic"] <- vargha.delaney(NSGA$best, NSGANE$best);
	effectSize[instanceName, "hv"] <- vargha.delaney(NSGA$hv, NSGANE$hv);
	effectSize[instanceName, "gd"] <- 1.0 - vargha.delaney(NSGA$gd, NSGANE$gd);
}

effectSize;

# Calculating means and standard deviation
## Create matrices to hold mean and stdev values
mean_count <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));

for (config_ in configs)
{
	for (instance_ in instances)
	{
		newdata <- subset(data, inst == instance_ & config == config_);
		instanceName <- instanceNames[which(instances==instance_)];

		mean_count[instanceName, config_] <- mean(newdata$count);
		mean_ic[instanceName, config_] <- mean(newdata$best);
		mean_hv[instanceName, config_] <- mean(newdata$hv);
		mean_gd[instanceName, config_] <- mean(newdata$gd);
		
		sd_ic[instanceName, config_] <- sd(newdata$best);
		sd_hv[instanceName, config_] <- sd(newdata$hv);
		sd_gd[instanceName, config_] <- sd(newdata$gd);
	}
}

mean_ic;
mean_hv;
mean_gd;

sd_ic;
sd_hv;
sd_gd;