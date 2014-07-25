##################################################
#####   Comparing to Random Search           #####
##################################################

# Calculating the Effect Size of Varga & Delaney (A12)
vargha.delaney <- function(r1, r2) {
	m <- length(r1);
	n <- length(r2);
	return ((sum(rank(c(r1, r2))[seq_along(r1)]) / m - (m + 1) / 2) / n);
}

# Load data - micro do Marcio
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/random/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Analysis/random/data.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);

# Create matrices to hold mean and stdev values
mean_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
sd_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
sd_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
sd_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));

# Fill out matrices with means and standard deviation
for (config_ in configs)
{
	for (instances_ in instances)
	{
		newdata <- subset(data, inst == instances_ & config == config_);

		mean_ic[instances_, config_] <- mean(newdata$best);
		mean_hv[instances_, config_] <- mean(newdata$hv);
		mean_gd[instances_, config_] <- mean(newdata$gd);
		
		sd_ic[instances_, config_] <- sd(newdata$best);
		sd_hv[instances_, config_] <- sd(newdata$hv);
		sd_gd[instances_, config_] <- sd(newdata$gd);
	}
}

# Statistics
statNames <- c("ic_pv_rs-ga", "ic_es_ga-rs", "gd_pv_rs-ga", "gd_es_ga-rs", "hv_pv_rs-ga", "hv_es_ga-rs");
stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instances, statNames));

# Inference tests on an instance basis
for (instance_ in instances)
{
	rs <- subset(data, inst == instance_ & config == "rs50k");
	ga <- subset(data, inst == instance_ & config == "nsga50k2x");

	stats[instance_, "ic_pv_rs-ga"] <- wilcox.test(rs$best, ga$best)$p.value;
	stats[instance_, "ic_es_ga-rs"] <- vargha.delaney(ga$best, rs$best);
	stats[instance_, "gd_pv_rs-ga"] <- wilcox.test(rs$gd, ga$gd)$p.value;
	stats[instance_, "gd_es_ga-rs"] <- vargha.delaney(ga$gd, rs$gd);
	stats[instance_, "hv_pv_rs-ga"] <- wilcox.test(rs$hv, ga$hv)$p.value;
	stats[instance_, "hv_es_ga-rs"] <- vargha.delaney(ga$hv, rs$hv);
}

stats;
