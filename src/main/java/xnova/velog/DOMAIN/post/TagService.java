package xnova.velog.DOMAIN.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xnova.velog.Entity.Post;
import xnova.velog.Entity.Tag;
import xnova.velog.DOMAIN.post.TagDTO;
import xnova.velog.DOMAIN.post.TagRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    @Autowired
    private final TagRepository tagRepository;

    @Transactional
    public List<Tag> saveTagsWithPost(Post post, List<TagDTO.Request> tagRequest) {
        List<Tag> tags = tagRequest.stream()
                .map(tagDTO -> Tag.builder()
                        .tagName(tagDTO.getTagName())
                        .post(post) // 빌더 패턴을 사용하여 post 필드 설정
                        .build())
                .collect(Collectors.toList());

        return tagRepository.saveAll(tags);
    }

    @Transactional
    public List<Tag> updateTagWithPost(Post post, List<TagDTO.Request> tagRequest) {
        Set<String> existingTagNames = post.getTags().stream()
                .map(Tag::getTagName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagRequest.stream()
                .map(tagDTO -> Tag.builder()
                        .tagName(tagDTO.getTagName())
                        .post(post)
                        .build())
                .filter(tag -> !existingTagNames.contains(tag.getTagName()))
                .collect(Collectors.toList());

        return tagRepository.saveAll(newTags);
    }

    @Transactional
    public void deleteTags(Post post){
        List<Tag> tags = post.getTags();
        if (tags != null && !tags.isEmpty()) {
            tagRepository.deleteAll(tags);
        }
    }
}
