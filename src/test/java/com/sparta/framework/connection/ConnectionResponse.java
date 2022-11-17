package com.sparta.framework.connection;

import com.sparta.framework.exception.ConnectionManagementException;
import com.sparta.framework.injection.Injector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnectionResponse {

    private HttpResponse<String> response;

    protected ConnectionResponse(String URL) {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(URL))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException e) {
            throw new ConnectionManagementException("Given URL, " + URL + ", is not a valid URL");
        } catch (IOException | InterruptedException e) {
            throw new ConnectionManagementException("Request could not be made: " + e.getMessage());
        }
        this.response = response;
    }

    public ConnectionResponse and() {
        return this;
    }

    public String getHeader(String key) {
        return response
                .headers()
                .firstValue(key)
                .orElse("Header not found.");
    }

    public int getStatusCode() {
        return response.statusCode();
    }

    public <T> T getBodyAs(Class<T> dtoClass) {
        if (dtoClass == String.class) {
            return (T) response.body();
        } else {
            return Injector.getDTO(response.body(), dtoClass);
        }
    }

}
