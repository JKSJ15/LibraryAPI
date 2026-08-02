package library.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshRequestDto(@Schema(
        description = "JWT refresh token to be sent in the endpoin Refresh",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20ifQ.signature"
    ) String refreshToken) {

}
