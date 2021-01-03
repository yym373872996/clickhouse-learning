package person.rulo.clickhouse.learning.springboot.util;

import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author rulo
 * @Date 2021/1/2 15:49
 *
 * 异常工具类
 */
@Component
public class ExceptionUtil {

    /**
     * 获得完整的异常信息
     * @param e
     * @return
     */
    public String getWholeException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
