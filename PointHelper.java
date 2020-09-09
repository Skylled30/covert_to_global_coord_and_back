package com.company;

public class PointHelper {
    private double latitude, longitude, altitude; //radians
    private double latitude2, longitude2; //degrees
    private double x, y, z;
    private double gX, gY, gZ;

    private final double R0 = 6_378_137.0;
    private final double R1 = 6_356_752.314_245;
    private final double Ee2 = 0.006694379990197619;

    public PointHelper(double latitude, double longitude, double altitude, double x, double y, double z) {
        this.latitude2 = latitude;
        this.longitude2 = longitude;
        this.latitude = Math.toRadians(latitude);
        this.longitude = Math.toRadians(longitude);
        this.altitude = altitude;
        this.x = x;
        this.y = y;
        this.z = z;
        gX = gY = gZ = 0;
    }

    public void convertToGlobalCoord(){
        double ro = R0 / Math.sqrt(1 - Ee2 * Math.sin(latitude) * Math.sin(latitude));
        gX = (ro + altitude) * Math.cos(latitude) * Math.cos(longitude);
        gY= (ro + altitude) * Math.cos(latitude) * Math.sin(longitude);
        gZ = (ro * (1 - Ee2) + altitude) * Math.sin(latitude);
    }

    public void getGlobalCoord(){
        //1. Параллельный перенос
        x = gX - x;
        y = gY - y;
        z = gZ - z;
        //2. Поворот против часовой на longitude
        x = x * Math.cos(longitude2) - y * Math.sin(longitude2);
        y = x * Math.sin(longitude2) + y * Math.cos(longitude2);
        //3. Поворопот по часовой на П/2 - latitude
        x = x * Math.sin(latitude2) - z * Math.cos(latitude2);
        z = -x * Math.cos(latitude2) + z * Math.sin(latitude2);

    }

    public double[] findHAndFi(double z2, double R){
        double R0 = 6378140.0;
        double Ff = 1.0/298.257;
        double Ee = 2.0*Ff-Ff*Ff;
        int j;
        double C,fi,x;
        fi = Math.atan2(z2, R);
        for(j = 1; j <= 15; j++){
            C = 1 / Math.sqrt(1 - Ee * Math.sin(fi) * Math.sin(fi));
            x = z2 + R0 * C * Ee * Math.sin(fi);
            fi = Math.atan(x / R);
        }
        C = 1 / Math.sqrt(1 - Ee * Math.sin(fi) * Math.sin(fi));
        x = R / Math.cos(fi) - R0 * C;
        return new double[]{x, fi};
    }

    public double[] getPointFromGlobal(){
        double r = Math.sqrt(x * x + y * y);
        double[] arg = findHAndFi(z, r);
        double h = arg[0], fi = arg[1];
        double ro = R0 / Math.sqrt(1 - Ee2 * Math.sin(fi) * Math.sin(fi));
        double alpha = Math.acos(x / ((ro + h) * Math.cos(fi)));
        return new double[]{
                Math.toDegrees(fi), //latitude
                Math.toDegrees(alpha), //longitude
                h //altitude
        };
    }

    public void printGlobalCoord(){
        System.out.println("gX, gY, gZ: " + gX + " " + gY + " " + gZ);
    }

    public void printGlobalCoord2(){
        System.out.println("x, y, z: " + x + " " + y + " " + z);
    }

}
