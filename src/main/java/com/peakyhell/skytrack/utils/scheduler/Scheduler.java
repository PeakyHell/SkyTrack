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

    // Getters
    public int getCurrentTick() { return this.currentTick; }

    public HashMap<Integer, List<Runnable>> getTasks() { return tasks; }

    public List<RecurringTask> getRecurringTasks() { return recurringTasks; }

    // Setters
    private void incrementCurrentTick() { this.currentTick++; }

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
        this.getTasks().computeIfAbsent(this.getCurrentTick() + delay, k -> new ArrayList<>()).add(task);
    }

    /**
     * Schedule a task that must run every <code>interval</code> ticks
     * @param task The task to schedule
     * @param delay The delay before running the task for the first time
     * @param interval The interval between each run of the task
     */
    public void scheduleRecurring(Runnable task, int delay, int interval) {
        scheduleRecurringCondition(task, delay, interval, () -> true, true);
    }

    /**
     * Schedule a task that must run every <code>interval</code> ticks, while <code>condition</code> is true.
     * @param task The task to schedule
     * @param delay The delay before running the task for the first time
     * @param interval The interval between each run of the task
     * @param condition The condition that must be true for the task to continue running.
     * @param keep If <code>condition</code> become false, if true, the task will not be deleted and will be restarted when condition become true again
     */
    public void scheduleRecurringCondition(Runnable task, int delay, int interval, BooleanSupplier condition, boolean keep) {
        this.getRecurringTasks().add(new RecurringTask(task, delay, interval, condition, keep));
    }

    /**
     * Executes the tasks that are scheduled for the current tick and removes them from the tasks list.
     */
    public void tick() {
        // Run unique tasks
        if (this.getTasks().containsKey(this.getCurrentTick())) {
            List<Runnable> toExecute =  this.getTasks().remove(this.getCurrentTick());

            for (Runnable taskToRun : toExecute) {
                if (taskToRun != null) taskToRun.run();
            }
        }

        // Run recurring tasks
        if (!this.getRecurringTasks().isEmpty()) {
            this.getRecurringTasks().removeIf(rt -> !rt.getCondition().getAsBoolean() && !rt.isKeep()); // Remove tasks with conditions that became false and that must not be kept

            List<RecurringTask> rTasks = new ArrayList<>(this.getRecurringTasks());

            for (RecurringTask rt : rTasks) {
                if (rt.getCondition().getAsBoolean() && this.getCurrentTick() >= rt.getStartTick() && (this.getCurrentTick() - rt.getStartTick()) % rt.getInterval() == 0) {
                    rt.getTask().run();
                }
            }
        }


        this.incrementCurrentTick();
    }

    public void clear() {
        this.getTasks().clear();
    }

    public void clearRecurring() {
        this.getRecurringTasks().clear();
    }
}
