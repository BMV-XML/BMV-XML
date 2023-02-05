package xml.patent.serice.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatentDTO {
    private String id;
    private LocalDate applicationDate;
    private String submitter;
    private String submitterFullname;
    private boolean hasSolution;
}
