package xml.stamp.service.stamp.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.stamp.service.stamp.service.dto.request.AddressDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecepientDTO extends AddressDTO {
    private String name;
}
