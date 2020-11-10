package ru.education.model.implementation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.education.model.Formatter;

@Primary
@Component("barFormatter")
public class BarFormatter implements Formatter {
    @Override
    public String format() {
        return "bar";
    }
}
