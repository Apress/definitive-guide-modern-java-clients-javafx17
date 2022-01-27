package org.modernclientjava.model;

public class Main {

    private float humidity;
    private float pressure;
    private float temp_max;
    private float temp_min;
    private float temp;
    private float feels_like;

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Main{" +
                "humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", temp_max='" + temp_max + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
