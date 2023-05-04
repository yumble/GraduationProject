package project.graduation.config.resultform;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum ResultResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, 200, "요청에 성공하였습니다."),
    NO_CONTENT(true, 204, "No Content"),
    /**
     * 4xx
     */
    // COMMON
    BAD_REQUEST(false, 400, "Bad Request"),
    REQUEST_ERROR(false, 400, "입력값을 확인해주세요."),
    UNAUTHORIZED(false, 401, "Unauthorized"),
    FORBIDDEN(false, 403, "Forbidden"),
    NOT_FOUND(false, 404, "Not Found"),
    METHOD_NOT_ALLOWED(false, 405,"Method Not Allowed"),
    NOT_ACCEPTABLE(false, 406, "Not Acceptable"),


    /**
     * 400
     */
    EXIST_USER(false, 400, "해당 유저가 이미 존재합니다."),
    NOT_FOUND_USER(false, 400, "해당 유저 정보가 없습니다"),

    /**
     * 403
     */
    EXCEPTION_TOKEN_EXPIRED(false, 403, "만료된 토큰입니다."),
    EXCEPTION_TOKEN_ERROR(false, 403, "토큰 에러입니다."),

    /**
     * 5xx
     */
    DATABASE_ERROR(false, 500, "데이터베이스 연결에 실패하였습니다."),
    UPLOAD_ERROR(false, 500, "업로드에 실패하였습니다."),
    SERVER_ERROR(false, 500, "서버와의 연결에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private ResultResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

