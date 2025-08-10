package com.peakyhell.skytrack.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scheduler {
    HashMap<Integer, List<Runnable>> tasks;
    int currentTick;


    public Scheduler() {
        this.tasks = new HashMap<>();
        this.currentTick = 0;
    }

    /**
     * Schedule a task that must be run in `delay` ticks.
     * @param task The task to schedule
     * @param delay The delay before running the task
     */
    public void schedule(Runnable task, int delay) {
        // If no or negative delay, run immediately
        if (delay <= 0) {
            task.run();
            return;
        }

        // If tasks are already scheduled for tick, add to list.
        // Else initialize new list for tick
        this.tasks.computeIfAbsent(this.currentTick + delay, k -> new ArrayList<>()).add(task);
    }

    /**
     * Executes the tasks that are scheduled for the current tick and removes them from the tasks list.
     */
    public void tick() {
        if (tasks.containsKey(currentTick)) {
            List<Runnable> toExecute =  new ArrayList<>(tasks.get(currentTick));

            for (Runnable task : toExecute) {
                task.run();

            }
        }
        tasks.remove(currentTick);
        this.currentTick++;
    }
}
