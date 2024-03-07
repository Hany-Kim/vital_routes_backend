package swyg.vitalroutes.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/*
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
//@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@RequiredArgsConstructor
*/
@Data
@Schema(description = "Challenge 생성 시 사용")
public class ChallengeSaveFormDTO {
    @Schema(description = "Challenge 작성자명을 담는 변수")
    private String challengeWriter; // 작성자
    @Schema(description = "Challenge 제목을 담는 변수")
    private String challengeTitle; // 제목
    @Schema(description = "Challenge 본문 내용을 담는 변수")
    private String challengeContents; // 본문
    @Schema(description = "Challenge 등록시 사용한 이동 수단을 담는 변수. ex. 걷기(0), 자전거(1) ")
    private String challengeTransportation; // 이동수단

    @Schema(description = "Challenge 대표사진 파일을 담는 변수")
    private MultipartFile titleImage; // 실제 파일을 담아줄 수 있는 역할 (대표사진 저장)

    @Schema(description = "Challenge 이동 경로 사진 중 출발지 사진 파일을 담는 변수")
    private MultipartFile startingPositionImage; // 출발지 이미지 파일 담는용도
    @Schema(description = "Challenge 이동 경로 사진 중 도착지 사진 파일을 담는 변수")
    private MultipartFile destinationImage; // 도착지 이미지 파일 담는용도
    @Schema(description = "Challenge 이동 경로 사진 중 경유지1 사진 파일을 담는 변수")
    private MultipartFile stopOverImage1; // 경유지1 이미지 파일 담는용도
    @Schema(description = "Challenge 이동 경로 사진 중 경유지2 사진 파일을 담는 변수")
    private MultipartFile stopOverImage2; // 경유지2 이미지 파일 담는용도
    @Schema(description = "Challenge 이동 경로 사진 중 경유지3 사진 파일을 담는 변수")
    private MultipartFile stopOverImage3; // 경유지3 이미지 파일 담는용도

    /*
    public ChallengeSaveFormDTO(String challengeWriter,
                                String challengeTitle,
                                String challengeContents,
                                String challengeTransportation,
                                MultipartFile titleImage,
                                MultipartFile startingPositionImage,
                                MultipartFile destinationImage,
                                MultipartFile stopOverImage1,
                                MultipartFile stopOverImage2,
                                MultipartFile stopOverImage3) {
        this.challengeWriter = challengeWriter;
        this.challengeTitle = challengeTitle;
        this.challengeContents = challengeContents;
        this.challengeTransportation = challengeTransportation;
        this.titleImage = titleImage;
        this.startingPositionImage = startingPositionImage;
        this.destinationImage = destinationImage;
        this.stopOverImage1 = stopOverImage1;
        this.stopOverImage2 = stopOverImage2;
        this.stopOverImage3 = stopOverImage3;
        if(!stopOverImage1.isEmpty()){
        }
        if(!stopOverImage2.isEmpty()){
        }
        if(!stopOverImage3.isEmpty()){
        }
    }
     */
}