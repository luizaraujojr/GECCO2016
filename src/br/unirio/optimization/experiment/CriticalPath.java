package br.unirio.optimization.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import br.unirio.overwork.model.base.Activity;

public class CriticalPath {
    public static double maxCost;
    public static String format = "%1$-10s %2$-5s %3$-5s %4$-5s %5$-5s %6$-5s %7$-10s %8$-5s %9$-10s\n";
    
    

    public List<Activity> act;
    
    public CriticalPath(List<Activity> Atividades){
    	List<Activity> ListaAtividades = new ArrayList<Activity>();
    	ListaAtividades=Atividades;
    	
    	criticalPath(Atividades);
    }
//    public static void main(String[] args) {
//        // The example dependency graph
//        List<Activity> allTasks = new ArrayList<Activity>();
//        List<Activity> dep = new ArrayList<Activity>();
//        Project p = new Project();
//        
//        Activity end = new ActivityDevelopment(p, "End");
//        dep.add(end);
//        Activity F = new Activity("F", 2, dep);
//        
//        Activity A = new Activity("A", 3, dep);
//        dep.clear();
//        dep.add(F);
//        dep.add(A);
//        Activity X = new Activity("X", 4, dep);
//        
//        dep.clear();
//        dep.add(A);
//        dep.add(X);
//        
//        Activity Q = new Activity("Q", 2, dep);
//        dep.clear();
//        dep.add(Q);
//        
//        Activity start = new Activity("Start", 0, dep);
//        
//        allTasks.add(end);
//        allTasks.add(F);
//        allTasks.add(A);
//        allTasks.add(X);
//        allTasks.add(Q);
//        allTasks.add(start);
//        c
//        
//        Activity[] result = criticalPath(allTasks);
//        print(result);
//        
//        
//        // System.out.println("Critical Path: " + Arrays.toString(result));
//    }

//    // A wrapper class to hold the tasks during the calculation
//    public static class Activity {
//        // the actual cost of the task
//        public double CPMcost;
//        // the cost of the task along the critical path
//        public double criticalCost;
//        // a name for the task for printing
//        public String name;
//        // the earliest start
//        public double earlyStart;
//        // the earliest finish
//        public double earlyFinish;
//        // the latest start
//        public double latestStart;
//        // the latest finish
//        public double latestFinish;
//        // the tasks on which this task is dependant
//        public List<Activity> dependencies = new ArrayList<Activity>();
//
//        public Activity(String name, int cost, Iterable<Activity> dependencies) {
//            this.name = name;
//            this.CPMcost = cost;
//            for (Activity t : dependencies) {
//                this.dependencies.add(t);
//            }
//            this.earlyFinish = -1;
//        }
//        
//        public Activity(String name, int cost) {
//            this.name = name;
//            this.CPMcost = cost;
//            this.earlyFinish = -1;
//        }
//
//        public void setLatest() {
//            latestStart = maxCost - criticalCost;
//            latestFinish = latestStart + CPMcost;
//        }
//
//        public String[] toStringArray() {
//            String criticalCond = earlyStart == latestStart ? "Yes" : "No";
//            String[] toString = { name, earlyStart + "", earlyFinish + "", latestStart + "", latestFinish + "",
//                    latestStart - earlyStart + "", criticalCond };
//            return toString;
//        }
//
//        public boolean isDependent(Activity t) {
//            // is t a direct dependency?
//            if (dependencies.contains(t)) {
//                return true;
//            }
//            // is t an indirect dependency
//            for (Activity dep : dependencies) {
//                if (dep.isDependent(t)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }

    public static Activity[] criticalPath(List<Activity> tasks) {
        // tasks whose critical cost has been calculated
    	List<Activity> completed = new ArrayList<Activity>();
   
        // tasks whose critical cost needs to be calculated
    	List<Activity> remaining = new ArrayList<Activity>(tasks);

        // Backflow algorithm
        // while there are tasks whose critical cost isn't calculated.
        while (!remaining.isEmpty()) {
            boolean progress = false;

            // find a new task to calculate
            for (Iterator<Activity> it = remaining.iterator(); it.hasNext();) {
                Activity task = it.next();
                if (completed.containsAll(task.getDependencies())) {
                    // all dependencies calculated, critical cost is max
                    // dependency
                    // critical cost, plus our cost
                    double critical = 0;
                    for (Activity t : task.getPrecedences()) {
                        if (t.getCriticalCost() > critical && t.getCriticalCost() > 0) {
                            critical = t.getCriticalCost();
                        }
                        else critical = t.getCPMcost();
                    }
                    task.setCriticalCost (critical + task.getCPMcost());
                    // set task as calculated an remove
                    completed.add(task);
                    it.remove();
                    // note we are making progress
                    progress = true;
                }
            }
            // If we haven't made any progress then a cycle must exist in
            // the graph and we wont be able to calculate the critical path
            if (!progress)
                throw new RuntimeException("Cyclic dependency, algorithm stopped!");
        }

        // get the cost
        maxCost(tasks);
        
        List<Activity> initialNodes = initials(tasks);
        calculateEarly(initialNodes);

        // get the tasks
        Activity[] ret = completed.toArray(new Activity[0]);
        // create a priority list
        Arrays.sort(ret, new Comparator<Activity>() {

            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return ret;
    }

    public static void calculateEarly(List<Activity> initials) {
        for (Activity initial : initials) {
            initial.setEarlyStart(0);
            initial.setEarlyFinish(initial.getCPMcost());
            setEarly(initial);
        }
    }

    public static void setEarly(Activity initial) {
        double completionTime = initial.getEarlyFinish();
        for (Activity t : initial.getPrecedences()) {
            if (completionTime >= t.getEarlyStart()) {
                t.setEarlyStart (completionTime);
                t.setEarlyFinish (completionTime + t.getCPMcost());
            }
            setEarly(t);
        }
    }

    public static List<Activity> initials(List<Activity> tasks) {
    	List<Activity> remaining = new ArrayList<Activity>(tasks);
  
        for (Activity t : tasks) {
        	if(t.getDependencies().size() > 0 ) 
            //for (Activity td : t.getPrecedences()) {
                remaining.remove(t);
            //}
        }
        

        System.out.print("Initial nodes: ");
        for (Activity t : remaining)
            System.out.print(t.getName() + " ");
        System.out.print("\n\n");
        return remaining;
    }

    public static void maxCost(List<Activity> tasks) {
        double max = -1;
        for (Activity t : tasks) {
            if (t.getCriticalCost() > max)
                max = t.getCriticalCost();
        }
        maxCost = max;
        System.out.println("Critical path length (cost): " + maxCost);
        for (Activity t : tasks) {
            setLatest(t);
        }
    }

    public static void print(Activity[] tasks) {
        System.out.format(format, "Task", "ST", "FN", "ES", "EF", "LS", "LF", "Slack", "Critical?");
        for (Activity t : tasks)
            System.out.format(format, (Object[]) t.toStringArray());
    }
    
    public static void setLatest(Activity t ) {
        //t.setLatestStart(maxCost - t.getCriticalCost());
        t.setLatestStart(t.getEarlyStart() + t.getCriticalCost());
        t.setLatestFinish(t.getLatestStart() + t.getCPMcost());
    }
}