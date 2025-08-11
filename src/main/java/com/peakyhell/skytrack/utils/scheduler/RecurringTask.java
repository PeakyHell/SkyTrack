package com.peakyhell.skytrack.utils.scheduler;

import com.peakyhell.skytrack.SkyTrack;

import java.util.function.BooleanSupplier;

public class RecurringTask {
    Runnable task;
    int startTick;
    int interval;
    BooleanSupplier condition;

    /**
     * Creates a task that will be run every <code>interval</code> while <code>condition</code> is met.
     * The first occurrence will be run after <code>delay</code> ticks.
     * @param task The task to schedule
     * @param delay The delay before running the task
     * @param interval The interval (in ticks) between each occurrence
     * @param condition The condition the task must fill to continue running
     */
    public RecurringTask(Runnable task, int delay, int interval, BooleanSupplier condition) {
        this.task = task;
        this.startTick = SkyTrack.SCHEDULER.getCurrentTick() + delay;
        this.interval = interval;
        this.condition = condition;
    }
}
