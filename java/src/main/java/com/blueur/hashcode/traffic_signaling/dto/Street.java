package com.blueur.hashcode.traffic_signaling.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Street {
    @EqualsAndHashCode.Include
    private String name;
    private int length;
    @ToString.Exclude
    private Intersection start;
    @ToString.Exclude
    private Intersection end;

    public void setStart(Intersection start) {
        this.start = start;
        start.getOutgoingStreets().add(this);
    }

    public void setEnd(Intersection end) {
        this.end = end;
        end.getIncomingStreets().add(this);
    }
}
