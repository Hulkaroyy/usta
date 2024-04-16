package uz.handihub.productms.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.handihub.productms.exceptions.NotFoundException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public class RestRequestUtils {
    private static final String USER_ID_HEADER_NAME = "X-User-Id";

    @NotNull
    public static HttpServletRequest getCurrentHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            log.warn("Current HTTP request is not found in RequestContextHolder");
            throw new NotFoundException("Current HTTP request is not found in RequestContextHolder");
        }

        return servletRequestAttributes.getRequest();
    }

    @NotNull
    public static UUID getUserIdFromHeader() {

        HttpServletRequest request = getCurrentHttpServletRequest();

        String userId = request.getHeader(USER_ID_HEADER_NAME);
        if (StringUtils.isBlank(userId)) {
            String api = request.getRequestURI();
            log.warn("User ID is not found in header(X-User-Id) of current request! API: {}", api);
            throw new NotFoundException("User ID is not found in header(X-User-Id) of current request! API: " + api);
        }

        return UUID.fromString(userId);
    }

}
