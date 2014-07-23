##################################################
#####   Defining the Population Multiplier   #####
##################################################

## change the file replacing the character # for the character i
## remove the repetitive title of result analysis

# Load data - micro do Marcio
data <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/populationSize/data.txt", header=TRUE);

# Load data - micro do Luiz
# data <- read.table(file="C:/workspace/Hector/data/result/Analysis/populationSize/data.txt", header=TRUE);

# Separate sequence configuration and instances
unique_configurations <- unique(as.character(data$config));
unique_instances <- unique(data$inst);

# Calculating the Effect Size of Varga & Delaney (A12)
varga.delaney <- function(r1, r2) {
	m <- length(r1);
	n <- length(r2);
	return ((sum(rank(c(r1, r2))[seq_along(r1)]) / m - (m + 1) / 2) / n);
}

# Calculate statistics
HVStatisticAnalysis <- matrix(nrow=length(unique_instances), ncol=15, dimnames=list(unique_instances, c("HV_es_2x-4x", "HV_es_4x-2x", "HV_es_4x-8x", "HV_es_8x-4x", "HV_es_2x-8x", "HV_es_8x-2x", "HV_es_2x-ref", "HV_es_ref-2x", "HV_es_4x-ref", "HV_es_ref-4x", "HV_es_8x-ref", "HV_es_ref-8x", "HV_pv_4x", "HV_pv_8x", "HV_pv_best")));
GDStatisticAnalysis <- matrix(nrow=length(unique_instances), ncol=16, dimnames=list(unique_instances, c("GD_es_2x-4x", "GD_es_4x-2x", "GD_es_4x-8x", "GD_es_8x-4x", "GD_es_2x-8x", "GD_es_8x-2x", "GD_es_2x-ref", "GD_es_ref-2x", "GD_es_4x-ref", "GD_es_ref-4x", "GD_es_8x-ref", "GD_es_ref-8x", "GD_pv_2x", "GD_pv_4x", "GD_pv_8x", "GD_pv_best")));
BESTStatisticAnalysis <- matrix(nrow=length(unique_instances), ncol=16, dimnames=list(unique_instances, c("BEST_es_2x-4x", "BEST_es_4x-2x", "BEST_es_4x-8x", "BEST_es_8x-4x", "BEST_es_2x-8x", "BEST_es_8x-2x", "BEST_es_2x-ref", "BEST_es_ref-2x", "BEST_es_4x-ref", "BEST_es_ref-4x", "BEST_es_8x-ref", "BEST_es_ref-8x", "BEST_pv_2x", "BEST_pv_4x", "BEST_pv_8x", "BEST_pv_best")));

