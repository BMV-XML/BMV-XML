package xml.patent.serice.patent.service.dto.full.report;

import xml.patent.serice.patent.service.beans.*;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public FullReportDTO createFullReportDTO() {
        return new FullReportDTO();
    }

}
