##################################################
#####   Defining the Population Multiplier   #####
##################################################

## change the file replacing the character # for the character i
## remove the repetitive title of result analysis

# limpa todas as variaveis
rm(list = ls());

# Load data - micro do Marcio
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Parameter Setting/populationSize/data.txt", header=TRUE);
#data <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/ParamPopulation/data.txt", header=TRUE);

# Load data - micro do Luiz
data <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/Dissertacao/Analysis/ParamPopulation/data1.txt", header=TRUE);

# Separate sequence configuration and instances
unique_configurations <- unique(as.character(data$config));
unique_instances <- unique(data$inst);

# Create matrices to hold mean and stdev values
mean_solution <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
mean_hv <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
mean_gd <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
mean_spr <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#mean_time <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#mean_invgd <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#mean_gspr <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#mean_eps <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));

sd_solution <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
sd_hv <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
sd_gd <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
sd_spr <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#sd_time <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#sd_invgd <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#sd_gspr <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));
#sd_eps <- matrix(nrow=length(unique_instances), ncol=length(unique_configurations), dimnames=list(unique_instances, unique_configurations));

# Fill out matrices with means and standard deviation
for (config_ in unique_configurations)
{
	for (instances_ in unique_instances)
	{
		newdata <- subset(data, inst == instances_ & config == config_);

		mean_solution [instances_, config_] <- mean(newdata$best);
		mean_hv [instances_, config_] <- mean(newdata$hv);
		mean_gd [instances_, config_] <- mean(newdata$gd);
		mean_spr [instances_, config_] <- mean(newdata$spr);
		#mean_time [instances_, config_] <- mean(newdata$time);
		#mean_invgd [instances_, config_] <- mean(newdata$invgd);
		#mean_gspr [instances_, config_] <- mean(newdata$gspr);
		#mean_eps [instances_, config_] <- mean(newdata$eps);
		
		sd_solution [instances_, config_] <- sd(newdata$best);
		sd_hv [instances_, config_] <- sd(newdata$hv);
		sd_gd [instances_, config_] <- sd(newdata$gd);
		sd_spr [instances_, config_] <- sd(newdata$spr);
		#sd_time [instances_, config_] <- sd(newdata$time);
		#sd_invgd [instances_, config_] <- sd(newdata$invgd);
		#sd_gspr [instances_, config_] <- sd(newdata$gspr);
		#sd_eps [instances_, config_] <- sd(newdata$eps);
	}
}

# Inference tests on an instance basis with gd
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
	print(wt);
	print("");
	print("------------------------------------------------------------------------------------------------");
}

# Inference tests on an instance basis with HV
for (instance_ in unique_instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(hv~config, data=instanceData_)$p.value;
	print(paste("p-Value for", instance_, "=", pv, sep=" "));

	wt <- pairwise.wilcox.test(instanceData_$hv, instanceData_$config, p.adj="bonferroni", exact=F)$p.value;
	
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
	print(wt);
	print("");
	print("------------------------------------------------------------------------------------------------");
}


# Inference tests on an instance basis with spr
for (instance_ in unique_instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(spr~config, data=instanceData_)$p.value;
	print(paste("p-Value for", instance_, "=", pv, sep=" "));

	wt <- pairwise.wilcox.test(instanceData_$spr, instanceData_$config, p.adj="bonferroni", exact=F)$p.value;
	
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
	print(wt);
	print("");
	print("------------------------------------------------------------------------------------------------");
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

colname <- c( "nsga2x_m", "nsga2x_sd",   "nsga4x_m", "nsga4x_sd", "nsga8x_m", "nsga8x_sd");

gd <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
gd [1:6,1:6] <- cbind(mean_gd[,1],sd_gd[,1] ,mean_gd[,2],sd_gd[,2],mean_gd[,3],sd_gd[,3]);
gd;

hv <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
hv [1:6,1:6] <- cbind(mean_hv[,1],sd_hv[,1] ,mean_hv[,2],sd_hv[,2],mean_hv[,3],sd_hv[,3]);
hv;

spr <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
spr [1:6,1:6] <- cbind(mean_spr[,1],sd_spr[,1] ,mean_spr[,2],sd_spr[,2],mean_spr[,3],sd_spr[,3]);
spr;

solution <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
solution [1:6,1:6] <- cbind(mean_solution[,1],sd_solution[,1] ,mean_solution[,2],sd_solution[,2],mean_solution[,3],sd_solution[,3]);
solution;

#time <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
#time [1:6,1:6] <- cbind(mean_time[,1],sd_time[,1] ,mean_time[,2],sd_time[,2],mean_time[,3],sd_time[,3]);
#time;

#gspr <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
#gspr [1:6,1:6] <- cbind(mean_gspr[,1],sd_gspr[,1] ,mean_gspr[,2],sd_gspr[,2],mean_gspr[,3],sd_gspr[,3]);
#gspr;

#eps <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
#eps [1:6,1:6] <- cbind(mean_eps[,1],sd_eps[,1] ,mean_eps[,2],sd_eps[,2],mean_eps[,3],sd_eps[,3]);
#eps;

#invgd <- matrix(nrow=6, ncol=6, dimnames=list(unique_instances, colname));
#invgd [1:6,1:6] <- cbind(mean_invgd[,1],sd_invgd[,1] ,mean_invgd[,2],sd_invgd[,2],mean_invgd[,3],sd_invgd[,3]);
#invgd;