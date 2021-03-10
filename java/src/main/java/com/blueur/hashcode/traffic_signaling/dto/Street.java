package com.blueur.hashcode.traffic_signaling.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Street {
    private final int id;
    private String name;
    private int length;
    private Intersection start;
    private Intersection end;

    public void setStart(Intersection start) {
        this.start = start;
        start.getOutgoingStreets().push(this);
    }

    public void setEnd(Intersection end) {
        this.end = end;
        end.getIncomingStreets().push(this);
    }
}
