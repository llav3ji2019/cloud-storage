package structures;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record User(String username, String password) {
    @Override
    public String toString() {
        return username + " " + password;
    }

    public List<String> toStringArray() {
        return Arrays.asList(toString().split(" "));
    }
}
