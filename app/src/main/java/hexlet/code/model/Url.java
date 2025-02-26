package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Url {

    private Long id;
    @ToString.Include
    private String name;
    private Timestamp createdAt;
    @ToString.Exclude
    private List<UrlCheck> checks;

    public Url(String name) {
        this.name = name;
        this.checks = new LinkedList<>();
    }
}
