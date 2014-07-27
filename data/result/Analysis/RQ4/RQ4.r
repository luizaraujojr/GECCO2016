# clear all objects in memory
rm(list = ls());

# Load data - micro do Marcio
gaOvertime <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq4/nsga_overtime.txt", header=TRUE);
gaNoError <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq4/nsga_noerror.txt", header=TRUE);
cpm <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq4/cpm.txt", header=TRUE);
margarine <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq4/margarine.txt", header=TRUE);
sh <- read.table(file="/Users/Marcio/Desktop/Codigos/Hector/data/result/Analysis/rq4/secondhalf.txt", header=TRUE);

# Separate instances
instances <- unique(gaOvertime$inst);

# Creates and fills the correlation matrices
# - interesting fact: noh and mks have extremelly high correlation - thus, noh is not necessarily required in the optimization
# - removing noh would acceleate things and allow to run 100k fit evals (something to test for the MSc Thesis)
# - correlations are yet stronger the on the overtime scenario!
corrMatrixOT <- matrix(nrow=length(instances), ncol=3, dimnames=list(instances, c("NOHxMKS", "NOHxCST", "MKSxCST")));
corrMatrixNE <- matrix(nrow=length(instances), ncol=3, dimnames=list(instances, c("NOHxMKS", "NOHxCST", "MKSxCST")));

for (instance_ in instances)
{
	newdataOT <- subset(gaOvertime, inst == instance_);

	corrMatrixOT[instance_, 1] <- cor(newdataOT$noh, newdataOT$mks, method="spearman");
	corrMatrixOT[instance_, 2] <- cor(newdataOT$noh, newdataOT$cst, method="spearman");
	corrMatrixOT[instance_, 3] <- cor(newdataOT$mks, newdataOT$cst, method="spearman");

	newdataNE <- subset(gaNoError, inst == instance_);

	corrMatrixNE[instance_, 1] <- cor(newdataNE$noh, newdataNE$mks, method="spearman");
	corrMatrixNE[instance_, 2] <- cor(newdataNE$noh, newdataNE$cst, method="spearman");
	corrMatrixNE[instance_, 3] <- cor(newdataNE$mks, newdataNE$cst, method="spearman");
}

corrMatrixOT;
corrMatrixNE;

# Ploting function
plot.my.chart <- function(xo, yo, xe, ye, xc, yc, xm, ym, xs, ys, xTitle, yTitle) {
	xrange <- range(c(xo, xe, xc, xm, xe));
	yrange <- range(c(yo, ye, yc, ym, ye));
	
	plot(xrange, yrange, type="n", xlab=xTitle, ylab=yTitle);
	text(min(xrange) + 0.95 * (max(xrange) - min(xrange)), min(yrange) + 0.95 * (max(yrange) - min(yrange)), instance_, font=2);

	lines(xo, yo, type="p", col="red", cex=1, pch=1);
	lines(xe, ye, type="p", col="blue", cex=0.5, pch=1);

	lines(xc, yc, type="p", col="black", pch=3);
	#text(xc, yc, "C");

	lines(xm, ym, type="p", col="black", pch=4);
	#text(xm, ym, "M");

	lines(xs, ys, type="p", col="black", pch=8);
	#text(xs, ys, "S");
}

# Plot 3D faces for all instances
pdf("c:/Users/Marcio/Desktop/faces-3d.pdf", width=10, height=16)
par(mfrow=c(6, 3))
par(mar=c(3,3,1,1)+0.1,mgp=c(2,1,0))

for (instance_ in instances)
{
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
				  "Makespan (days)", "Cost (1000$)");
				  
	plot.my.chart(dataOT$mks, dataOT$noh, 
				  dataNE$mks, dataNE$noh, 
				  dataCPM$mks, dataCPM$noh, 
				  dataMAR$mks, dataMAR$noh, 
				  dataSH$mks, dataSH$noh, 
				  "Makespan (days)", "Overtime (hours)");
				  
	plot.my.chart(dataOT$cst / 1000, dataOT$noh, 
				  dataNE$cst / 1000, dataNE$noh, 
				  dataCPM$cst / 1000, dataCPM$noh, 
				  dataMAR$cst / 1000, dataMAR$noh, 
				  dataSH$cst / 1000, dataSH$noh, 
				  "Cost (1000$)", "Overtime (hours)");
}

dev.off();

# Plots a 3D chart - requires installing the rgl package (see menu option Pacakges >> Install Packages)
#library(rgl);
#plot3d(newdata$noh,newdata$mks,newdata$cst, col="red");