for (instancia_ in unique_instances)
{	
	nsga50k2x <- subset(data, inst == instancia_ & config == 'nsga50k2x');	
	nsga50k4x <- subset(data, inst == instancia_ & config == 'nsga50k4x');
	nsga50k8x<- subset(data, inst == instancia_ & config == 'nsga50k8x');
	
	nsga50k2x <- subset(data, inst == instancia_ & config == 'nsga50k2x');
	nsga50k4x <- subset(data, inst == instancia_ & config == 'nsga50k4x');
	nsga50k8x<- subset(data, inst == instancia_ & config == 'nsga50k8x');
	
	refHV <- c();
	refGD <- c();
	refBEST <- c();
	
	#for (i in 1: max(length(nsga50k2x), length(nsga50k4x), length(nsga50k8x)))
		refHV <- append(refHV, max(nsga50k2x$hv, nsga50k4x$hv, nsga50k8x$hv));
		refGD <- append(refGD, max(nsga50k2x$gd, nsga50k4x$gd, nsga50k8x$gd));	
		refBEST <- append(refBEST, max(nsga50k2x$best, nsga50k4x$best, nsga50k8x$best));

		HVStatisticAnalysis[instancia_, 'HV_es_2x-4x'] <- varga.delaney (nsga50k2x$hv,nsga50k4x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_4x-2x'] <- varga.delaney (nsga50k4x$hv,nsga50k2x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_4x-8x'] <- varga.delaney (nsga50k4x$hv,nsga50k8x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_8x-4x'] <- varga.delaney (nsga50k8x$hv,nsga50k4x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_8x-2x'] <- varga.delaney (nsga50k8x$hv,nsga50k2x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_2x-8x'] <- varga.delaney (nsga50k2x$hv,nsga50k8x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_ref-2x'] <- varga.delaney (refHV,nsga50k2x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_2x-ref'] <- varga.delaney (nsga50k2x$hv,refHV);
		HVStatisticAnalysis[instancia_, 'HV_es_ref-4x'] <- varga.delaney (refHV,nsga50k4x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_4x-ref'] <- varga.delaney (nsga50k4x$hv,refHV);
		HVStatisticAnalysis[instancia_, 'HV_es_ref-8x'] <- varga.delaney (refHV,nsga50k8x$hv);
		HVStatisticAnalysis[instancia_, 'HV_es_8x-ref'] <- varga.delaney (nsga50k8x$hv,refHV);

		HVStatisticAnalysis[instancia_, 'HV_pv_4x'] <- wilcox.test(nsga50k2x$hv, nsga50k4x$hv )$p.value;
		HVStatisticAnalysis[instancia_, 'HV_pv_8x'] <- wilcox.test(nsga50k2x$hv, nsga50k8x$hv )$p.value;
		HVStatisticAnalysis[instancia_, 'HV_pv_best'] <- wilcox.test(nsga50k2x$hv, refHV)$p.value;

		GDStatisticAnalysis[instancia_, 'GD_es_2x-4x'] <- varga.delaney (nsga50k2x$gd,nsga50k4x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_4x-2x'] <- varga.delaney (nsga50k4x$gd,nsga50k2x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_4x-8x'] <- varga.delaney (nsga50k4x$gd,nsga50k8x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_8x-4x'] <- varga.delaney (nsga50k8x$gd,nsga50k4x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_8x-2x'] <- varga.delaney (nsga50k8x$gd,nsga50k2x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_2x-8x'] <- varga.delaney (nsga50k2x$gd,nsga50k8x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_ref-2x'] <- varga.delaney (refGD,nsga50k2x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_2x-ref'] <- varga.delaney (nsga50k2x$gd,refGD);
		GDStatisticAnalysis[instancia_, 'GD_es_ref-4x'] <- varga.delaney (refGD,nsga50k4x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_4x-ref'] <- varga.delaney (nsga50k4x$gd,refGD);
		GDStatisticAnalysis[instancia_, 'GD_es_ref-8x'] <- varga.delaney (refGD,nsga50k8x$gd);
		GDStatisticAnalysis[instancia_, 'GD_es_8x-ref'] <- varga.delaney (nsga50k8x$gd,refGD);

		GDStatisticAnalysis[instancia_, 'GD_pv_4x'] <- wilcox.test(nsga50k2x$gd, nsga50k4x$gd )$p.value;
		GDStatisticAnalysis[instancia_, 'GD_pv_8x'] <- wilcox.test(nsga50k2x$gd, nsga50k8x$gd )$p.value;
		GDStatisticAnalysis[instancia_, 'GD_pv_best'] <- wilcox.test(nsga50k2x$gd, refGD)$p.value;
		
		BESTStatisticAnalysis[instancia_, 'BEST_es_2x-4x'] <- varga.delaney (nsga50k2x$best,nsga50k4x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_4x-2x'] <- varga.delaney (nsga50k4x$best,nsga50k2x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_4x-8x'] <- varga.delaney (nsga50k4x$best,nsga50k8x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_8x-4x'] <- varga.delaney (nsga50k8x$best,nsga50k4x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_8x-2x'] <- varga.delaney (nsga50k8x$best,nsga50k2x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_2x-8x'] <- varga.delaney (nsga50k2x$best,nsga50k8x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_ref-2x'] <- varga.delaney (refBEST,nsga50k2x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_2x-ref'] <- varga.delaney (nsga50k2x$best,refBEST);
		BESTStatisticAnalysis[instancia_, 'BEST_es_ref-4x'] <- varga.delaney (refBEST,nsga50k4x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_4x-ref'] <- varga.delaney (nsga50k4x$best,refBEST);
		BESTStatisticAnalysis[instancia_, 'BEST_es_ref-8x'] <- varga.delaney (refBEST,nsga50k8x$best);
		BESTStatisticAnalysis[instancia_, 'BEST_es_8x-ref'] <- varga.delaney (nsga50k8x$best,refBEST);

		BESTStatisticAnalysis[instancia_, 'BEST_pv_4x'] <- wilcox.test(nsga50k2x$best, nsga50k4x$best )$p.value;
		BESTStatisticAnalysis[instancia_, 'BEST_pv_8x'] <- wilcox.test(nsga50k2x$best, nsga50k8x$best )$p.value;
		BESTStatisticAnalysis[instancia_, 'BEST_pv_best'] <- wilcox.test(nsga50k2x$best, refBEST)$p.value;			
	#}
}

HVStatisticAnalysis;
GDStatisticAnalysis;
BESTStatisticAnalysis;