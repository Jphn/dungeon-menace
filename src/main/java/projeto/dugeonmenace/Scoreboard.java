package projeto.dugeonmenace;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;;

public class Scoreboard {
    private URI uri;
    private HttpClient client = HttpClient.newHttpClient();

    Scoreboard(String uri) {
        this.setUri(uri);
    }

    void setUri(String uri) {
        this.uri = URI.create(uri);
    }

    String getUri() {
        return this.uri.toString();
    }

    void post(Score score) throws IOException, InterruptedException {
        String body = score.toJson();

        HttpRequest post = HttpRequest.newBuilder()
                .uri(this.uri)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        client.send(post,
                HttpResponse.BodyHandlers.ofString());
    }

    List<Score> get() throws IOException, InterruptedException {
        HttpRequest get = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(get,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<List<Score>> jacksonTypeReference = new TypeReference<List<Score>>() {
        };

        return objectMapper.readValue(response.body(), jacksonTypeReference);
    }
}
