package xnova.velog.DOMAIN.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xnova.velog.DOMAIN.auth.MemberRepository;
import xnova.velog.Entity.Member;
import xnova.velog.Entity.Post;
import xnova.velog.Entity.Tag;
import xnova.velog.Entity.TagMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagMappingRepository tagMappingRepository;

    @Transactional
    public PostDTO.Response savePost(PostDTO.Request postRequest) {
        // 상태 값 검증
        if (postRequest.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        //Post 객체 생성 - 요청값에 따라서 상태 데이터에 대한 값은 다를 것임
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .imageUrl(postRequest.getImageUrl())
                .content(postRequest.getContent())
                .status(postRequest.getStatus())
                .likes(0)
                .hits(0)
                .build();

        // 회원 정보 조회 및 설정
        Member member = memberRepository.findById(postRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("cannot find member"));

        Post savedPost = postRepository.save(post);

        // 태그 처리 및 저장
        List<Tag> tags = postRequest.getTags().stream()
                .map(tagDTO -> tagRepository.findById(tagDTO.getId())
                        .orElseGet(() -> tagRepository.save(new Tag(null, tagDTO.getTagName()))))
                .collect(Collectors.toList());
        tagRepository.saveAll(tags);

        //태그 배핑 저장
        List<TagMapping> tagMappings = tags.stream()
                .map(tag -> new TagMapping(savedPost, tag))
                .collect(Collectors.toList());


        // 태그 매핑 설정
        tagMappingRepository.saveAll(tagMappings);

        savedPost.setTags(tagMappings);

        // Post 저장 (최종적으로 한 번만 저장)
        return PostDTO.Response.fromEntity(savedPost);
    }


    public PostDTO.Response findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            return PostDTO.Response.fromEntity(post);
        }
        else throw new RuntimeException("Post not found");
    }

    @Transactional
    public void updateHits(Long id){ //조회수 업로드
        postRepository.updateHits(id);
    }

    /*@Transactional
    public PostDTO update(PostDTO postDTO) { //게시판 업로드
        Post post = Post.toUpdatePost(postDTO);
        postRepository.save(post);
        return findById(postDTO.getPost_id()); //왜 이렇게 리턴하는지?
    }*/

    @Transactional
    public PostDTO.Response updatePost(Long id, PostDTO.Request postRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post = Post.builder()
                .title(postRequest.getTitle())
                .imageUrl(postRequest.getImageUrl())
                .content(postRequest.getContent())
                .status(postRequest.getStatus())
                .build();

        /*List<TagMapping> existingTags = post.getTags();

        List<Tag> newTags = postRequest.getTags().stream()
                .map(tagDTO -> tagRepository.findById(tagDTO.getId())
                        .orElseGet(() -> new Tag(null, tagDTO.getTagName())))
                .collect(Collectors.toList());

        List<TagMapping> newTagMappings = newTags.stream()
                .map(tag -> new TagMapping(post, tag))
                .collect(Collectors.toList());

        tagMappingRepository.deleteAll(existingTags);
        tagMappingRepository.saveAll(newTagMappings);
        post.setTags(newTagMappings);

        return PostDTO.Response.fromEntity(postRepository.save(post));*/
        return PostDTO.Response.fromEntity(postRepository.save(post));
    }

    public List<PostDTO.Response> findAllTempPosts(){ //임시저장 게시물 목록 확인
        List<Post> posts = postRepository.findByStatus("temp");
        return posts.stream().map(PostDTO.Response::fromEntity).collect(Collectors.toList());
    }

    /*public PostDTO findTempPostById(Long id){ //임시저장 게시물 내용 받기
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cannot find post"));
        if (!"temp".equals(post.getStatus())){
            throw  new RuntimeException("Post is not in temp status");
        }
        return PostDTO.toPostDTO(post);
    }*/

    @Transactional
    public PostDTO.Response saveTempPost(Long id) { //임시저장 게시물 저장
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cannot find post"));
        post.setStatus("saved");
        Post savedPost = postRepository.save(post);
        return PostDTO.Response.fromEntity(savedPost);
    }

    public void delete(Long id){ //삭제
        postRepository.deleteById(id);
    }
}
