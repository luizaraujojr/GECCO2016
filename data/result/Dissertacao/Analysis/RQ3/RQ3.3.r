data_error1 <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_error1_frontier_obj.txt", header=TRUE);
data_noerror <- read.table(file="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/basedata/nsga/150k/nsga_150k_c50_2x_noerror_frontier_obj.txt", header=TRUE);

library(plyr);
count(data_error1, "inst");
count(data_noerror, "inst");
