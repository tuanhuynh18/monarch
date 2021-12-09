package com.example.monarch.data;

import java.util.HashMap;

public class CostOfLiving {
    public static final HashMap<String, Integer> AVERAGE_COST_OF_LIVING = new HashMap<String, Integer>();
    static {
        AVERAGE_COST_OF_LIVING.put("Atlanta", 9);
        AVERAGE_COST_OF_LIVING.put("Boston", 14);
        AVERAGE_COST_OF_LIVING.put("Los Angeles", 15);
    }
}
