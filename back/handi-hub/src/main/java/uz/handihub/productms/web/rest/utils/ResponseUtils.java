package uz.handihub.productms.web.rest.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.handihub.productms.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ResponseUtils {

    private static final String TOTAL_COUNT_HEADER_NAME = "X-Total-Count";

    public static <T> ResponseEntity<T> wrap(Optional<T> optional) {
        if (Objects.isNull(optional)) {
            throw new InvalidArgumentException("Invalid argument is passed! Optional must not be null!");
        }

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static <T> ResponseEntity<List<T>> wrap(Page<T> pagination) {
        if (Objects.isNull(pagination)) {
            throw new InvalidArgumentException("Invalid argument is passed! Page must not be null!");
        }

        return ResponseEntity
                .ok()
                .header(TOTAL_COUNT_HEADER_NAME, String.valueOf(pagination.getTotalElements()))
                .body(pagination.getContent());
    }
}
