package xnova.velog.DOMAIN.board_pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.DOMAIN.board_pagination.utils.PostConverter;
import xnova.velog.Entity.Post;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostResponseDTO<PostResponseDTO.PageResponseDTO<Post>> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findAll(pageable);

        return PostConverter.toPostResponseDTO(postPage);
    }
}
