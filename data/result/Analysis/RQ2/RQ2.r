##################################################
#####            Research Request 2          #####
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
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);

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

# Effect Size tests 

gd_statNames <- c("gd-cpm-nsga", "gd-sh-nsga", "gd-mar-nsga");
hv_statNames <- c("hv-cpm-nsga", "hv-sh-nsga", "hv-mar-nsga");
ic_statNames <- c("ic-cpm-nsga", "ic-sh-nsga", "ic-mar-nsga");

gd_stats <- matrix(nrow=length(instances), ncol=length(gd_statNames), dimnames=list(instances, gd_statNames));
hv_stats <- matrix(nrow=length(instances), ncol=length(hv_statNames), dimnames=list(instances, hv_statNames));
ic_stats <- matrix(nrow=length(instances), ncol=length(ic_statNames), dimnames=list(instances, ic_statNames));

for (instance_ in instances)
{
 
	margarine <- subset(data, inst == instance_ & config == "MAR");
	CPM <- subset(data, inst == instance_ & config == "CPM");
	SecondHalf <- subset(data, inst == instance_ & config == "SH");
	nsga <- subset(data, inst == instance_ & config == "GA");

	gd_stats[instance_, "gd-cpm-nsga"] <- vargha.delaney(CPM$gd, nsga$gd);
	gd_stats[instance_, "gd-sh-nsga"] <- vargha.delaney(SecondHalf$gd, nsga$gd);
	gd_stats[instance_, "gd-mar-nsga"] <- vargha.delaney(margarine$gd, nsga$gd);
	
	hv_stats[instance_, "hv-cpm-nsga"] <- vargha.delaney(CPM$hv, nsga$hv);
	hv_stats[instance_, "hv-sh-nsga"] <- vargha.delaney(SecondHalf$hv, nsga$hv);
	hv_stats[instance_, "hv-mar-nsga"] <- vargha.delaney(margarine$hv, nsga$hv);
	
	ic_stats[instance_, "ic-cpm-nsga"] <- vargha.delaney(CPM$best, nsga$best);
	ic_stats[instance_, "ic-sh-nsga"] <- vargha.delaney(SecondHalf$best, nsga$best);
	ic_stats[instance_, "ic-mar-nsga"] <- vargha.delaney(margarine$best, nsga$best);
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


