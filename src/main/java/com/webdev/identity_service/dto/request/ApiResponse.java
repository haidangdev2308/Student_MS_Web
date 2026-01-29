package com.webdev.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

//chuan hoa api res
@Data //tu dong tao getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //giúp bạn tạo ra một đối tượng (Object) dễ hiểu
@FieldDefaults(level = AccessLevel.PRIVATE) // mặc đinh field là private
@JsonInclude(JsonInclude.Include.NON_NULL) //cai nao null thi khong kem vao json
public class ApiResponse <T> {
     int code = 1000; //1000 mac dinh thanh cong
     String message;
     T result;
}
