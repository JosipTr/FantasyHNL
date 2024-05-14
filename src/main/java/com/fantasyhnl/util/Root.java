package com.fantasyhnl.util;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Root<T> {
    private String get;
    private ArrayList<Object> errors;
    private int results;
    private Paging paging;
    private ArrayList<T> response;
}
