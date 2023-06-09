package project.graduation.config.basestatus;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * Basic HTTP StatusCode
     */
    FORBIDDEN(false, 403, "Forbidden"),
    NOT_FOUND(false, 404, "Not Found"),

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */


    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    NOT_FOUND_JWT_TOKEN(false, 2001, "토큰을 발견할 수 없습니다.."),
    EXPIRED_TOKEN(false, 2002, "유효하지 않은 토큰입니다."),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),
    FAIL_TO_ROLE_CHECK(false, 2004, "권한이 없는 유저의 접근입니다."),

    /**
     * 3000 : Response 오류
     */

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.");

    //[PATCH] /users/{userIdx}

    //Post /posts

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

