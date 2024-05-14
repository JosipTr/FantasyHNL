package com.fantasyhnl.venue;

import lombok.Data;

@Data
public class Venue {
    private int id;
    private String name;
    private String address;
    private String city;
    private Integer capacity;
    private String surface;
    private String image;
}
