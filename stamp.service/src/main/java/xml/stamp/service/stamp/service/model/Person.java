package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlType(name = "TFizicko_lice", propOrder = {
        "address",
        "contact",
        "name",
        "surname",
        "citizenship"}, namespace = "http://www.ftn.uns.ac.rs/base-schame")
@XmlRootElement(name = "TFizicko_lice", namespace = "http://www.ftn.uns.ac.rs/base-schame")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person extends GlobalEntity {

    @XmlElement(name = "Ime", required = true)
    protected Predicate name;
    @XmlElement(name = "Prezime", required = true)
    protected Predicate surname;
    @XmlElement(name = "Drzavljanstvo", required = true)
    protected String citizenship;


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", address=" + address +
                ", contact=" + contact +
                "}\n";
    }

    @Override
    public boolean contains(String s){
        if (basicContains(s))
            return true;
        if (name.getText().toLowerCase().contains(s)){
            return true;
        }
        if (surname.getText().toLowerCase().contains(s)){
            return true;
        }
        if (citizenship.toLowerCase().contains(s)){
            return true;
        }
        return false;
    }
}
