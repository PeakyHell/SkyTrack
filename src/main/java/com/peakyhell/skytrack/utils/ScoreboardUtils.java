package com.peakyhell.skytrack.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardUtils {

    public static List<String> getLines() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return null;
        }

        // Fetch sidebar
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective sidebar = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

        // Get all entries that aren't null or invisible
        List<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(sidebar).stream()
                .filter(entry -> entry != null && !entry.hidden()) // Remove entries that are hidden
                .sorted(Comparator.comparingInt(ScoreboardEntry::value)) // Sort entries by value
                .toList();

        // Rebuild visible lines
        ArrayList<String> visibleLines = new ArrayList<>();
        for (ScoreboardEntry entry : entries) {
            visibleLines.add(Team.decorateName(scoreboard.getScoreHolderTeam(entry.owner()), entry.name()).getString());
        }

        // Reverse lines order
        return visibleLines.reversed();
    }
}
