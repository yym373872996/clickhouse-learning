package person.rulo.clickhouse.learning.springboot.util;

import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.core.entity.type.JavaType;

import java.math.BigInteger;

/**
 * @Author rulo
 * @Date 2020/11/30 17:18
 */
@Component
public class CalculatorUtil {

    public Object sum(Object a, Object b, JavaType javaType) {
        switch (javaType) {
            case SHORT:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (short) a + (short) b;
            case INTEGER:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (int) a + (int) b;
            case LONG:
                a = a == null ? 0L : a;
                b = b == null ? 0L : b;
                return (long) a + (long) b;
            case BIGINTEGER:
                a = a == null ? BigInteger.valueOf(0) : a;
                b = b == null ? BigInteger.valueOf(0) : b;
                return ((BigInteger) a).add((BigInteger) b);
            case FLOAT:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (float) a + (float) b;
            case DOUBLE:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (double) a + (double) b;
        }
        return null;
    }

    public Object min(Object a, Object b, JavaType javaType) {
        switch (javaType) {
            case SHORT:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return Math.min((short) a, (short) b);
            case INTEGER:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return Math.min((int) a, (int) b);
            case LONG:
                a = a == null ? 0L : a;
                b = b == null ? 0L : b;
                return Math.min((long) a, (long) b);
            case BIGINTEGER:
                a = a == null ? BigInteger.valueOf(0) : a;
                b = b == null ? BigInteger.valueOf(0) : b;
                return ((BigInteger) a).min((BigInteger) b);
            case FLOAT:
                a = a == null ? 0F : a;
                b = b == null ? 0F : b;
                return Math.min((float) a, (float) b);
            case DOUBLE:
                a = a == null ? 0D : a;
                b = b == null ? 0D : b;
                return Math.min((double) a, (double) b);
        }
        return null;
    }

    public Object max(Object a, Object b, JavaType javaType) {
        switch (javaType) {
            case SHORT:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return Math.max((short) a, (short) b);
            case INTEGER:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return Math.max((int) a, (int) b);
            case LONG:
                a = a == null ? 0L : a;
                b = b == null ? 0L : b;
                return Math.max((long) a, (long) b);
            case BIGINTEGER:
                a = a == null ? BigInteger.valueOf(0) : a;
                b = b == null ? BigInteger.valueOf(0) : b;
                return ((BigInteger) a).max((BigInteger) b);
            case FLOAT:
                a = a == null ? 0F : a;
                b = b == null ? 0F : b;
                return Math.max((float) a, (float) b);
            case DOUBLE:
                a = a == null ? 0D : a;
                b = b == null ? 0D : b;
                return Math.max((double) a, (double) b);
        }
        return null;
    }

    public Object divide(Object a, Object b, JavaType javaType) {
        switch (javaType) {
            case SHORT:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (short) a / (short) b;
            case INTEGER:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (int) a / (int) b;
            case LONG:
                a = a == null ? 0L : a;
                b = b == null ? 0L : b;
                return (long) a / (long) b;
            case BIGINTEGER:
                a = a == null ? BigInteger.valueOf(0) : a;
                b = b == null ? BigInteger.valueOf(0) : b;
                return ((BigInteger) a).divide((BigInteger) b);
            case FLOAT:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (float) a / (float) b;
            case DOUBLE:
                a = a == null ? 0 : a;
                b = b == null ? 0 : b;
                return (double) a / (double) b;
        }
        return null;
    }

}
