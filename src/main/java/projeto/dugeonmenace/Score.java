package projeto.dugeonmenace;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Score {

    private String username;
    private int score;

    @JsonCreator
    Score(@JsonProperty("score") int score, @JsonProperty("username") String username) {
        this.score = score;
        this.username = username;
    }

    String getUsername() {
        return this.username;
    }

    Integer getScore() {
        return this.score;
    }

    String toJson() {
        return String.format("{\"username\": \"%s\", \"score\": %d}", this.username, this.score);
    }
}