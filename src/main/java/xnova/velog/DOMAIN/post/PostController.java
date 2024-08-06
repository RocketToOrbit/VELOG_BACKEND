package xnova.velog.DOMAIN.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/save") //게시물 저장하기
    public ResponseEntity<?> savePost(@RequestBody PostDTO.Request postDTO) throws IOException {
        return ResponseEntity.ok().body(postService.savePost(postDTO));
    }

    @GetMapping("/{id}") //게시판 상세 조회
    public ResponseEntity<PostDTO.Response> findById(@PathVariable Long id) {
        postService.updateHits(id); //조회수 업데이트
        PostDTO.Response postDTO = postService.findPost(id);
        return ResponseEntity.ok(postDTO);
    }

    @PostMapping("/update/{id}") //게시판 수정하기
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDTO.Request postDTO) throws IOException {
        PostDTO.Response updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }


    @GetMapping("/tempPosts") //임시저장된 게시물 목록 확인하기
    public ResponseEntity<?> getTempPosts() {
        List<PostDTO.Response> tempPosts = postService.findAllTempPosts();
        return ResponseEntity.ok(tempPosts);
    }

    @GetMapping("/savedPosts") //저장된 게시물 목록 확인하기
    public ResponseEntity<?> getPosts() {
        List<PostDTO.Response> savedPosts = postService.findAllPosts();
        return ResponseEntity.ok(savedPosts);
    }


    //임시저장된 게시물 저장하기
    //그러나 그냥 수정해서 상태를 변경해도 똑같은 결과가 나옴
    @PostMapping("/saveTempPost/{id}")
    public ResponseEntity<?> tempPostSave(@PathVariable Long id, @RequestBody PostDTO.Request postDTO) throws IOException {
        //PostDTO.Response savedPost = postService.saveTempPost(id);
        PostDTO.Response savedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(savedPost);
    }

    @DeleteMapping("/delete/{id}") //게시판 삭제
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
