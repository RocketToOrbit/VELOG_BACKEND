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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    @Autowired
    private final TagRepository tagRepository;

    /*@Transactional
    public List<Tag> saveTags(List<TagDTO.Request> tagRequest) { // 새로 추가된 메서드
        List<Tag> tags = tagRequest.stream()
                .map(tagDTO -> new Tag(null, tagDTO.getTagName()))
                .collect(Collectors.toList());

        return tagRepository.saveAll(tags);
    }*/

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
}
