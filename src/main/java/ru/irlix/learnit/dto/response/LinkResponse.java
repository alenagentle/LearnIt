package ru.irlix.learnit.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkResponse {
    private Long id;

    private String text;

    private Long wikiId;
}
