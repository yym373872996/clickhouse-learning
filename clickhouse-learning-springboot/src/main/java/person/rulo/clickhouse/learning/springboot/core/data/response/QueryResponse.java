package person.rulo.clickhouse.learning.springboot.core.data.response;

import person.rulo.clickhouse.learning.springboot.core.data.wrapper.result.ResultWrapper;

/**
 * @Author rulo
 * @Date 2020/11/23 16:21
 *
 * 查询返回体
 */
public class QueryResponse {

    // 结果包装器
    private ResultWrapper resultWrapper;
    // 响应代码
    private String code;
    // 响应消息
    private String message;
    // 查询耗时
    private long costTime;

    public QueryResponse() {
    }

    public QueryResponse(ResultWrapper resultWrapper, String code, String message, long costTime) {
        this.resultWrapper = resultWrapper;
        this.code = code;
        this.message = message;
        this.costTime = costTime;
    }

    public ResultWrapper getResultWrapper() {
        return resultWrapper;
    }

    public void setResultWrapper(ResultWrapper resultWrapper) {
        this.resultWrapper = resultWrapper;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }
}
