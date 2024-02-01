package structures;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record RequestData(AppCommand command, List<String> parameters) implements Serializable {}
