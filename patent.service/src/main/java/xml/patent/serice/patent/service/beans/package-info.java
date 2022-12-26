@XmlSchema(
        namespace = "http://www.ftn.uns.ac.rs/patent",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
        xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "pat", namespaceURI="http://www.ftn.uns.ac.rs/patent"),
                @javax.xml.bind.annotation.XmlNs(prefix = "base", namespaceURI = "http://www.ftn.uns.ac.rs/base-schame"),
                @javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema"),
                @javax.xml.bind.annotation.XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance")}
)package xml.patent.serice.patent.service.beans;

import javax.xml.bind.annotation.XmlSchema;
