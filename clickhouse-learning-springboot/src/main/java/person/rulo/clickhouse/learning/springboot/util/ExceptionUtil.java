package person.rulo.clickhouse.learning.springboot.util;

import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author rulo
 * @Date 2021/1/2 15:49
 */
@Component
public class ExceptionUtil {
    public String getWholeException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
