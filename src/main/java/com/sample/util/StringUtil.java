package com.sample.util;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class StringUtil {
    private static final String FIELD_PATTERN = "%s %s";

    public static String buildErrorMessage(Errors errors) {
        StringBuilder stringBuilder = new StringBuilder();

        List<ObjectError> objectErrors = errors.getAllErrors();
        for (ObjectError objectError : objectErrors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError)objectError;
                stringBuilder.append(String.format(FIELD_PATTERN, fieldError.getField(), fieldError.getDefaultMessage()));
            } else {
                stringBuilder.append(objectError.toString());
            }
            stringBuilder.append(",");
        }

        String tempResult = stringBuilder.toString();
        return tempResult.substring(0, tempResult.length() - 1);
    }
}
