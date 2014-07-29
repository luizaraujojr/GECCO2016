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
data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);

# Load data - micro do Luiz
# data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);

# Inference tests on an instance basis (GD)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	pv <- wilcox.test(NSGA$best, NSGANE$best)$p.value;
	print(paste("IC: p-Value for Wilcox:", instance_, "(NSGA,NSGANE)=", pv, sep=" "));
}

# Inference tests on an instance basis (GD)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	pv <- wilcox.test(NSGA$hv, NSGANE$hv)$p.value;
	print(paste("HV: p-Value for Wilcox:", instance_, "(NSGA,NSGANE)=", pv, sep=" "));
}

# Inference tests on an instance basis (GD)
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	pv <- wilcox.test(NSGA$gd, mu=NSGANE$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,NSGANE)=", pv, sep=" "));
}

# Effect Size tests 
effectSizeNames <- c("ic", "hv", "gd");
effectSize <- matrix(nrow=length(instances), ncol=length(effectSizeNames), dimnames=list(instances, effectSizeNames));

for (instance_ in instances)
{
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	effectSize[instance_, "ic"] <- vargha.delaney(NSGA$best, NSGANE$best);
	effectSize[instance_, "hv"] <- vargha.delaney(NSGA$hv, NSGANE$hv);
	effectSize[instance_, "gd"] <- 1.0 - vargha.delaney(NSGA$gd, NSGANE$gd);
}

effectSize;

# Calculating means and standard deviation
## Create matrices to hold mean and stdev values
mean_count <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
sd_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
sd_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
sd_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));

for (config_ in configs)
{
	for (instances_ in instances)
	{
		newdata <- subset(data, inst == instances_ & config == config_);

		mean_ic[instances_, config_] <- mean(newdata$best);
		mean_hv[instances_, config_] <- mean(newdata$hv);
		mean_gd[instances_, config_] <- mean(newdata$gd);
		mean_count[instances_, config_] <- mean(newdata$count);
		
		sd_ic[instances_, config_] <- sd(newdata$best);
		sd_hv[instances_, config_] <- sd(newdata$hv);
		sd_gd[instances_, config_] <- sd(newdata$gd);
	}
}

mean_ic;
mean_hv;
mean_gd;


