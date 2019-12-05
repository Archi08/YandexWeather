package service;

import model.Weather;

public class LogForecast {

    public void outputWeather(Weather weather, String country) {
        System.out.println("Страна: " + country + "\n"
                + "Температура: " + weather.fact.temp + "\n"
                + "Ощущается как: " + weather.fact.feels_like + "\n"
                + "Погодные условия: " + parseCondition(weather.fact.condition) + "\n"
                + "Время года: " + parseSeason(weather.fact.season));
    }

    public String parseCondition(String condition) {
        String cond = new String();
        switch (condition) {
            case "clear":
                cond = "ясно";
                break;
            case "partly-cloudy":
                cond = "малооблачно";
                break;
            case "cloudy":
                cond = "облачно с прояснениями";
                break;
            case "overcast":
                cond = "пасмурно";
                break;
            case "partly-cloudy-and-light-rain":
                cond = "небольшой дождь";
                break;
            case "partly-cloudy-and-rain":
                cond = "дождь";
                break;
            case "overcast-and-rain":
                cond = "сильный дождь";
                break;
            case "overcast-thunderstorms-with-rain":
                cond = "сильный дождь, гроза";
                break;
            case "cloudy-and-light-rain":
                cond = "небольшой дождь";
                break;
            case "overcast-and-light-rain":
                cond = "небольшой дождь";
                break;
            case "cloudy-and-rain":
                cond = "дождь";
                break;
            case "overcast-and-wet-snow":
                cond = "дождь со снегом";
                break;
            case "partly-cloudy-and-light-snow":
                cond = "небольшой снег";
                break;
            case "partly-cloudy-and-snow":
                cond = "снег";
                break;
            case "overcast-and-snow":
                cond = "снегопад";
                break;
            case "cloudy-and-light-snow":
                cond = "небольшой снег";
                break;
            case "overcast-and-light-snow":
                cond = "небольшой снег";
                break;
            case "cloudy-and-snow ":
                cond = "снег";
                break;
        }
       return cond;
    }

    public String parseSeason (String season) {
        String seas = new String();
        switch (season) {
            case "summer":
                seas = "лето";
                break;
            case "autumn":
                seas = "осень";
                break;
            case "winter":
                seas = "зима";
                break;
            case "spring":
                seas = "весна";
                break;
        }
        return seas;
    }

}
