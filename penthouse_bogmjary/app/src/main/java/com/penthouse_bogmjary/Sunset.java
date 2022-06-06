package com.penthouse_bogmjary;

public class Sunset {
    private static final double PI = 3.141592d;

    private double DegreeToRadian(double d) {
        return (d * PI) / 180.0d;
    }

    private double RadianToDegree(double d) {
        return (d * 180.0d) / PI;
    }

    private boolean IsLeapYear(int i) {
        return (i % 4 == 0 && i % 100 != 0) || i % 400 == 0;
    }

    private int GetLastDay(int i, int i2) {
        if (i2 != 2) {
            return (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31;
        }
        if (i % 4 != 0) {
            return 28;
        }
        if (i % 100 != 0 || i % 400 == 0) {
            return 29;
        }
        return 28;
    }

    private int CalcJulianDay(int i, int i2, int i3) {
        int i4 = 0;
        for (int i5 = 1; i5 < i2; i5++) {
            i4 += GetLastDay(i, i5);
        }
        return i4 + i3;
    }

    private double CalcGamma(int i) {
        double d = (double) (i - 1);
        Double.isNaN(d);
        return d * 0.01721420273972603d;
    }

    private double CalcGamma2(int i, int i2) {
        double d = (double) (i - 1);
        double d2 = (double) i2;
        Double.isNaN(d2);
        Double.isNaN(d);
        return (d + (d2 / 24.0d)) * 0.01721420273972603d;
    }

    private double CalcEqofTime(double d) {
        double d2 = d * 2.0d;
        return (((((Math.cos(d) * 0.001868d) + 7.5E-5d) - (Math.sin(d) * 0.032077d)) - (Math.cos(d2) * 0.014615d)) - (Math.sin(d2) * 0.040849d)) * 229.18d;
    }

    private double CalcSolarDec(double d) {
        double d2 = d * 2.0d;
        return (((0.006918d - (Math.cos(d) * 0.399912d)) + (Math.sin(d) * 0.070257d)) - (Math.cos(d2) * 0.006758d)) + (Math.sin(d2) * 9.07E-4d);
    }

    private double CalcHourAngle(double d, double d2, int i) {
        double DegreeToRadian = DegreeToRadian(d);
        double acos = Math.acos((Math.cos(DegreeToRadian(90.833d)) / (Math.cos(DegreeToRadian) * Math.cos(d2))) - (Math.tan(DegreeToRadian) * Math.tan(d2)));
        if (i == 1) {
            return acos;
        }
        if (i == 0) {
            return -acos;
        }
        return 0.0d;
    }

    private double CalcSunriseGMT(int i, double d, double d2) {
        double CalcGamma = CalcGamma(i);
        double CalcGamma2 = CalcGamma2(i, (int) (((((d2 - RadianToDegree(CalcHourAngle(d, CalcSolarDec(CalcGamma), 1))) * 4.0d) + 720.0d) - CalcEqofTime(CalcGamma)) / 60.0d));
        return (((d2 - RadianToDegree(CalcHourAngle(d, CalcSolarDec(CalcGamma2), 1))) * 4.0d) + 720.0d) - CalcEqofTime(CalcGamma2);
    }

    private double CalcSunsetGMT(int i, double d, double d2) {
        double CalcGamma = CalcGamma(i + 1);
        double CalcGamma2 = CalcGamma2(i, (int) (((((d2 - RadianToDegree(CalcHourAngle(d, CalcSolarDec(CalcGamma), 0))) * 4.0d) + 720.0d) - CalcEqofTime(CalcGamma)) / 60.0d));
        return (((d2 - RadianToDegree(CalcHourAngle(d, CalcSolarDec(CalcGamma2), 0))) * 4.0d) + 720.0d) - CalcEqofTime(CalcGamma2);
    }

    public String GetTimeString(double d) {
        double d2 = d / 60.0d;
        double floor = Math.floor(d2);
        double floor2 = (d2 - Math.floor(d2)) * 60.0d;
        double floor3 = Math.floor(floor2);
        Math.floor((floor2 - Math.floor(floor2)) * 60.0d);
        return BuildConfig.FLAVOR + Integer.toString((int) floor) + ":" + Integer.toString((int) floor3);
    }

    public double GetSunriseTime(int i, int i2, int i3, double d, double d2, int i4, int i5) {
        double CalcSunriseGMT = CalcSunriseGMT(CalcJulianDay(i, i2, i3), d, d2);
        double d3 = (double) i4;
        Double.isNaN(d3);
        double d4 = CalcSunriseGMT - (d3 * 60.0d);
        double d5 = (double) i5;
        Double.isNaN(d5);
        return d4 + d5;
    }

    public double GetSunsetTime(int i, int i2, int i3, double d, double d2, int i4, int i5) {
        double CalcSunsetGMT = CalcSunsetGMT(CalcJulianDay(i, i2, i3), d, d2);
        double d3 = (double) i4;
        Double.isNaN(d3);
        double d4 = CalcSunsetGMT - (d3 * 60.0d);
        double d5 = (double) i5;
        Double.isNaN(d5);
        return d4 + d5;
    }

    public void sunsettest() {
        double GetSunriseTime = GetSunriseTime(2015, 8, 19, 37.34d, -126.589999d, -9, 0);
        GetTimeString(GetSunriseTime);
        GetSunsetTime(2015, 8, 19, 37.34d, -126.589999d, -9, 0);
        GetTimeString(GetSunriseTime);
    }

    public double sunpos(double d) {
        return Math.cos(Math.toRadians((d + 10.0d) * 0.9863013625144958d)) * -23.440000534057617d;
    }

    public double sunattitude(double d, double d2, double d3) {
        return Math.toDegrees(Math.asin((Math.sin(Math.toRadians(d2)) * Math.sin(Math.toRadians(d))) + (Math.cos(Math.toRadians(d2)) * Math.cos(Math.toRadians(d)) * Math.cos(Math.toRadians((d3 * 15.0d) - 0.02490234375d)))));
    }

    public double suncenter(double d, double d2, double d3) {
        double d4 = (d2 * 15.0d) - 0.02490234375d;
        double degrees = Math.toDegrees(Math.asin((-Math.sin(Math.toRadians(d4))) * Math.cos(Math.toRadians(sunpos(d3))) * Math.sin(Math.toRadians(90.0d - d))));
        return d4 >= -90.0d ? 180.0d - degrees : degrees;
    }
}