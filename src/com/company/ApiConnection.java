package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ApiConnection {
    private final String url;
    private HttpURLConnection urlConnection;

    public ApiConnection() {
        Random rnd = new Random();
        url = "http://numbersapi.com/" + Math.abs(rnd.nextInt()) % 1001 + "/trivia";
    }

    private void setConnection() throws IOException {
        URL obj = new URL(url);
        urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
    }

    public String getRequestString() throws IOException {
        setConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        return response.toString();
    }

    public String getUrl() {
        return url;
    }
}
