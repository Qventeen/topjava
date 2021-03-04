package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

public class TestTimer implements TestRule{
    private static final Logger log = LoggerFactory.getLogger("result");

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Instant start = Instant.now();
                try {
                    base.evaluate();
                }finally {
                    Instant end  = Instant.now();
                    Duration duration = Duration.between(start, end);
                    log.info("\n--> TIME OF TEST {} = {}ms", description, duration.toMillis());
                }
            }
        };
    }
}
