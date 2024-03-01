package swyg.vitalroutes.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
// 시간 정보를 다룰 수 있는 클래스
// 게시글 업로드 시간, 댓글 단 시간 등 시간에 대한 정보를 따로 관리하기 용이
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp // 생성되었을때 시간
    @Column(updatable = false) // 수정시에는 관여 못하게
    private LocalDateTime createdTime;

    @UpdateTimestamp // 업데이트 되었을때 시간
    @Column(insertable = false) // 입력시에는 관여 못하게
    private LocalDateTime updatedTime;
}
