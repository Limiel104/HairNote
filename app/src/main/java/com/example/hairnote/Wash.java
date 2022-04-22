package com.example.hairnote;

import java.util.ArrayList;
import java.util.List;

public class Wash {
    private int id;
    private String date;
    private boolean isCleansing;
    private boolean usedPeeling;
    private boolean usedOiling;
    private String description;
    private List<Integer> usedCosmetics;

    public Wash(int id, String date, boolean isCleansing, boolean usedPeeling, boolean usedOiling, String description, List<Integer> usedCosmetics) {
        this.id = id;
        this.date = date;
        this.isCleansing = isCleansing;
        this.usedPeeling = usedPeeling;
        this.usedOiling = usedOiling;
        this.description = description;
        this.usedCosmetics = usedCosmetics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCleansing() {
        return isCleansing;
    }

    public void setCleansing(boolean cleansing) {
        isCleansing = cleansing;
    }

    public boolean isUsedPeeling() {
        return usedPeeling;
    }

    public void setUsedPeeling(boolean usedPeeling) {
        this.usedPeeling = usedPeeling;
    }

    public boolean isUsedOiling() {
        return usedOiling;
    }

    public void setUsedOiling(boolean usedOiling) {
        this.usedOiling = usedOiling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getUsedCosmetics() {
        return usedCosmetics;
    }

    public void setUsedCosmetics(List<Integer> usedCosmetics) {
        this.usedCosmetics = usedCosmetics;
    }

    @Override
    public String toString() {
        return "Wash{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", isCleansing=" + isCleansing +
                ", usedPeeling=" + usedPeeling +
                ", usedOiling=" + usedOiling +
                ", description='" + description + '\'' +
                ", usedCosmetics=" + usedCosmetics +
                '}';
    }
}
