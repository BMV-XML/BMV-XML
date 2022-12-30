package xml.stamp.service.stamp.service.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate>
{
    @Override
    public String marshal(LocalDate date)
    {
        if ( date == null ) return null;
        return date.toString();
    }

    @Override
    public LocalDate unmarshal(String string)
    {
 //       System.out.println(string);
        return LocalDate.parse(string);
    }
}