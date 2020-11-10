package ru.education.model.implementation;

import org.springframework.stereotype.Component;
import ru.education.model.Formatter;

@Component("fooFormatter")
public class FooFormatter implements Formatter {
    @Override
    public String format() {
        return "foo";
    }
}
