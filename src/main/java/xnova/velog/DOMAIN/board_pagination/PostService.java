package xnova.velog.DOMAIN.board_pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.DOMAIN.board_pagination.PostRepository;
import xnova.velog.DOMAIN.board_pagination.utils.PostConverter;
import xnova.velog.Entity.Post;

import java.time.ZoneOffset;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostResponseDTO<PostResponseDTO.PageResponseDTO<PostResponseDTO.PostSummaryDTO>> getPosts(Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("createAt").descending());

        List<Post> posts;
        long totalElements = postRepository.count(); // 총 요소 수 계산
        boolean isSorted = pageable.getSort().isSorted(); // 정렬 여부 계산

        if (cursor == null) {
            // 커서가 null이면 최신 게시물을 불러오는 쿼리를 실행
            posts = postRepository.findAllByOrderByCreateAtDesc(pageable);
        } else {
            // 커서가 존재하면 해당 커서 기준으로 이전 게시물들을 불러오는 쿼리 실행
            posts = postRepository.findAllByOrderByCreateAtDesc(cursor, pageable);
        }

        // 다음 커서를 계산
        Long nextCursor = posts.isEmpty() ? null : posts.get(posts.size() - 1).getCreateAt().toEpochSecond(ZoneOffset.UTC);

        return PostConverter.toPostResponseDTO(posts, nextCursor, totalElements, isSorted);
    }
}
