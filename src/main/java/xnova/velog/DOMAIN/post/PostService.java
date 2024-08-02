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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagService tagService;

    //게시물 저장
    @Transactional
    public PostDTO.Response savePost(PostDTO.Request postRequest) {
        // 상태 값 검증 - 상태는 저장이거나 임시 저장이어야 함
        if (postRequest.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        // 회원 정보 조회 및 설정
        Member member = memberRepository.findById(postRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("cannot find member"));

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

        //태그 목록을 해당 게시글에 저장
        List<Tag> tags = tagService.saveTagsWithPost(post, postRequest.getTags());

        //태그들을 하나씩 게시글에 저장
        tags.forEach(post::addTag);

        //레포지토리에 생성한 객체 저장 - 전체 게시글 저장
        postRepository.save(post);

        // postDTO를 저장
        return PostDTO.Response.fromEntity(post);
    }


    //조회
    //아이디를 통해 게시물을 찾아서 데이터를 전달
    @Transactional(readOnly = true)
    public PostDTO.Response findPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return PostDTO.Response.fromEntity(post);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    //조회수 업데이트
    @Transactional
    public void updateHits(Long id) {
        postRepository.updateHits(id);
    }


    //게시판 업데이트 : 기존의 내용을 불러옴(아이디로 찾기) -> 새로운 내용을 입력
    // -> 새로운 내용을 전달받아서 다시 저장
    @Transactional
    public PostDTO.Response updatePost(Long id, PostDTO.Request postRequest) {
        //기존의 게시물을 불러오기
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cannot find post"));

        //회원인지 확인 -> 그래야 나중에 member를 response 해줄 수 있음
        Member member = memberRepository.findById(postRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("cannot find member"));

        //새로 입력된 태그를 저장하고, 게시물과 함께 매핑하는 작업을
        // tagService에서 수행
        tagService.updateTagWithPost(existingPost, postRequest.getTags());

        //새롭게 수정된 내용에 맞게 객체 재설정
        Post updatedPost = Post.builder()
                .postId(existingPost.getPostId())
                .member(member)
                .hits(existingPost.getHits())
                .tags(existingPost.getTags())
                .likes(existingPost.getLikes())
                .title(postRequest.getTitle())
                .imageUrl(postRequest.getImageUrl())
                .content(postRequest.getContent())
                .status(postRequest.getStatus())
                .build();

        //내용 저장
        postRepository.save(updatedPost);

        return PostDTO.Response.fromEntity(updatedPost);
    }


    //임시저장 게시물 목록 확인
    public List<PostDTO.Response> findAllTempPosts(){
        List<Post> posts = postRepository.findByStatus("temp");
        return posts.stream()
                .map(PostDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    //저장 게시물 목록 확인
    public List<PostDTO.Response> findAllPosts(){
        List<Post> posts = postRepository.findByStatus("saved");
        return posts.stream()
                .map(PostDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    //이게 과연 필요할까?
    /*public PostDTO findTempPostById(Long id){ //임시저장 게시물 내용 받기
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cannot find post"));
        if (!"temp".equals(post.getStatus())){
            throw  new RuntimeException("Post is not in temp status");
        }
        return PostDTO.toPostDTO(post);
    }*/


    //임시저장 게시물 저장
    //update와 어떤 차이가 있는지 모르겠다 - 결국 입력받는 값 중에 status 값이 바뀌는 것인데,
    //saved로 바뀌면 임시 저장된 게시물에서 없어지는 거 아닌가?
    @Transactional
    public PostDTO.Response saveTempPost(Long id) {
        Post tempPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cannot find post"));

        Post savedPost = tempPost.toBuilder()
                .status("saved")
                .build();

        postRepository.save(savedPost);

        return PostDTO.Response.fromEntity(savedPost);
    }



    //게시물 삭제 - 태그도 함께 삭제하도록 작성
    @Transactional
    public void deletePost(Long id){
        //삭제
        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("cannot find post"));

        tagService.deleteTags(post);

        postRepository.delete(post);
    }


}
