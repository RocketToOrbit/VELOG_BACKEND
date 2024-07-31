package xnova.velog.DOMAIN.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xnova.velog.DOMAIN.auth.MemberRepository;
import xnova.velog.Entity.Member;
import xnova.velog.Entity.Post;
import xnova.velog.Entity.Tag;

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
    private TagService tagService;

    @Transactional
    public PostDTO.Response savePost(PostDTO.Request postRequest) {
        // 상태 값 검증
        if (postRequest.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        // 회원 정보 조회 및 설정 - 없어도 무방할듯
        Member member = memberRepository.findById(postRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("cannot find member"));

        //태그를 저장
        //List<Tag> tags = tagService.saveTags(postRequest.getTags());

        //post 객체를 생성
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .imageUrl(postRequest.getImageUrl())
                .content(postRequest.getContent())
                .status(postRequest.getStatus())
                .likes(0)
                .hits(0)
                .member(member)
                .build();
        //postRepository.save(post);

        List<Tag> tags = tagService.saveTagsWithPost(post, postRequest.getTags());
        tags.forEach(post::addTag);


        //레포지토리에 생성한 객체 저장
        postRepository.save(post);

        // postDTO를 저장
        return PostDTO.Response.fromEntity(post);
    }


    @Transactional(readOnly = true)
    public PostDTO.Response findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return PostDTO.Response.fromEntity(post);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    @Transactional
    public void updateHits(Long id) {
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
        Post savedPost = postRepository.save(post);
        return PostDTO.Response.fromEntity(savedPost);
    }

    public void delete(Long id){ //삭제
        postRepository.deleteById(id);
    }
}
