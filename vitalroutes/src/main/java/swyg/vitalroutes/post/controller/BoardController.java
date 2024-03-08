package swyg.vitalroutes.post.controller;

import com.drew.imaging.ImageProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swyg.vitalroutes.common.response.ApiResponseDTO;
import swyg.vitalroutes.member.dto.MemberNicknameDTO;
import swyg.vitalroutes.post.dto.BoardDTO;
import swyg.vitalroutes.post.dto.ChallengeSaveFormDTO;
import swyg.vitalroutes.post.service.BoardService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static swyg.vitalroutes.common.response.ResponseType.SUCCESS;

@Tag(name = "Challenge API Controller", description = "Challenge 생성, 조회, 수정, 삭제 기능 제공")
//@Controller
@RestController
@RequiredArgsConstructor // 생성자 주입방식으로 의존성 주입받음 => service클래스 호출
@RequestMapping("/board") // 부모 주소 자동 입력
public class BoardController {
    private final BoardService boardService; // 생성자 주입방식으로 의존성 주입받음
    @GetMapping("/save") // 자식 주소 매핑
    public String saveForm() {
        System.out.println("\n============\nsave.html로 이동\n============\n");
        return "save"; // save.html 반환 (게시글 저장 페이지)
    }

    @Operation(summary = "새로운 챌린지 생성", description = "새로운 챌린지를 생성(등록) 할 수 있다.")
    @ApiResponse(responseCode = "200", description = "챌린지 생성(등록) 완료")
    @PostMapping("/save") // 클라이언트로부터 post로 /save주소로 요청을 받음
    public ApiResponseDTO<?> save(ChallengeSaveFormDTO challengeSaveFormDTO) throws ImageProcessingException, IOException, URISyntaxException {
    //public String save(@ModelAttribute BoardDTO boardDTO) throws IOException, ImageProcessingException { // html에서 Controller로 전달해줄 때, 가장 간단한 방법은 @RequestParam() 방법이 있다. 여기서는 대신 @ModelAttribute 사용
        BoardDTO boardDTO = toTransformBoardDTO(challengeSaveFormDTO); // challengeSaveForm -> boardDTO
        System.out.println("\n============\nindex.html로 이동\n============\n");
        System.out.println("boardDTO = " + boardDTO); // 들어온 값 확인
        boardService.save(boardDTO);
        challengeSaveFormDTO = toTransformChallengeSaveFormDTO(boardDTO);
        return new ApiResponseDTO<>(OK, SUCCESS, "Challenge가 생성되었습니다.", null);
        //return "index";
    }

    private ChallengeSaveFormDTO toTransformChallengeSaveFormDTO(BoardDTO boardDTO) {
        ChallengeSaveFormDTO challengeSaveFormDTO = new ChallengeSaveFormDTO();
        challengeSaveFormDTO.setChallengeWriter(boardDTO.getBoardWriter());
        challengeSaveFormDTO.setChallengeTitle(boardDTO.getBoardTitle());
        challengeSaveFormDTO.setChallengeContents(boardDTO.getBoardContents());
        challengeSaveFormDTO.setChallengeTransportation(boardDTO.getBoardTransportation());
        challengeSaveFormDTO.setTitleImage(boardDTO.getTitleImage());
        challengeSaveFormDTO.setStartingPositionImage(boardDTO.getStartingPositionImage());
        challengeSaveFormDTO.setDestinationImage(boardDTO.getDestinationImage());
        if(boardDTO.getStopOverImage1() != null) {
            challengeSaveFormDTO.setStopOverImage1(boardDTO.getStopOverImage1());
        }
        if(boardDTO.getStopOverImage2() != null) {
            challengeSaveFormDTO.setStopOverImage2(boardDTO.getStopOverImage2());
        }
        if(boardDTO.getStopOverImage3() != null) {
            challengeSaveFormDTO.setStopOverImage3(boardDTO.getStopOverImage3());
        }
        return challengeSaveFormDTO;
    }

