package com.peakyhell.skytrack.utils.scheduler;

import com.peakyhell.skytrack.SkyTrack;

import java.util.function.BooleanSupplier;


public class RecurringTask {

    private final Runnable task;
    private final int startTick;
    private final int interval;
    private final BooleanSupplier condition;
    private final boolean keep;


// === Constructors ===
    /**
     * Creates a task that will be run every <code>interval</code> while <code>condition</code> is met.
     * The first occurrence will be run after <code>delay</code> ticks.
     * @param task The task to schedule
     * @param delay The delay before running the task
     * @param interval The interval (in ticks) between each occurrence
     * @param condition The condition the task must fill to continue running
     * @param keep If <code>condition</code> become false, if true, the task will not be deleted and will be restarted when condition become true again
     */
    public RecurringTask(Runnable task, int delay, int interval, BooleanSupplier condition, boolean keep) {
        this.task = task;
        this.startTick = SkyTrack.SCHEDULER.getCurrentTick() + delay;
        this.interval = interval;
        this.condition = condition;
        this.keep = keep;
    }


// === Getters ===
    public Runnable getTask() { return task; }
    public int getStartTick() { return startTick; }
    public int getInterval() { return interval; }
    public BooleanSupplier getCondition() { return condition; }
    public boolean isKeep() { return keep; }
}
