package com.sparta.framework.connection;

import java.util.HashMap;

public class ConnectionManager {

    public static final String BASE_URL = "https://swapi.dev/api/";

    private String URL;
    private HashMap<String, String> parameters;

    private ConnectionManager() {
        this.URL = BASE_URL;
        this.parameters = new HashMap<>();
        parameters.put("format", "json");
    }

    public static ConnectionManager from() {
        return new ConnectionManager();
    }

    public ConnectionManager URL(String URL) {
        this.URL = URL;
        return this;
    }

    public ConnectionManager baseURL() {
        return URL(BASE_URL);
    }

    public ConnectionManager slash(String endpoint) {
        if (endpoint.startsWith("/") && URL.endsWith("/")) {
            endpoint = endpoint.substring(1);
        } else if (!endpoint.startsWith("/") && !URL.endsWith("/")) {
            endpoint = "/" + endpoint;
        }
        URL += endpoint;
        return this;
    }

    public ConnectionManager withParameter(String parameter, String value) {
        parameters.put(parameter, value);
        return this;
    }

    public ConnectionManager withParameter(String parameter, int value) {
        return withParameter(parameter, "" + value);
    }

    public ConnectionManager withParameter(String parameter, float value) {
        return withParameter(parameter, "" + value);
    }

    private String buildURL() {
        String finalURL = URL;
        if (finalURL.endsWith("/")) {
            finalURL.replaceAll("\\/$", "");
        }
        for (String key: parameters.keySet()) {
            if (finalURL.contains("?")) {
                finalURL += "&";
            } else {
                finalURL += "?";
            }
            finalURL += key + "=" + parameters.get(key);
        }
        return finalURL;
    }

    public ConnectionResponse getResponse() {
        return new ConnectionResponse().makeRequest(buildURL());
    }

    public String getURL() {
        return URL;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
}