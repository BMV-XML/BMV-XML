@XmlSchema(
        namespace = "http://www.ftn.uns.ac.rs/authorship",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
        xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "aut", namespaceURI = "http://www.ftn.uns.ac.rs/authorship"),
                @javax.xml.bind.annotation.XmlNs(prefix = "base", namespaceURI = "http://www.ftn.uns.ac.rs/base-schame"),
                @javax.xml.bind.annotation.XmlNs(prefix = "xs", namespaceURI = "http://www.w3.org/2001/XMLSchema"),
                @javax.xml.bind.annotation.XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance"),
                @javax.xml.bind.annotation.XmlNs(prefix = "pred", namespaceURI = "http://www.ftn.uns.ac.rs/rdf/authorship/predicate/")}
)
package xml.authorship.service.authorship.service.beans;

import javax.xml.bind.annotation.XmlSchema;