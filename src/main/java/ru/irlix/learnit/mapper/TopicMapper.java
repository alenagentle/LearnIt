package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.irlix.learnit.dto.request.TopicRequest;
import ru.irlix.learnit.dto.response.LinkResponse;
import ru.irlix.learnit.dto.response.topic.TopicFullResponse;
import ru.irlix.learnit.dto.response.topic.TopicResponse;
import ru.irlix.learnit.entity.Direction;
import ru.irlix.learnit.entity.Link;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.entity.Wiki;
import ru.irlix.learnit.exception.NoRelatedDirectionException;
import ru.irlix.learnit.service.helper.DirectionHelper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TestMapper.class, ImageMapper.class})
public abstract class TopicMapper {

    @Autowired
    private DirectionHelper directionHelper;

    public abstract Topic mapToEntity(TopicRequest request);

    @Mapping(target = "wikiText", source = "wiki.text")
    @Mapping(target = "links", ignore = true)
    public abstract TopicFullResponse mapToFullResponse(Topic topic);

    public abstract List<TopicResponse> mapToResponseList(List<Topic> topic);

    public Page<TopicResponse> mapToResponsePage(Page<Topic> topics) {
        List<TopicResponse> content = mapToResponseList(topics.getContent());
        return new PageImpl<>(content, topics.getPageable(), topics.getTotalElements());
    }

    @AfterMapping
    protected void map(@MappingTarget Topic topic, TopicRequest request) {
        List<Direction> directions = directionHelper.findDirectionsByIds(request.getDirectionIdSet());
        if (directions.isEmpty()) {
            throw new NoRelatedDirectionException();
        }
        topic.setDirections(directions);
        Wiki wiki = new Wiki();
        wiki.setText(request.getWikiText());
        if (request.getLinks() != null) {
            List<Link> links = new ArrayList<>();
            request.getLinks().forEach(text -> setLinkList(wiki, links, text));
            wiki.setLinks(links);
        }
        topic.setWiki(wiki);
    }

    @AfterMapping
    protected void map(@MappingTarget TopicResponse response, Topic topic) {
        int testsCount = topic.getTests().size();
        response.setTestCount(testsCount);
        long finishedTestsCount = topic.getTests()
                .stream()
                .map(test -> test.getResults()
                        .stream()
                        .filter(result -> Boolean.TRUE.equals(result.getIsFinished())))
                .count();
        response.setFinishedTestsCount(finishedTestsCount);
        if (testsCount <= 0) {
            testsCount = 1;
        }
        response.setProgress(finishedTestsCount / testsCount * 100);
    }

    @AfterMapping
    protected void map(@MappingTarget TopicFullResponse response, Topic topic) {
        List<Long> directionsId = topic.getDirections().stream()
                .map(Direction::getId)
                .toList();
        response.setDirectionsId(directionsId);
        List<LinkResponse> linkResponses = new ArrayList<>();
        topic.getWiki().getLinks().forEach(link -> setLinks(linkResponses, link));
        response.setLinks(linkResponses);
    }

    private void setLinks(List<LinkResponse> linkResponses, Link link) {
        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setText(link.getText());
        linkResponse.setWikiId(link.getWiki().getId());
        linkResponse.setId(link.getId());
        linkResponses.add(linkResponse);
    }

    private void setLinkList(Wiki wiki, List<Link> links, String text) {
        Link link = new Link();
        link.setText(text);
        link.setWiki(wiki);
        links.add(link);
    }
}
