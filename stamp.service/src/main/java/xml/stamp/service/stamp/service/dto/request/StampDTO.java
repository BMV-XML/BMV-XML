package xml.stamp.service.stamp.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StampDTO {
    String type;
    String kind;
    List<String> colors;
    String description;
    List<String> goodsAndServicesClass;
    String priority;
    String transliteration;
    String translation;
    String image;
}
