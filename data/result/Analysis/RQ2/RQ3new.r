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
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);

# Inference tests on an instance basis
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(gd~config, data=instanceData_)$p.value;
	print(paste("p-Value for KW:", instance_, "=", pv, sep=" "));
	
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	
	pv <- wilcox.test(NSGA$gd, mu=NSGANE$gd[1])$p.value;
	print(paste("p-Value for Wilcox:", instance_, "(NSGA,NSGANE)=", pv, sep=" "));
	
	print("");
}

# Effect Size tests 

gd_statNames <- c("gd-NSGANE-NSGA");
hv_statNames <- c("hv-NSGANE-NSGA");
ic_statNames <- c("ic-NSGANE-NSGA");

gd_stats <- matrix(nrow=length(instances), ncol=length(gd_statNames), dimnames=list(instances, gd_statNames));
hv_stats <- matrix(nrow=length(instances), ncol=length(hv_statNames), dimnames=list(instances, hv_statNames));
ic_stats <- matrix(nrow=length(instances), ncol=length(ic_statNames), dimnames=list(instances, ic_statNames));

for (instance_ in instances)
{
	NSGA <- subset(data, inst == instance_ & config == "NSGA");
	NSGANE <- subset(data, inst == instance_ & config == "NSGANE");

	gd_stats[instance_, "gd-NSGANE-NSGA"] <- vargha.delaney(NSGANE$gd, NSGA$gd);
	
	hv_stats[instance_, "hv-NSGANE-NSGA"] <- vargha.delaney(NSGANE$hv, NSGA$hv);
	
	ic_stats[instance_, "ic-NSGANE-NSGA"] <- vargha.delaney(NSGANE$best, NSGA$best);
}

gd_stats;
hv_stats;
ic_stats;

# Calculating means and standard deviation
## Create matrices to hold mean and stdev values
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

		mean_ic[instances_, config_] <- mean(newdata$gd);
		mean_hv[instances_, config_] <- mean(newdata$hv);
		mean_gd[instances_, config_] <- mean(newdata$gd);
		
		sd_ic[instances_, config_] <- sd(newdata$gd);
		sd_hv[instances_, config_] <- sd(newdata$hv);
		sd_gd[instances_, config_] <- sd(newdata$gd);
	}
}

mean_ic;
mean_hv;
mean_gd;


