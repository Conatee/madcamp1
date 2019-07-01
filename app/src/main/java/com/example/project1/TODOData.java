package com.example.project1;

import java.io.Serializable;
import java.util.Comparator;

public class TODOData implements Serializable, Comparator<TODOData> {

    private static final long serialVersionUID = 1L;

    String title, date;
    boolean isTrivial, isUrgent, done = false;

    public TODOData () {
    }

    public TODOData(String title, String date, boolean isTrivial, boolean isUrgent) {
        this.title = title;
        this.date = date;
        this.isTrivial = isTrivial;
        this.isUrgent = isUrgent;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public boolean isTrivial() {
        return isTrivial;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public boolean isDone() { return done;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTrivial(boolean isTrivial) {
        this.isTrivial = isTrivial;
    }

    public void setUrgent(boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public void setDone(boolean done) { this.done = done; }

    @Override
    public int compare(TODOData t1, TODOData t2) {
        if (t1.isDone() == t2.isDone()) {
            if(Integer.parseInt(t1.getDate()) > Integer.parseInt(t2.getDate())) {
                return 1;
            }
            else if(Integer.parseInt(t1.getDate()) < Integer.parseInt(t2.getDate())) {
                return -1;
            }
            else {
                return 0;
            }
        } else {
            if (t1.isDone() == true) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
