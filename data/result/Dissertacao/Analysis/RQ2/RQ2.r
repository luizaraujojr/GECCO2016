###############
########## QP 2 
##### O Tamanho de Efeito (ES) do teste de Vargha-Delaney Â12 considerando o algoritmo NSGA-II com cada uma das práticas identificadas para todas as instâncias e indicadores de qualidade
##### Indicadores de qualidade por instância e por configuração   
###############

# limpa todas as variaveis
rm(list = ls());

# Calculating the Effect Size of VarNSGA & Delaney (A12)
vargha.delaney <- function(r1, r2) {
	m <- length(r1);
	n <- length(r2);
	return ((sum(rank(c(r1, r2))[seq_along(r1)]) / m - (m + 1) / 2) / n);
}

# Load data - micro do Marcio
# data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);
#data <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/RQ2/data - error1.txt", header=TRUE);

# Load data - micro do Luiz
#data <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/Analysis/RQ2/data_multiexperiment.txt", header=TRUE);
data <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/Analysis/RQ2/data_experiment.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(as.character(data$inst));
#instances <- c("I0", "I5", "I4", "I3", "I1", "I2");
#instances <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");

# Inference tests on an instance basis Contributions
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
#	instance_ <- instances[which(instances==instance_)];

	NSGA <- subset(instanceData_, config == "nsga150k2x");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(NSGA$best, mu=mar$best[1])$p.value;
	print(paste("IC: p-Value for Wilcox:", instance_, "(NSGA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$best, mu=sh$best[1])$p.value;
	print(paste("IC: p-Value for Wilcox:", instance_, "(NSGA,SH)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$best, mu=cpm$best[1])$p.value;
	print(paste("IC: p-Value for Wilcox:", instance_, "(NSGA,CPM)=", pv, sep=" "));
	
	print("");
}

# Inference tests on an instance basis HV
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);

	NSGA <- subset(instanceData_, config == "nsga150k2x");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(NSGA$hv, mu=mar$hv[1])$p.value;
	print(paste("HV: p-Value for Wilcox:", instance_, "(NSGA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$hv, mu=sh$hv[1])$p.value;
	print(paste("HV: p-Value for Wilcox:", instance_, "(NSGA,SH)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$hv, mu=cpm$hv[1])$p.value;
	print(paste("HV: p-Value for Wilcox:", instance_, "(NSGA,CPM)=", pv, sep=" "));
	
	print("");
}

# Inference tests on an instance basis GD
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	
	NSGA <- subset(instanceData_, config == "nsga150k2x");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(NSGA$gd, mu=mar$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$gd, mu=sh$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,SH)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$gd, mu=cpm$gd[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,CPM)=", pv, sep=" "));
	
	print("");
}

# Inference tests on an instance basis with Spread
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	
	NSGA <- subset(instanceData_, config == "nsga150k2x");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(NSGA$spr, mu=mar$spr[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$spr, mu=sh$spr[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,SH)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$spr, mu=cpm$spr[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,CPM)=", pv, sep=" "));
	
	print("");
}

# Inference tests on an instance basis with Time
for (instance_ in instances)
{
	instanceData_ <- subset(data, inst == instance_);
	
	NSGA <- subset(instanceData_, config == "nsga150k2x");
	mar <- subset(instanceData_, config == "MAR");
	sh <- subset(instanceData_, config == "SH");
	cpm <- subset(instanceData_, config == "CPM");
	
	pv <- wilcox.test(NSGA$time, mu=mar$time[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,MAR)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$time, mu=sh$time[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,SH)=", pv, sep=" "));

	pv <- wilcox.test(NSGA$time, mu=cpm$time[1])$p.value;
	print(paste("GD: p-Value for Wilcox:", instance_, "(NSGA,CPM)=", pv, sep=" "));
	
	print("");
}

# Effect Size tests 
statNames <- c("NSGA/MAR", "NSGA/SH", "NSGA/CPM");
gd_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instances, statNames));
hv_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instances, statNames));
ic_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instances, statNames));
sp_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instances, statNames));
tm_stats <- matrix(nrow=length(instances), ncol=length(statNames), dimnames=list(instances, statNames));

