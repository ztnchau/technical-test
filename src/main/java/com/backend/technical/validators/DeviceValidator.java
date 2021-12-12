package com.backend.technical.validators;

import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.errors.ErrorItem;
import com.backend.technical.exceptions.CustomErrorException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.backend.technical.utils.CommonUtils.DATA_BODY_KEY;
import static com.backend.technical.utils.CommonUtils.DEVICE_ID_KEY;
import static com.backend.technical.utils.CommonUtils.MANDATORY_BODY_PATTERN_MSG;
import static com.backend.technical.utils.CommonUtils.MANDATORY_PARAMETER_PATTERN_MSG;
import static com.backend.technical.utils.CommonUtils.isBlank;

@Component
public class DeviceValidator {

    public void validateRequest(final DeviceRequest request) {
        List<ErrorItem> errors = new ArrayList<>();
        if (isBlank(request.getDeviceId())) {
            errors.add(new ErrorItem(DEVICE_ID_KEY, String.format(MANDATORY_PARAMETER_PATTERN_MSG, request.getDeviceId())));
        }
        if (request.getData() == null) {
            errors.add(new ErrorItem(DATA_BODY_KEY, MANDATORY_BODY_PATTERN_MSG));
        }

        if (!errors.isEmpty()) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Bad Request.", errors);
        }
    }
}
