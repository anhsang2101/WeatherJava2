package com.example.weatherjava.model;

public class Weather {

    private String name;
    private String date;
    private String description;
    private String status;
    private String temp;
    private String humidity;
    private String feelLike;
    private String wind;
    private String sunrise;
    private String sunset;
    private String icStatus;


    public Weather(String name, String date, String description, String status, String temp, String humidity, String feelLike, String wind, String sunrise, String sunset, String icStatus) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.status = status;
        this.temp = temp;
        this.humidity = humidity;
        this.feelLike = feelLike;
        this.wind = wind;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.icStatus = icStatus;
    }

    public Weather() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelLike() {
        return feelLike;
    }

    public void setFeelLike(String feelLike) {
        this.feelLike = feelLike;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getIcStatus() {
        return icStatus;
    }

    public void setIcStatus(String icStatus) {
        this.icStatus = icStatus;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", feelLike='" + feelLike + '\'' +
                ", wind='" + wind + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", icStatus='" + icStatus + '\'' +
                '}';
    }
}
