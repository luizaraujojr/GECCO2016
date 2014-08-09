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
 data <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/Analysis/rq2/data - error1.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- c("I0", "I5", "I4", "I3", "I1", "I2");
instanceNames <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");

# Inference tests on an instance basis
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	ga <- subset(instanceData_, config == "GA");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(ga$best, mu=mar$best[1])$p.value;
	print(paste("IC: p-Value for Wilcox:", instanceName, "(GA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(ga$best, mu=sh$best[1])$p.value;
	print(paste("IC: p-Value for Wilcox:", instanceName, "(GA,SH)=", pv, sep=" "));

	pv <- wilcox.test(ga$best, mu=cpm$best[1])$p.value;
	print(paste("IC: p-Value for Wilcox:", instanceName, "(GA,CPM)=", pv, sep=" "));
	
	print("");
}

# Inference tests on an instance basis
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	ga <- subset(instanceData_, config == "GA");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(ga$hv, mu=mar$hv[1])$p.value;
	print(paste("HV: p-Value for Wilcox:", instanceName, "(GA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(ga$hv, mu=sh$hv[1])$p.value;
	print(paste("HV: p-Value for Wilcox:", instanceName, "(GA,SH)=", pv, sep=" "));

	pv <- wilcox.test(ga$hv, mu=cpm$hv[1])$p.value;
	print(paste("HV: p-Value for Wilcox:", instanceName, "(GA,CPM)=", pv, sep=" "));
	
	print("");
}

# Inference tests on an instance basis
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	instanceName <- instanceNames[which(instances==instance_)];

	ga <- subset(instanceData_, config == "GA");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(ga$gd, mu=mar$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instanceName, "(GA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(ga$gd, mu=sh$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instanceName, "(GA,SH)=", pv, sep=" "));

	pv <- wilcox.test(ga$gd, mu=cpm$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instanceName, "(GA,CPM)=", pv, sep=" "));
	
	print("");
}

# Effect Size tests 
statNames <- c("GA/MAR", "GA/SH", "GA/CPM");
gd_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instanceNames, statNames));
hv_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instanceNames, statNames));
ic_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instanceNames, statNames));

for (instance_ in instances)
{
	instanceName <- instanceNames[which(instances==instance_)];
	
 	margarine <- subset(data, inst == instance_ & config == "MAR");
	CPM <- subset(data, inst == instance_ & config == "CPM");
	SecondHalf <- subset(data, inst == instance_ & config == "SH");
	nsga <- subset(data, inst == instance_ & config == "GA");
	
	ic_stats[instanceName, "GA/MAR"] <- vargha.delaney(nsga$best, margarine$best);
	ic_stats[instanceName, "GA/SH"] <- vargha.delaney(nsga$best, SecondHalf$best);
	ic_stats[instanceName, "GA/CPM"] <- vargha.delaney(nsga$best, CPM$best);

	hv_stats[instanceName, "GA/MAR"] <- vargha.delaney(nsga$hv, margarine$hv);
	hv_stats[instanceName, "GA/SH"] <- vargha.delaney(nsga$hv, SecondHalf$hv);
	hv_stats[instanceName, "GA/CPM"] <- vargha.delaney(nsga$hv, CPM$hv);

	gd_stats[instanceName, "GA/MAR"] <- 1 - vargha.delaney(nsga$gd, margarine$gd);
	gd_stats[instanceName, "GA/SH"] <- 1 - vargha.delaney(nsga$gd, SecondHalf$gd);
	gd_stats[instanceName, "GA/CPM"] <- 1 - vargha.delaney(nsga$gd, CPM$gd);
}

gd_stats;
hv_stats;
ic_stats;

# Calculating means
mean_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));
mean_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instanceNames, configs));

for (config_ in configs)
{
	for (instance_ in instances)
	{
		instanceName <- instanceNames[which(instances==instance_)];
		newdata <- subset(data, inst == instance_ & config == config_);
		mean_ic[instanceName, config_] <- mean(newdata$best);
		mean_hv[instanceName, config_] <- mean(newdata$hv);
		mean_gd[instanceName, config_] <- mean(newdata$gd);
	}
}

mean_ic;
mean_hv;
mean_gd;

# Calculating standard deviation
sd_ic <- matrix(nrow=length(instances), ncol=1, dimnames=list(instanceNames, c("GA")));
sd_hv <- matrix(nrow=length(instances), ncol=1, dimnames=list(instanceNames, c("GA")));
sd_gd <- matrix(nrow=length(instances), ncol=1, dimnames=list(instanceNames, c("GA")));

for (instance_ in instances)
{
	instanceName <- instanceNames[which(instances==instance_)];
	newdata <- subset(data, inst == instance_ & config == "GA");
	sd_ic[instanceName, "GA"] <- sd(newdata$best);
	sd_hv[instanceName, "GA"] <- sd(newdata$hv);
	sd_gd[instanceName, "GA"] <- sd(newdata$gd);
}
