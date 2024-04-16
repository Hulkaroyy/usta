package uz.handihub.productms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.core.io.Resource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContentWithResourceDTO {

    private ContentDTO content;

    private Resource resource;

}
