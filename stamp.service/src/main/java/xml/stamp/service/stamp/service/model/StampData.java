package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Podaci_o_zigu", propOrder = {"type", "kind", "imagePath", "colors",
        "transliterationStamp", "translateStamp", "descriptionStamp", "goodsAndServicesClass", "priority",
        "stampApplicationNumber", "dateOfApplication"})
@XmlRootElement(name = "Podaci_o_zigu")
public class StampData {


    @XmlElement(name = "Tip", required = true)
    protected StampType type;

    @XmlElement(name = "Vrsta", required = true)
    protected String kind;

    @XmlElement(name = "Izgled_znaka", required = true)
    protected String imagePath;

    @XmlElement(name = "Boja_znaka", required = true)
    protected List<String> colors;

    @XmlElement(name = "Transliteracija_znaka", required = false)
    protected String transliterationStamp;

    @XmlElement(name = "Prevod_znaka", required = false)
    protected String translateStamp;

    @XmlElement(name = "Opis_znaka", required = true)
    protected String descriptionStamp;

    @XmlElement(name = "Klase_robe_i_usluga", required = true)
    protected List<String> goodsAndServicesClass;

    @XmlElement(name = "Prvenstvo", required = true)
    protected Priority priority;

    @XmlElement(name="Broj_prijave_ziga", required = true)
    protected String stampApplicationNumber;

    @XmlElement(name="Datum_prijave", required = true)
    protected DatePredicate dateOfApplication;



    @Override
    public String toString() {
        return "StampData{" +
                "type=" + type +
                ", kind='" + kind + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", colors=" + colors +
                ", transliterationStamp='" + transliterationStamp + '\'' +
                ", translateStamp='" + translateStamp + '\'' +
                ", descriptionStamp='" + descriptionStamp + '\'' +
                ", goodsAndServicesClass=" + goodsAndServicesClass +
                ", priority='" + priority + '\'' +
                "}\n";
    }

    public boolean contains(String s) {
        if(type.toString().toLowerCase().contains(s))
            return true;
        if(kind.toString().toLowerCase().contains(s))
            return true;
        for(String color:colors){
            if(color.toLowerCase().contains(s))
                return true;
        }
        if(transliterationStamp.toLowerCase().contains(s))
            return true;
        if(translateStamp.toLowerCase().contains(s))
            return true;
        if(descriptionStamp.toLowerCase().contains(s))
            return true;
        for(String goods:goodsAndServicesClass){
            if(goods.toLowerCase().contains(s))
                return true;
        }
        if(priority.getReason().toLowerCase().contains(s))
            return true;
        if(stampApplicationNumber.contains(s))
            return true;
        if(dateOfApplication.getDate().toString().contains(s))
            return true;
        return false;
    }
}
