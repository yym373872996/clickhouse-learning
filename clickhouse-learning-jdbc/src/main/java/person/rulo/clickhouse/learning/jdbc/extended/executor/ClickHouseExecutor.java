package person.rulo.clickhouse.learning.jdbc.extended.executor;

import person.rulo.clickhouse.learning.jdbc.extended.util.ClickHouseConnUtil;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.domain.ClickHouseFormat;
import ru.yandex.clickhouse.util.ClickHouseRowBinaryStream;
import ru.yandex.clickhouse.util.ClickHouseStreamCallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * @Author rulo
 * @Date 2020/7/9 14:32
 */
public class ClickHouseExecutor {

    ClickHouseConnection connection;
    ClickHouseStatement statement;

    public void insertByCallback() {
        try {
            connection = ClickHouseConnUtil.getInstance().getConnection();
            statement = connection.createStatement();
            Long startTime = System.currentTimeMillis();
            statement
                    .write()
                    .send("INSERT INTO student", new ClickHouseStreamCallback() {
                        public void writeTo(ClickHouseRowBinaryStream stream) throws IOException {
                            for(int i = 1; i <= 1000000; i++) {
                                stream.writeInt64(i);
                                stream.writeString("rua" + i);
                                if (i % 10000 == 0) {
                                    System.out.println("batch inserted 10000 records");
                                }
                            }
                        }
                    }, ClickHouseFormat.RowBinary);
            Long endTime = System.currentTimeMillis();
            System.out.println("耗时：" + (endTime - startTime) + "ms");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ClickHouseConnUtil.getInstance().closeResource(statement, connection);
        }
    }

    public void insertConfigurable() {
        try {
            String student = "{\"id\":\"1\", \"name\":\"rita\"}, {\"id\":\"2\"}";
            InputStream inputStream = new ByteArrayInputStream(student.getBytes());
            connection = ClickHouseConnUtil.getInstance().getConnection();
            statement = connection.createStatement();
            Long startTime = System.currentTimeMillis();
            statement
                    .write()
                    .sql("INSERT INTO student")
                    .data(inputStream, ClickHouseFormat.JSONEachRow)
                    .send();
            Long endTime = System.currentTimeMillis();
            System.out.println("耗时：" + (endTime - startTime) + "ms");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ClickHouseConnUtil.getInstance().closeResource(statement, connection);
        }
    }

}