for (instance_ in instances)
{
	instance_ <- instances[which(instances==instance_)];
	
 	margarine <- subset(data, inst == instance_ & config == "MAR");
	CPM <- subset(data, inst == instance_ & config == "CPM");
	SecondHalf <- subset(data, inst == instance_ & config == "SH");
	nsNSGA <- subset(data, inst == instance_ & config == "nsga150k2x");
	
	ic_stats[instance_, "NSGA/MAR"] <- vargha.delaney(nsNSGA$best, margarine$best);
	ic_stats[instance_, "NSGA/SH"] <- vargha.delaney(nsNSGA$best, SecondHalf$best);
	ic_stats[instance_, "NSGA/CPM"] <- vargha.delaney(nsNSGA$best, CPM$best);

	hv_stats[instance_, "NSGA/MAR"] <- vargha.delaney(nsNSGA$hv, margarine$hv);
	hv_stats[instance_, "NSGA/SH"] <- vargha.delaney(nsNSGA$hv, SecondHalf$hv);
	hv_stats[instance_, "NSGA/CPM"] <- vargha.delaney(nsNSGA$hv, CPM$hv);

	gd_stats[instance_, "NSGA/MAR"] <- 1 - vargha.delaney(nsNSGA$gd, margarine$gd);
	gd_stats[instance_, "NSGA/SH"] <- 1 - vargha.delaney(nsNSGA$gd, SecondHalf$gd);
	gd_stats[instance_, "NSGA/CPM"] <- 1 - vargha.delaney(nsNSGA$gd, CPM$gd);
	
	sp_stats[instance_, "NSGA/MAR"] <- 1 - vargha.delaney(nsNSGA$spr, margarine$spr);
	sp_stats[instance_, "NSGA/SH"] <- 1 - vargha.delaney(nsNSGA$spr, SecondHalf$spr);
	sp_stats[instance_, "NSGA/CPM"] <- 1 - vargha.delaney(nsNSGA$spr, CPM$spr);
	
	tm_stats[instance_, "NSGA/MAR"] <- 1 - vargha.delaney(nsNSGA$time, margarine$spr);
	tm_stats[instance_, "NSGA/SH"] <- 1 - vargha.delaney(nsNSGA$time, SecondHalf$spr);
	tm_stats[instance_, "NSGA/CPM"] <- 1 - vargha.delaney(nsNSGA$time, CPM$spr);
}

ic_stats;
hv_stats;
gd_stats;
sp_stats;
tm_stats;


# Calculating means
mean_ic <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_hv <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_gd <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_sp <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));
mean_tm <- matrix(nrow=length(instances), ncol=length(configs), dimnames=list(instances, configs));

for (config_ in configs)
{
	for (instance_ in instances)
	{
		instance_ <- instances[which(instances==instance_)];
		newdata <- subset(data, inst == instance_ & config == config_);
		mean_ic[instance_, config_] <- mean(newdata$best);
		mean_hv[instance_, config_] <- mean(newdata$hv);
		mean_gd[instance_, config_] <- mean(newdata$gd);
		mean_sp[instance_, config_] <- mean(newdata$spr);
		mean_tm[instance_, config_] <- mean(newdata$time);
	}
}

mean_ic;
mean_hv;
mean_gd;
mean_sp;
mean_tm;

# Calculating standard deviation
sd_ic <- matrix(nrow=length(instances), ncol=1, dimnames=list(instances, c("nsga150k2x")));
sd_hv <- matrix(nrow=length(instances), ncol=1, dimnames=list(instances, c("nsga150k2x")));
sd_gd <- matrix(nrow=length(instances), ncol=1, dimnames=list(instances, c("nsga150k2x")));
sd_sp <- matrix(nrow=length(instances), ncol=1, dimnames=list(instances, c("nsga150k2x")));
sd_tm <- matrix(nrow=length(instances), ncol=1, dimnames=list(instances, c("nsga150k2x")));

for (instance_ in instances)
{
	instance_ <- instances[which(instances==instance_)];
	newdata <- subset(data, inst == instance_ & config == "nsga150k2x");
	sd_ic[instance_, "nsga150k2x"] <- sd(newdata$best);
	sd_hv[instance_, "nsga150k2x"] <- sd(newdata$hv);
	sd_gd[instance_, "nsga150k2x"] <- sd(newdata$gd);
	sd_sp[instance_, "nsga150k2x"] <- sd(newdata$spr);
	sd_tm[instance_, "nsga150k2x"] <- sd(newdata$time);
}

sd_ic;
sd_hv;
sd_gd;
sd_sp;
sd_tm;
