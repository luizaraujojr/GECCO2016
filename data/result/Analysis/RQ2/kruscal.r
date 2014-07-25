# Load data - micro do Marcio
#data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Load data - micro do Luiz
 data <- read.table(file="C:/workspace/Hector/data/result/Analysis/rq2/data.txt", header=TRUE);

# Separate sequence configuration and instances
configs <- unique(as.character(data$config));
instances <- unique(data$inst);

# Inference tests on an instance basis
for (instance_ in unique_instances)
{
	instanceData_ <- subset(data, inst == instance_);

	pv <- kruskal.test(gd~config, data=instanceData_)$p.value;
	print(paste("p-Value for", instance_, "=", pv, sep=" "));

	wt <- pairwise.wilcox.test(instanceData_$gd, instanceData_$config, p.adj="bonferroni", exact=F)$p.value;
	
	rownames <- names(wt[,1]);
	colnames <- names(wt[1,]);
	
	for (i in 1:3) 
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
