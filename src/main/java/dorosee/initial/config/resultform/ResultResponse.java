package dorosee.initial.config.resultform;

import lombok.Getter;

import java.util.Map;

@Getter
public class ResultResponse<T> {
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    private T resultObject;
    private T resultList;
    private Map<String, Object> totalMap;

    // 요청에 성공한 경우
    public ResultResponse(T resultObject, T resultList) {
        this.isSuccess = ResultResponseStatus.SUCCESS.isSuccess();
        this.message = ResultResponseStatus.SUCCESS.getMessage();
        this.code = ResultResponseStatus.SUCCESS.getCode();
        this.resultObject = resultObject;
        this.resultList = resultList;
    }

    // 요청에 성공한 경우
    public ResultResponse(T resultObject, T resultList, Map<String, Object> totalMap) {
        this.isSuccess = ResultResponseStatus.SUCCESS.isSuccess();
        this.message = ResultResponseStatus.SUCCESS.getMessage();
        this.code = ResultResponseStatus.SUCCESS.getCode();
        this.resultObject = resultObject;
        this.resultList = resultList;
        this.totalMap = totalMap;
    }

    // 요청에 실패한 경우
    public ResultResponse(ResultResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}