    private BoardDTO toTransformBoardDTO(ChallengeSaveFormDTO challengeSaveFormDTO) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardWriter(challengeSaveFormDTO.getChallengeWriter());
        boardDTO.setBoardTitle(challengeSaveFormDTO.getChallengeTitle());
        boardDTO.setBoardContents(challengeSaveFormDTO.getChallengeContents());
        boardDTO.setBoardTransportation(challengeSaveFormDTO.getChallengeTransportation());
        boardDTO.setTitleImage(challengeSaveFormDTO.getTitleImage());
        boardDTO.setStartingPositionImage(challengeSaveFormDTO.getStartingPositionImage());
        boardDTO.setDestinationImage(challengeSaveFormDTO.getDestinationImage());
        if(challengeSaveFormDTO.getStopOverImage1() != null) {
            boardDTO.setStopOverImage1(challengeSaveFormDTO.getStopOverImage1());
        }
        if(challengeSaveFormDTO.getStopOverImage2() != null) {
            boardDTO.setStopOverImage2(challengeSaveFormDTO.getStopOverImage2());
        }
        if(challengeSaveFormDTO.getStopOverImage3() != null) {
            boardDTO.setStopOverImage3(challengeSaveFormDTO.getStopOverImage3());
        }
        return boardDTO;
    }

    @GetMapping("/")
    public String findAll(Model model) { // 게시글 목록을 db로 부터 가져와야한다. DB로부터 data를 가져올때는 Model 객체를 사용한다.
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        //System.out.println("\n============\nlist.html로 이동\n============\n");
        List<BoardDTO> boardDTOList = boardService.findAll(); // 게시글 "목록"을 가져온다.
        model.addAttribute("boardList", boardDTOList); // 가져온 객체를 model객체에 담아둔다.
        return "list"; // list.html로 간다.
    }

    @GetMapping("/{id}")
    /*
    public String findById(@PathVariable("id") Long id, Model model, // 경로상의 값을 가져올때는 @PathVariable를 사용
                           @PageableDefault(page=1) Pageable pageable) {
     */
    public BoardDTO findById(@PathVariable("id") Long id, Model model, // 경로상의 값을 가져올때는 @PathVariable를 사용
                            @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        System.out.println("\n============\ndetail.html로 이동\n============\n");
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        //return "detail";
        return boardDTO;
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        /*
            게시글의 정보를 Update.html에 보여줄 목적
         */
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        /*
            수정 후 수정이 반영된 상세페이지를 보여줌
            (목록을 보여주어도 됨)
         */
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
        //return "redirect:/board/" + boardDTO.getId(); // 수정 후 조회수 업데이트에 영향을 받을 수 있다.
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        /*
            삭제 후 게시글 목록이 나타남
         */
        boardService.delete(id);
        return "redirect:/board/";
    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(
            @PageableDefault(page = 1)Pageable pageable,
            Model model) {// Pageable 라이브러리 사용시 스프링에 내장된 라이브러리 사용할것
        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);

        // page 갯수가 20개
        // 현재 사용자가 3페이지를 보고 있다면
        // 시스템 별로 다르겠지만
        // 1 2 3 4 5 페이지 중 3페이지에 대한 css가 다르게 보인다.
        // 지금 프로젝트에서는 보여지는 페이지 갯수를 3개만 보여줄 예정이다.
        // 총 페이지 갯수가 8 개라면
        // 1 2 3 || 7 8 까지 보여주면 된다. 9페이지는 보여줄 필요없다.
        int blockLimit = 3; // 보여지는 페이지 갯수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        // 현재 사용자가 1 2 3페이지 중에 있다면 starting page는 1 값부터 준다.
        // (소숫점 올림(현재 사용자가 요청한 페이지 / 보여지는 페이지 갯수) - 1) * 보여지는 페이지 갯수 + 1;
        //int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages(); // 3 6 9 12 ~~
        int endPage = Math.min((startPage + blockLimit - 1), boardList.getTotalPages()); // 3 6 9 12 ~~
        // 실제 페이지 갯수가 endPage갯수보다 작은값을 가지고 있다면 endPage값이 아닌 실제 페이지 갯수를 저장

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
