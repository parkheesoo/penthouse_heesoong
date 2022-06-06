package com.penthouse_bogmjary;

public class FeeData {
    int elec_y = 0;
    int gas_y = 0;
    int water_y = 0;

    public FeeData(){}
    public FeeData(int elec_y, int gas_y, int water_y) {
        this.elec_y = elec_y;
        this.gas_y = gas_y;
        this.water_y = water_y;
    }

    public int getElec_y(){
        return elec_y;
    }
    public void setElec_y(int elec_y){
        this.elec_y = elec_y;
    }

    public int getGas_y(){
        return gas_y;
    }
    public void setGas_y(int gas_y){
        this.gas_y = gas_y;
    }

    public int getWater_y(){
        return water_y;
    }
    public void setWater_y(int water_y){
        this.water_y = water_y;
    }
}
