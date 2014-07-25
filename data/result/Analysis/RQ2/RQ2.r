##################################################
#####   Calculating the Effect Size          #####
##################################################

# Calculating the Effect Size of Varga & Delaney (A12)
vargha.delaney <- function(r1, r2) {
	m <- length(r1);
	n <- length(r2);
	return ((sum(rank(c(r1, r2))[seq_along(r1)]) / m - (m + 1) / 2) / n);
}

# limpa todas as variaveis
rm(list = ls());

# Load data - micro do Marcio
data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Load data - micro do Luiz
# data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

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

		mean_ic[instances_, config_] <- mean(newdata$gd);
		mean_hv[instances_, config_] <- mean(newdata$hv);
		mean_gd[instances_, config_] <- mean(newdata$gd);
		
		sd_ic[instances_, config_] <- sd(newdata$gd);
		sd_hv[instances_, config_] <- sd(newdata$hv);
		sd_gd[instances_, config_] <- sd(newdata$gd);
	}
}

# Statistics
gd_statNames <- c("gd_mar-cpm", "gd_mar-sh", "gd_mar-nsga", "gd_sh-cpm", "gd_sh-nsga", "gd_sh-mar", "gd_cpm-mar", "gd_cpm-nsga", "gd_cpm-sh","gd_nsga-mar", "gd_nsga-cpm", "gd_nsga-sh");
hv_statNames <- c("hv_mar-cpm", "hv_mar-sh", "hv_mar-nsga", "hv_sh-cpm", "hv_sh-nsga", "hv_sh-mar", "hv_cpm-mar", "hv_cpm-nsga", "hv_cpm-sh","hv_nsga-mar", "hv_nsga-cpm", "hv_nsga-sh");
best_statNames <- c("best_mar-cpm", "best_mar-sh", "best_mar-nsga", "best_sh-cpm", "best_sh-nsga", "best_sh-mar", "best_cpm-mar", "best_cpm-nsga", "best_cpm-sh","best_nsga-mar", "best_nsga-cpm", "best_nsga-sh");

gd_stats <- matrix(nrow=length(instances), ncol=length(gd_statNames), dimnames=list(instances, gd_statNames));
hv_stats <- matrix(nrow=length(instances), ncol=length(hv_statNames), dimnames=list(instances, hv_statNames));
best_stats <- matrix(nrow=length(instances), ncol=length(best_statNames), dimnames=list(instances, best_statNames));

