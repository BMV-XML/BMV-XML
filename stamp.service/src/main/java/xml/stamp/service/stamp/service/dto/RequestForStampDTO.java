package xml.stamp.service.stamp.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestForStampDTO {
    private String text;

    public RequestForStampDTO(String text) {
        this.text = text;
    }

}
