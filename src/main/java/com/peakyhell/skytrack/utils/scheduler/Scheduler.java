package com.peakyhell.skytrack.utils.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scheduler {
    private final HashMap<Integer, List<Runnable>> tasks;
    private int currentTick;


    public Scheduler() {
        this.tasks = new HashMap<>();
        this.currentTick = 0;
    }

    /**
     * Schedule a task that must be run in <code>delay</code> ticks.
     * @param task The task to schedule
     * @param delay The delay before running the task
     */
    public void schedule(Runnable task, int delay) {
        if (task == null) return;

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
     * Schedule a task that must run every <code>interval</code> ticks, for <code>end</code> ticks.
     * @param task The task to schedule
     * @param delay The delay before running the task for the first time
     * @param end The duration (in ticks) to keep running the task
     * @param interval How often the task must be run (in ticks)
     */
    public void scheduleInterval(Runnable task, int delay, int end, int interval) {
        if (task == null || end <= 0 || interval <= 0) return;

        int startTick = this.currentTick + delay;
        for (int tick = startTick; tick < startTick + end; tick += interval) {
            this.tasks.computeIfAbsent(tick, k -> new ArrayList<>()).add(task);
        }
    }

    /**
     * Executes the tasks that are scheduled for the current tick and removes them from the tasks list.
     */
    public void tick() {
        if (tasks.containsKey(currentTick)) {
            List<Runnable> toExecute =  new ArrayList<>(tasks.get(currentTick));

            for (Runnable task : toExecute) {
                if (task != null) task.run();
            }
        }
        tasks.remove(currentTick);
        this.currentTick++;
    }

    public void clear() {
        this.tasks.clear();
    }
}
