package xml.stamp.service.stamp.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Adresa", propOrder = {"street", "number", "postNumber", "city", "country"})
@XmlRootElement(name = "Adresa", namespace = "http://www.ftn.uns.ac.rs/base-schame")
public class Address {
    @XmlElement(name = "Ulica", required = true,namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String street;

    @XmlElement(name = "Broj", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String number;

    @XmlElement(name = "Postanski_broj", required = true,namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String postNumber;

    @XmlElement(name = "Grad", required = true,namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String city;

    @XmlElement(name = "Drzava", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String country;


    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postNumber='" + postNumber + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                "}\n";
    }
    public boolean contains(String s) {
        if (street.toLowerCase().contains(s))
            return true;
        if (number.toLowerCase().contains(s))
            return true;
        if (postNumber.toLowerCase().contains(s))
            return true;
        if (city.toLowerCase().contains(s))
            return true;
        if (country.toLowerCase().contains(s))
            return true;
        return false;
    }
}
