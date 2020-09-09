package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        PointHelper point1 = new PointHelper(52.259373, 104.211082, 468, 1, 1, 1);
        point1.convertToGlobalCoord();
        point1.printGlobalCoord();
        point1.getGlobalCoord();
        point1.printGlobalCoord2();
        System.out.println(Arrays.toString(point1.getPointFromGlobal()));
    }
}
