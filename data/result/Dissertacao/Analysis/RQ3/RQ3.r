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
#data <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/RQ3/data - error1.txt", header=TRUE);

# Load data - micro do Luiz
# data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);
data <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/Analysis/RQ3/data1.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- c("I0", "I5", "I4", "I3", "I1", "I2");
instanceNames <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");

# Inference tests on an instance basis (IC)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	nsga150k2xse <- subset(data, inst == instance_ & config == "nsga150k2xse");
	nsga150k2x <- subset(data, inst == instance_ & config == "nsga150k2x");

	pv <- wilcox.test(nsga150k2x$best, nsga150k2xse$best)$p.value;
	print(paste("IC: p-Value for Wilcox:", instanceName, "(nsga150k2x,nsga150k2xse)=", pv, sep=" "));
}

# Inference tests on an instance basis (HV)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	nsga150k2xse <- subset(data, inst == instance_ & config == "nsga150k2xse");
	nsga150k2x <- subset(data, inst == instance_ & config == "nsga150k2x");

	pv <- wilcox.test(nsga150k2x$hv, nsga150k2xse$hv)$p.value;
	print(paste("HV: p-Value for Wilcox:", instanceName, "(nsga150k2x,nsga150k2xse)=", pv, sep=" "));
}

# Inference tests on an instance basis (GD)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	nsga150k2xse <- subset(data, inst == instance_ & config == "nsga150k2xse");
	nsga150k2x <- subset(data, inst == instance_ & config == "nsga150k2x");

	pv <- wilcox.test(nsga150k2x$gd, mu=nsga150k2xse$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instanceName, "(nsga150k2x,nsga150k2xse)=", pv, sep=" "));
}

# Inference tests on an instance basis (SP)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	nsga150k2xse <- subset(data, inst == instance_ & config == "nsga150k2xse");
	nsga150k2x <- subset(data, inst == instance_ & config == "nsga150k2x");

	pv <- wilcox.test(nsga150k2x$gd, mu=nsga150k2xse$gd[1])$p.value;
	print(paste("SP: p-Value for Wilcox:", instanceName, "(nsga150k2x,nsga150k2xse)=", pv, sep=" "));
}

# Effect Size tests 
effectSizeNames <- c("ic", "hv", "gd", "sp");
effectSize <- matrix(nrow=length(instances), ncol=length(effectSizeNames), dimnames=list(instanceNames, effectSizeNames));

for (instance_ in instances)
{
	instanceName <- instanceNames[which(instances==instance_)];
	nsga150k2x <- subset(data, inst == instance_ & config == "nsga150k2x");
	nsga150k2xse <- subset(data, inst == instance_ & config == "nsga150k2xse");
	effectSize[instanceName, "ic"] <- vargha.delaney(nsga150k2x$best, nsga150k2xse$best);
	effectSize[instanceName, "hv"] <- vargha.delaney(nsga150k2x$hv, nsga150k2xse$hv);
	effectSize[instanceName, "gd"] <- 1.0 - vargha.delaney(nsga150k2x$gd, nsga150k2xse$gd);
	effectSize[instanceName, "sp"] <- 1.0 - vargha.delaney(nsga150k2x$sp, nsga150k2xse$sp);
}

effectSize;

# Calculating means and standard deviation
## Create matrices to hold mean and stdev values
mean_count <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_sp <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
sd_sp <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));

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
		mean_sp[instanceName, config_] <- mean(newdata$sp);
		
		sd_ic[instanceName, config_] <- sd(newdata$best);
		sd_hv[instanceName, config_] <- sd(newdata$hv);
		sd_gd[instanceName, config_] <- sd(newdata$gd);
		sd_sp[instanceName, config_] <- sd(newdata$sp);
	}
}

mean_ic;
mean_hv;
mean_gd;
mean_sp;

sd_ic;
sd_hv;
sd_gd;
sd_sp;