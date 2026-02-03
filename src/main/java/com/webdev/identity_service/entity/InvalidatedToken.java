package com.webdev.identity_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity //entity lưu những token đã logout
public class InvalidatedToken {
    @Id
    String id;
    Date expiryTime;//lý do giữ: khi cần remove sẽ chọn những token hết hạn để remove, có thể mỗi ngày remove 1 lần để database không bị phình
}
