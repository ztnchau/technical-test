package com.backend.technical.utils;

import com.backend.technical.exceptions.CustomErrorException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.http.HttpStatus;

@Slf4j
public final class CommonUtils {

    public static final String COMMON_DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm:ss";
    public static final String REQUEST_DATE_TIME_PATTERN = "yyyy-MM-dd";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    public static final String MANDATORY_BODY_PATTERN_MSG = "Request body is mandatory";
    public static final String MANDATORY_PARAMETER_PATTERN_MSG = "Parameter is mandatory, actual: [%s]";
    public static final String BODY_KEY = "body";
    public static final String DEVICE_ID_KEY = "deviceId";
    public static final String DATA_BODY_KEY = "body.data";

    private static final String RESOURCE_ENTITY_NOT_FOUND_MSG = "Resource entity is not found";

    public static String dateToString(final Date date, final String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static boolean isBlank(final String input) {
        return input == null || input.isBlank();
    }

    public static CustomErrorException resourceNotFoundException() {
        return new CustomErrorException(HttpStatus.NOT_FOUND, RESOURCE_ENTITY_NOT_FOUND_MSG);
    }

    private CommonUtils() {}
}
