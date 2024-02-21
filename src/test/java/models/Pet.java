package models;

import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Pet {
    private int id;
    private Category category = new Category();
    private String name;
    private List<String> photoUrls = new ArrayList<String>();
    private List<Tag> tags = new ArrayList<>();
    private Status status;
}
