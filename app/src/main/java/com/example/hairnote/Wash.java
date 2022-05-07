package com.example.hairnote;

import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

public class Wash implements Comparable<Wash>{
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

    @Override
    public int compareTo(Wash wash) {

        String d1 = this.date.substring(0,2);
        String m1 = this.date.substring(5,7);
        String y1 = this.date.substring(10,14);

        String d2 = wash.date.substring(0,2);
        String m2 = wash.date.substring(5,7);
        String y2 = wash.date.substring(10,14);

        String date1 = y1+m1+d1;
        String date2 = y2+m2+d2;

        int number1 = Integer.parseInt(date1);
        int number2 = Integer.parseInt(date2);

        return Integer.compare(number1,number2);
   }

}
