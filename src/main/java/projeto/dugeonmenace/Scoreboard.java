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

    public Scoreboard(String uri) {
        this.setUri(uri);
    }

    public void setUri(String uri) {
        this.uri = URI.create(uri);
    }

    public String getUri() {
        return this.uri.toString();
    }

    public void post(Score score) throws IOException, InterruptedException {
        String body = score.toJson();

        HttpRequest post = HttpRequest.newBuilder()
                .uri(this.uri)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        client.send(post,
                HttpResponse.BodyHandlers.ofString());
    }

    public List<Score> get() throws IOException, InterruptedException {
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
