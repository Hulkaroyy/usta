package uz.handihub.productms.domain.enumeration;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.MediaType;

/**
 *
 * Link for resource
 * @link: https://stackoverflow.com/a/51598200/18417541
 */
@Getter
@ToString
public enum MimeType {
    MIME_APPLICATION_JSON("application/json",".json"),
    MIME_APPLICATION_ZIP("application/zip",".zip"),
    MIME_APPLICATION_TGZ("application/tgz",".tgz"),
    MIME_APPLICATION_PDF("application/pdf",".pdf"),
    MIME_IMAGE_GIF("image/gif",".gif"),
    MIME_IMAGE_JPEG("image/jpeg",".jpeg"),
    MIME_IMAGE_PNG("image/png",".png"),
    MIME_IMAGE_X_ICON("image/x-icon",".icon"),
    MIME_TEXT_PLAIN("text/plain",".txt"),
    ;

    private final MediaType type;
    private final String extension;

    MimeType(String typeAsString, String extension) {
        this.type = MediaType.valueOf(typeAsString);
        this.extension = extension;
    }
}
