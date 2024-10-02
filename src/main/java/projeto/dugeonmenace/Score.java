package projeto.dugeonmenace;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Score {

    private String username;
    private long score;

    @JsonCreator
    public Score(@JsonProperty("score") long score, @JsonProperty("username") String username) {
        this.score = score;
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public long getScore() {
        return this.score;
    }

    public String toJson() {
        return String.format("{\"username\": \"%s\", \"score\": %d}", this.username, this.score);
    }
}