package com.peakyhell.skytrack.utils.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Scheduler {
    private int currentTick;
    private final HashMap<Integer, List<Runnable>> tasks;
    private final List<RecurringTask> recurringTasks;

    public Scheduler() {
        this.currentTick = 0;
        this.tasks = new HashMap<>();
        this.recurringTasks = new ArrayList<>();
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
     * Schedule a task that must run every <code>interval</code> ticks
     * @param task The task to schedule
     * @param delay The delay before running the task for the first time
     * @param interval The interval between each run of the task
     */
    public void scheduleRecurring(Runnable task, int delay, int interval) {
        scheduleRecurringCondition(task, delay, interval, () -> true);
    }

    /**
     * Schedule a task that must run every <code>interval</code> ticks, while <code>condition</code> is true.
     * @param task The task to schedule
     * @param delay The delay before running the task for the first time
     * @param interval The interval between each run of the task
     * @param condition The condition that must be true for the task to continue running.
     */
    public void scheduleRecurringCondition(Runnable task, int delay, int interval, BooleanSupplier condition) {
        this.recurringTasks.add(new RecurringTask(task, delay, interval, condition));
    }

    /**
     * Executes the tasks that are scheduled for the current tick and removes them from the tasks list.
     */
    public void tick() {
        // Run unique tasks
        if (this.tasks.containsKey(this.currentTick)) {
            List<Runnable> toExecute =  this.tasks.remove(this.currentTick);

            for (Runnable taskToRun : toExecute) {
                if (taskToRun != null) taskToRun.run();
            }
        }

        // Run recurring tasks
        if (!this.recurringTasks.isEmpty()) {
            this.recurringTasks.removeIf(rt -> !rt.condition.getAsBoolean()); // Remove tasks with conditions that became false

            List<RecurringTask> rTasks = new ArrayList<>(recurringTasks);

            for (RecurringTask rt : rTasks) {
                if (this.currentTick >= rt.startTick && (this.currentTick - rt.startTick) % rt.interval == 0) {
                    rt.task.run();
                }
            }
        }


        this.currentTick++;
    }

    public void clear() {
        this.tasks.clear();
    }

    public void clearRecurring() {
        this.recurringTasks.clear();
    }

    public int getCurrentTick() {
        return this.currentTick;
    }
}
