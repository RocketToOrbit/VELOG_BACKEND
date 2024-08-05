package xnova.velog.DOMAIN.board_pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.DOMAIN.board_pagination.utils.PostConverter;

@Service
public class PostService<T> {
    //PostService는 제네릭 타입을 사용하여 다양한 엔티티에 대해 재사용할 수 있도록 합니다.

    @Autowired
    private PostRepository postRepository; // 게시물 리포지토리 주입

    // 최신순으로 페이징된 엔티티 목록을 가져오는 메서드
    public PostResponseDTO<PostResponseDTO.PageResponseDTO<T>> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<T> postPage = (Page<T>) postRepository.findAllByOrderByCreateAtDesc(pageable);
        return PostConverter.toPostResponseDTO(postPage);
    }
}
