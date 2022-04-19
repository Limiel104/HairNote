package com.example.hairnote;

public class Wash {
    private int id;
    //TO DO change date type or make more variables
    private String date; //dd-mm-yyyy for now
    private boolean isCleansing;
    // TO DO Cosmetics List
    private boolean usedPeeling;
    private boolean usedOiling;
    private String description;

    public Wash(int id, String date, boolean isCleansing, boolean usedPeeling, boolean usedOiling, String description) {
        this.id = id;
        this.date = date;
        this.isCleansing = isCleansing;
        this.usedPeeling = usedPeeling;
        this.usedOiling = usedOiling;
        this.description = description;
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

    @Override
    public String toString() {
        return "Wash{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", isCleansing=" + isCleansing +
                ", usedPeeling=" + usedPeeling +
                ", usedOiling=" + usedOiling +
                ", description='" + description + '\'' +
                '}';
    }
}
