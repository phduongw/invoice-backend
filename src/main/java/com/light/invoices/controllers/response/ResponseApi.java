package com.light.invoices.controllers.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseApi<T> {
    HttpStatus status;
    T data;

    public ResponseApi(int statusCode, String message) {
        this.status = HttpStatus.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
        this.data = null;
    }

    public ResponseApi<T> ok(T data) {
        this.data = data;
        this.status = HttpStatus.builder()
                .statusCode(org.springframework.http.HttpStatus.OK.value())
                .message("Success")
                .build();

        return this;
    }
}
