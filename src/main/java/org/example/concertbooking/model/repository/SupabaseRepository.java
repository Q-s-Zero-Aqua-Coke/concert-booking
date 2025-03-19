package org.example.concertbooking.model.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public interface SupabaseRepository<T> {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    String baseUrl = "%s/rest/v1".formatted(dotenv.get("SUPABASE_URL"));
    String apiKey = dotenv.get("SUPABASE_KEY");

    default void save(T dto, String tableName) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s".formatted(URI.create(baseUrl), tableName)))
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(dto)))
                .headers(
                        "apikey", apiKey,
                        "Authorization", "Bearer %s".formatted(apiKey),
                        "Content-Type", "application/json",
                        "Prefer", "return=minimal")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new Exception(response.statusCode() + " " + response.body());
        }
    }

    default String findById(String id, String tableName, String idColumn) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s?select=*&%s=eq.%s".formatted(URI.create(baseUrl), tableName, idColumn, id)))
                .GET()
                .headers(
                        "apikey", apiKey,
                        "Authorization", "Bearer %s".formatted(apiKey),
                        "Content-Type", "application/json",
                        "Prefer", "return=minimal")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new Exception(response.statusCode() + " " + response.body());
        }

        return response.body();
    }

    default String findAll(String tableName) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s?select=*".formatted(baseUrl, tableName)))
                .GET()
                .headers(
                        "apikey", apiKey,
                        "Authorization", "Bearer %s".formatted(apiKey)
                )
                .build();
        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );
        if (response.statusCode() >= 400) {
            throw new RuntimeException(response.statusCode() + " " + response.body());
        }
        return response.body();
    }
}
