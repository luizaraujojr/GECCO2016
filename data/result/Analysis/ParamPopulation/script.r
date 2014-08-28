##################################################
#####   Defining the Population Multiplier   #####
##################################################

## change the file replacing the character # for the character i
## remove the repetitive title of result analysis

# Load data - micro do Marcio
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Parameter Setting/populationSize/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Parameter Setting/populationSize/data.txt", header=TRUE);

# Separate sequence configuration and instances
unique_configurations <- unique(as.character(data$config));
unique_instances <- unique(data$inst);

# Create matrices to hold mean and stdev values
mean_solution <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
mean_hv <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
mean_gd <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
sd_solution <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
sd_hv <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
sd_gd <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));

# Fill out matrices with means and standard deviation
for (config_ in unique_configurations)
{
	for (instances_ in unique_instances)
	{
		newdata <- subset(data, inst == instances_ & config == config_);

		mean_solution [instances_, config_] <- mean(newdata$best);
		mean_hv [instances_, config_] <- mean(newdata$hv);
		mean_gd [instances_, config_] <- mean(newdata$gd);
		
		sd_solution [instances_, config_] <- sd(newdata$best);
		sd_hv [instances_, config_] <- sd(newdata$hv);
		sd_gd [instances_, config_] <- sd(newdata$gd);
	}
}

# Inference tests on an instance basis
for (instance_ in unique_instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(gd~config, data=instanceData_)$p.value;
	print(paste("p-Value for", instance_, "=", pv, sep=" "));

	wt <- pairwise.wilcox.test(instanceData_$gd, instanceData_$config, p.adj="bonferroni", exact=F)$p.value;
	
	rownames <- names(wt[,1]);
	colnames <- names(wt[1,]);
	
	for (i in 1:2) 
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
