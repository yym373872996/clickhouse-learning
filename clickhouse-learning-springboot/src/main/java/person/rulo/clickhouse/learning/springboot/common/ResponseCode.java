package person.rulo.clickhouse.learning.springboot.common;

import person.rulo.clickhouse.learning.springboot.core.entity.type.JavaType;

/**
 * @Author rulo
 * @Date 2021/1/2 15:34
 */
public enum ResponseCode {

    SUCCESS("200"),
    ERROR("500");

    private String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
