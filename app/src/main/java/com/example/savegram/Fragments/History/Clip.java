package com.example.savegram.Fragments.History;

public class Clip {
    private String clip,timeStamp;

    public Clip(String clip, String timeStamp) {
        this.clip = clip;
        this.timeStamp = timeStamp;
    }

    public String getClip() {
        return clip;
    }

    public void setClip(String clip) {
        this.clip = clip;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
