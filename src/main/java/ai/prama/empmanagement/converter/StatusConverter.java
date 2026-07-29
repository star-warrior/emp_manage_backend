package ai.prama.empmanagement.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusConverter implements Converter<String, Boolean> {

    @Override
    public Boolean convert(String source) {
        if ("active".equalsIgnoreCase(source)) {
            return true;
        }
        if ("inactive".equalsIgnoreCase(source)) {
            return false;
        }

        throw new IllegalArgumentException("Invalid status: " + source);
    }
}
