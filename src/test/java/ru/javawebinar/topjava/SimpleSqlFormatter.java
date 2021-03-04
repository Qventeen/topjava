package ru.javawebinar.topjava;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

public class SimpleSqlFormatter implements MessageFormattingStrategy {
    //First incorrect format for replace "), (\n            " --> "),\n        ("
    private final String FIRST_REPLACEMENT_REGEX = "\\),\\s\\(\\n\\s{12}";
    private static final String FIRST_CORRECTION = "),\n        (";

    //Second incorrect format for replace "\n         )" --> ")"
    private final String SECOND_REPLACEMENT_REGEX = "\\n\\s{8}\\)";
    private final String SECOND_CORRECTION = ")";
    private static final Formatter HIBERNATE_SQL_FORMATTER = new BasicFormatterImpl();

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        if (sql.isEmpty()) {
            return "";
        }
        String resultSql = HIBERNATE_SQL_FORMATTER.format(sql);

        String batch = "batch".equals(category) ? " add to batch " : "";
        return String.format("Hibernate: %s %s\n    {elapsed: %dms}%n", batch, correctionSqlString(resultSql), elapsed);
    }

    private String correctionSqlString(String sql){
        String resultSql = sql.replaceAll(FIRST_REPLACEMENT_REGEX, FIRST_CORRECTION);
        resultSql = resultSql.replaceAll(SECOND_REPLACEMENT_REGEX, SECOND_CORRECTION);
        return resultSql;
    }
}
