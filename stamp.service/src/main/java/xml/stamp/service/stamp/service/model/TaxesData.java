package xml.stamp.service.stamp.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Podaci_o_taksama", propOrder = {"baseTax", "classTax", "graphicSolutionTax", "totalTax"})
@XmlRootElement(name = "Podaci_o_taksama")

public class TaxesData {

    @XmlElement(name = "Osnovna_taksa", required = true)
    protected double baseTax;

    @XmlElement(name = "Taksa_za_klasu", required = true)
    protected double classTax;

    @XmlElement(name = "Taksa_za_graficko_resenje", required = true)
    protected double graphicSolutionTax;

    @XmlElement(name = "Ukupno", required = true)
    protected double totalTax;


    @Override
    public String toString() {
        return "TaxesData{" +
                "baseTax=" + baseTax +
                ", classTax=" + classTax +
                ", graphicSolutionTax=" + graphicSolutionTax +
                ", totalTax=" + totalTax +
                "}\n";
    }

    public boolean contains(String s){
        if(String.valueOf(baseTax).contains(s))
            return true;
        if(String.valueOf(classTax).contains(s))
            return true;
        if(String.valueOf(graphicSolutionTax).contains(s))
            return true;
        if(String.valueOf(totalTax).contains(s))
            return true;
        return false;
    }
}