# Inference tests on an instance basis
for (instance_ in instances)
{
 
	margarine <- subset(data, inst == instance_ & config == "MAR");
	CPM <- subset(data, inst == instance_ & config == "CPM");
	SecondHalf <- subset(data, inst == instance_ & config == "SH");
	nsga <- subset(data, inst == instance_ & config == "GA");

	gd_stats[instance_, "gd_mar-cpm"] <- vargha.delaney(margarine$gd, CPM$gd);
	gd_stats[instance_, "gd_mar-sh"] <- vargha.delaney(margarine$gd, SecondHalf$gd);
	gd_stats[instance_, "gd_mar-nsga"] <- vargha.delaney(margarine$gd, nsga$gd);
	
	gd_stats[instance_, "gd_sh-cpm"] <- vargha.delaney(SecondHalf$gd, CPM$gd);
	gd_stats[instance_, "gd_sh-nsga"] <- vargha.delaney(SecondHalf$gd, nsga$gd);
	gd_stats[instance_, "gd_sh-mar"] <- vargha.delaney(SecondHalf$gd, margarine$gd);
	
	gd_stats[instance_, "gd_cpm-mar"] <- vargha.delaney(CPM$gd, margarine$gd);
	gd_stats[instance_, "gd_cpm-nsga"] <- vargha.delaney(CPM$gd, nsga$gd);
	gd_stats[instance_, "gd_cpm-sh"] <- vargha.delaney(CPM$gd, SecondHalf$gd);
	
	gd_stats[instance_, "gd_nsga-mar"] <- vargha.delaney(nsga$gd, margarine$gd);
	gd_stats[instance_, "gd_nsga-cpm"] <- vargha.delaney(nsga$gd, CPM$gd);
	gd_stats[instance_, "gd_nsga-sh"] <- vargha.delaney(nsga$gd, SecondHalf$gd);
		
	
	hv_stats[instance_, "hv_mar-cpm"] <- vargha.delaney(margarine$hv, CPM$hv);
	hv_stats[instance_, "hv_mar-sh"] <- vargha.delaney(margarine$hv, SecondHalf$hv);
	hv_stats[instance_, "hv_mar-nsga"] <- vargha.delaney(margarine$hv, nsga$hv);
	
	hv_stats[instance_, "hv_sh-cpm"] <- vargha.delaney(SecondHalf$hv, CPM$hv);
	hv_stats[instance_, "hv_sh-nsga"] <- vargha.delaney(SecondHalf$hv, nsga$hv);
	hv_stats[instance_, "hv_sh-mar"] <- vargha.delaney(SecondHalf$hv, margarine$hv);
	
	hv_stats[instance_, "hv_cpm-mar"] <- vargha.delaney(CPM$hv, margarine$hv);
	hv_stats[instance_, "hv_cpm-nsga"] <- vargha.delaney(CPM$hv, nsga$hv);
	hv_stats[instance_, "hv_cpm-sh"] <- vargha.delaney(CPM$hv, SecondHalf$hv);
	
	hv_stats[instance_, "hv_nsga-mar"] <- vargha.delaney(nsga$hv, margarine$hv);
	hv_stats[instance_, "hv_nsga-cpm"] <- vargha.delaney(nsga$hv, CPM$hv);
	hv_stats[instance_, "hv_nsga-sh"] <- vargha.delaney(nsga$hv, SecondHalf$hv);
	
	
	best_stats[instance_, "best_mar-cpm"] <- vargha.delaney(margarine$best, CPM$best);
	best_stats[instance_, "best_mar-sh"] <- vargha.delaney(margarine$best, SecondHalf$best);
	best_stats[instance_, "best_mar-nsga"] <- vargha.delaney(margarine$best, nsga$best);
	
	best_stats[instance_, "best_sh-cpm"] <- vargha.delaney(SecondHalf$best, CPM$best);
	best_stats[instance_, "best_sh-nsga"] <- vargha.delaney(SecondHalf$best, nsga$best);
	best_stats[instance_, "best_sh-mar"] <- vargha.delaney(SecondHalf$best, margarine$best);
	
	best_stats[instance_, "best_cpm-mar"] <- vargha.delaney(CPM$best, margarine$best);
	best_stats[instance_, "best_cpm-nsga"] <- vargha.delaney(CPM$best, nsga$best);
	best_stats[instance_, "best_cpm-sh"] <- vargha.delaney(CPM$best, SecondHalf$best);
	
	best_stats[instance_, "best_nsga-mar"] <- vargha.delaney(nsga$best, margarine$best);
	best_stats[instance_, "best_nsga-cpm"] <- vargha.delaney(nsga$best, CPM$best);
	best_stats[instance_, "best_nsga-sh"] <- vargha.delaney(nsga$best, SecondHalf$best);
}

gd_stats;
hv_stats;
best_stats;


# Inference tests on an instance basis
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(gd~config, data=instanceData_)$p.value;
	print(paste("p-Value for KW:", instance_, "=", pv, sep=" "));

	ga <- subset(instanceData_, config == "GA");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(ga$gd, mu=mar$gd[1])$p.value;
	print(paste("p-Value for Wilcox:", instance_, "(GA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(ga$gd, mu=sh$gd[1])$p.value;
	print(paste("p-Value for Wilcox:", instance_, "(GA,SH)=", pv, sep=" "));

	pv <- wilcox.test(ga$gd, mu=cpm$gd[1])$p.value;
	print(paste("p-Value for Wilcox:", instance_, "(GA,CPM)=", pv, sep=" "));
	
	print("");
}
