package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        double x0 = 50, y0 = 50, z0 = 50;
        PointHelper point1 = new PointHelper(52.259373, 104.211082, 468, 1, 1, 1);
        point1.convertToGlobalCoord();
        point1.printGlobalCoord();
        point1.getGlobalCoord();
        point1.printGlobalCoord2();
        System.out.println("Полученные координаты: " + Arrays.toString(point1.getPointFromGlobal()));
    }
}
