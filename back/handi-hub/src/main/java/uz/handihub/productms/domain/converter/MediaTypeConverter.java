package uz.handihub.productms.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.http.MediaType;

import java.util.Objects;

@Converter
public class MediaTypeConverter implements AttributeConverter<MediaType, String> {

    @Override
    public String convertToDatabaseColumn(MediaType mediaType) {
        if(Objects.isNull(mediaType)) {
            return null;
        }

        return mediaType.getType();
    }

    @Override
    public MediaType convertToEntityAttribute(String s) {
        if(Objects.isNull(s)){
            return null;
        }

        return new MediaType(s);
    }
}
