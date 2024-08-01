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

    /*@GetMapping("/update/{id}") //수정된 게시판 보기
    public String updatedPost(@PathVariable("id") Long id, Model model){
        PostDTO postDTO = postService.findById(id); //수정된 정보를 DTO에 담아옴
        model.addAttribute("post", postDTO);
        return "modifying";
    }*/
    //필요 없을듯 함

    @PostMapping("/update/{id}") //게시판 수정하기
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDTO.Request postDTO) throws IOException {
        PostDTO.Response updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    /*@GetMapping("/tempPosts") //임시저장된 게시물 목록 확인하기
    public String getTempPosts(Model model) {
        List<PostDTO> tempPosts = postService.findAllTempPosts();
        model.addAttribute("tempPosts", tempPosts);
        return "tempPostList";
    }

    @GetMapping("/tempPost/{id}")
    public String getTempPostById(@PathVariable Long id, Model model) {
        PostDTO postDTO = postService.findById(id);
        model.addAttribute("post", postDTO);
        return "tempDetail";
    }

    @PostMapping("/saveTempPost") //임시저장된 게시물 저장하기
    public String tempPostSave(@RequestBody Long id, Model model) {
        PostDTO savedTempPost = postService.saveTempPost(id);
        model.addAttribute("post", savedTempPost);
        return "detail";
    }

    @GetMapping("/delete/{id}") //게시판 삭제
    public String deletePost(@PathVariable("id") Long id){
        postService.delete(id);
        return "redirect:/post";
    }*/



}
