package org.example.model;

public class OrderCancelRequest {
    private String track;

    public void setTrack(String track) {
        this.track = track;
    }

    public OrderCancelRequest(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

}
