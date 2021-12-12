package com.backend.technical.utils;

import com.backend.technical.exceptions.CustomErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.http.HttpStatus;

public final class CommonUtils {

    public static final String COMMON_DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm:ss";
    public static final String REQUEST_DATE_TIME_PATTERN = "yyyy-MM-ddHH:mm:ss";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    public static final String MANDATORY_BODY_PATTERN_MSG = "Request body is mandatory";
    public static final String MANDATORY_PARAMETER_PATTERN_MSG = "Parameter is mandatory, actual: [%s]";
    public static final String DEVICE_ID_KEY = "deviceId";
    public static final String DATA_BODY_KEY = "body.data";

    public static final String RESOURCE_ENTITY_NOT_FOUND_MSG = "Resource entity is not found";

    public static String dateToString(final Date date, final String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static boolean isBlank(final String input) {
        return input == null || input.isBlank();
    }

    public static String toJSON(final Object object) throws CustomErrorException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomErrorException("Error while parsing object to JSON");
        }
    }

    public static CustomErrorException resourceNotFoundException() {
        return new CustomErrorException(HttpStatus.NOT_FOUND, RESOURCE_ENTITY_NOT_FOUND_MSG);
    }

    public static Date now() {
        return new Date();
    }

    public static Date atBeginOfToday() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static Date atEndOfToday() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private CommonUtils() {}
}
