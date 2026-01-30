package com.webdev.identity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data //tu dong tao getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //giúp bạn tạo ra một đối tượng (Object) dễ hiểu
@FieldDefaults(level = AccessLevel.PRIVATE) // mặc đinh field là private
public class IntrospectRequest {
    String token;
}
