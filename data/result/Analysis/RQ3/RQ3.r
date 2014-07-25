##################################################
#####   RQ3          #####
##################################################

# Calculating the Effect Size of Varga & Delaney (A12)
vargha.delaney <- function(r1, r2) {
	m <- length(r1);
	n <- length(r2);
	return ((sum(rank(c(r1, r2))[seq_along(r1)]) / m - (m + 1) / 2) / n);
}

# Load data - micro do Marcio
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq3/data.txt", header=TRUE);

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
gd_statNames <- c("gd_GAsE-GAcE", "gd_GAcE-GAsE");
hv_statNames <- c("hv_GAsE-GAcE", "hv_GAcE-GAsE");
best_statNames <- c("best_GAsE-GAcE", "best_GAcE-GAsE");

gd_stats <- matrix(nrow=length(instances), ncol=length(gd_statNames), dimnames=list(instances, gd_statNames));
hv_stats <- matrix(nrow=length(instances), ncol=length(hv_statNames), dimnames=list(instances, hv_statNames));
best_stats <- matrix(nrow=length(instances), ncol=length(best_statNames), dimnames=list(instances, best_statNames));

# Inference tests on an instance basis
for (instance_ in instances)
{
	GAsE <- subset(data, inst == instance_ & config == "GAsE");
	GAcE <- subset(data, inst == instance_ & config == "GAcE");

	gd_stats[instance_, "gd_GAsE-GAcE"] <- vargha.delaney(GAsE$gd, GAcE$gd);
	gd_stats[instance_, "gd_GAcE-GAsE"] <- vargha.delaney(GAcE$gd, GAsE$gd);
	
	hv_stats[instance_, "hv_GAsE-GAcE"] <- vargha.delaney(GAsE$hv, GAcE$hv);
	hv_stats[instance_, "hv_GAcE-GAsE"] <- vargha.delaney(GAcE$hv, GAsE$hv);
	
	best_stats[instance_, "best_GAsE-GAcE"] <- vargha.delaney(GAsE$best, GAcE$best);
	best_stats[instance_, "best_GAcE-GAsE"] <- vargha.delaney(GAcE$best, GAsE$best);
}

gd_stats;
hv_stats;
best_stats;

# Inference tests on an instance basis
for (instance_ in unique_instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(gd~config, data=instanceData_)$p.value;
	print(paste("p-Value for", instance_, "=", pv, sep=" "));

	wt <- pairwise.wilcox.test(instanceData_$gd, instanceData_$config, p.adj="bonferroni", exact=F)$p.value;
	
	rownames <- names(wt[,1]);
	colnames <- names(wt[1,]);
	
	for (i in 1:1) 
	{
		for (j in 1:i)
		{
			if (wt[i,j] < 0.05)
			{
				print(paste("There exist differences between", rownames[i], "and", colnames[j], sep=" "));
			}
		}
	}
	
	print("");
}

# Best configuration for each instance
names <- names(mean_gd[1,]);

for (instance_ in unique_instances)
{
	instanceIndex <- match(instance_, unique_instances);
	instanceGD <- mean_gd[instanceIndex,];
	best <- match(min(instanceGD), instanceGD);
	print(paste("The best configuration for instance", instance_, "is", names[best], sep=" "));
}
