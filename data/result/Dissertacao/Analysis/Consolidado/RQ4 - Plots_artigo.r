	# clear all objects in memory
	rm(list = ls());

	# Load data - micro do luiz
	gaOvertime <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_error1_frontier_obj.txt", header=TRUE);
	#gaNoError <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_error1_frontier_obj.txt", header=TRUE);
	gaNoError <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_noerror_frontier_obj.txt", header=TRUE);
	cpm <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/basedata/oms/CPM_error1_frontier_obj.txt", header=TRUE);
	margarine <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/basedata/oms/Margarine_error1_frontier_obj.txt", header=TRUE);
	sh <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/basedata/oms/SecondHalf_error1_frontier_obj.txt", header=TRUE);


	#gaOvertime <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/base/rq4/nsga_error1.txt", header=TRUE);
	#gaOvertime <- read.table(file="/Users/marcio.barros/Documents/Hector/data/result/Analysis/RQ4/nsga_overtime.txt", header=TRUE);
	#gaNoError <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/Analysis/rq4/nsga_noerror.txt", header=TRUE);
	#cpm <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/Analysis/rq4/cpm_error1.txt", header=TRUE);
	#margarine <-read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/Analysis/rq4/margarine_error1.txt", header=TRUE);
	#sh <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/Analysis/rq4/secondhalf_error1.txt", header=TRUE);
	#data <- read.table(file="E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/Analysis/rq4/data.txt", header=TRUE);

	# All instanceNames
	#instanceNames <- c("I0", "I5", "I4", "I3", "I1", "I2");
	#instanceNames <- c("ACAD", "WMET", "WAMS", "PSOA", "OMET", "PARM");

	# Separate two instanceNames
	instanceNames <- c("I0", "I2");
	instanceNames <- c("ACAD", "PARM");

	# Creates and fills the correlation matrices
	# - interesting fact: noh and mks have extremelly high correlation - thus, noh is not necessarily required in the optimization
	# - removing noh would acceleate things and allow to run 100k fit evals (something to test for the MSc Thesis)
	# - correlations are yet stronger the on the overtime scenario!
	corrMatrixOT <- matrix(nrow=length(instanceNames), ncol=3, dimnames=list(instanceNames, c("NOHxMKS", "NOHxCST", "MKSxCST")));
	corrMatrixNE <- matrix(nrow=length(instanceNames), ncol=3, dimnames=list(instanceNames, c("NOHxMKS", "NOHxCST", "MKSxCST")));

	for (instance_ in instanceNames)
	{
		instanceName <- instanceNames[which(instanceNames==instance_)];

		newdataOT <- subset(gaOvertime, inst == instance_);

		corrMatrixOT[instanceName, 1] <- cor(newdataOT$noh, newdataOT$mks, method="spearman");
		corrMatrixOT[instanceName, 2] <- cor(newdataOT$noh, newdataOT$cst, method="spearman");
		corrMatrixOT[instanceName, 3] <- cor(newdataOT$mks, newdataOT$cst, method="spearman");

		newdataNE <- subset(gaNoError, inst == instance_);

		corrMatrixNE[instanceName, 1] <- cor(newdataNE$noh, newdataNE$mks, method="spearman");
		corrMatrixNE[instanceName, 2] <- cor(newdataNE$noh, newdataNE$cst, method="spearman");
		corrMatrixNE[instanceName, 3] <- cor(newdataNE$mks, newdataNE$cst, method="spearman");
	}

	corrMatrixOT;
	corrMatrixNE;

	# Ploting function
	plot.my.chart <- function(xo, yo, xe, ye, xc, yc, xm, ym, xs, ys, xTitle, yTitle, instanceName) {
		xrange <- range(c(xo, xe, xc, xm, xe));
		yrange <- range(c(yo, ye, yc, ym, ye));
		
		plot(xrange, yrange, type="n", xlab=xTitle, ylab=yTitle);
		text(min(xrange) + 0.95 * (max(xrange) - min(xrange)), min(yrange) + 0.95 * (max(yrange) - min(yrange)), instanceName, font=2);

		lines(xo, yo, type="p", col="light gray", cex=1, pch="o"); # NSGA
		lines(xe, ye, type="p", col="dark gray", cex=0.5, pch=0);# NSGANE

		lines(xc, yc, type="p", col="black", pch=3);#cpm
		#text(xc, yc, "C");

		lines(xm, ym, type="p", col="black", pch=4);#mar
		#text(xm, ym, "M");

		lines(xs, ys, type="p", col="black", pch=8);#sh
		#text(xs, ys, "S");
	}

	# Plot 3D faces for all instanceNames
	#pdf("E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/analysis/boxplot geral/faces-3d0.pdf", width=10, height=16)
	pdf("E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/analysis/consolidado/boxplot.pdf", width=10, height=5)
	#"E:/Backup/Documentos/GitHub/Hector/data/result/Dissertacao/analysis/boxplot geral/faces-3d1.pdf"
	#windows(14, 10);
	par(mfrow=c(2, 3))
	par(mar=c(2.7,2.7,1,1),mgp=c(1.4,0.5,0))	
	
	
	
	for (instance_ in instanceNames)
	{
		instanceName <- instanceNames[which(instanceNames==instance_)];

		dataOT <- subset(gaOvertime, inst == instance_);
		dataNE <- subset(gaNoError, inst == instance_);
		dataCPM <- subset(cpm, inst == instance_);
		dataMAR <- subset(margarine, inst == instance_);
		dataSH <- subset(sh, inst == instance_);

		plot.my.chart(dataOT$mks, dataOT$cst / 1000, 
					  dataNE$mks, dataNE$cst / 1000, 
					  dataCPM$mks, dataCPM$cst / 1000, 
					  dataMAR$mks, dataMAR$cst / 1000, 
					  dataSH$mks, dataSH$cst / 1000, 
					  "Duração (days)", "Custo (1000R$)", instanceName);
					  
		plot.my.chart(dataOT$noh * 8, dataOT$mks, 
					  dataNE$noh * 8, dataNE$mks, 
					  dataCPM$noh * 8, dataCPM$mks, 
					  dataMAR$noh * 8, dataMAR$mks, 
					  dataSH$noh * 8, dataSH$mks, 
					  "Hora-extra (horas)", "Duração (dias)", instanceName);
					  
		plot.my.chart(dataOT$noh * 8, dataOT$cst / 1000, 
					  dataNE$noh * 8, dataNE$cst / 1000, 
					  dataCPM$noh * 8, dataCPM$cst / 1000, 
					  dataMAR$noh * 8, dataMAR$cst / 1000, 
					  dataSH$noh * 8, dataSH$cst / 1000, 
					  "Hora-extra (horas)", "Custo (1000R$)", instanceName);
	}

#dev.copy(bmp,'myplot4.bmp')
#dev.off()

	# Plots a 3D chart - requires installing the rgl package (see menu option Pacakges >> Install Packages)
	#library(rgl);
	#newdata <- subset(gaOvertime, inst == instance_);
	#plot3d(newdata$noh * 8,newdata$mks,newdata$cst, col="red");	