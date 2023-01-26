package xml.patent.serice.patent.service.dto;

import lombok.Getter;
import lombok.Setter;
import xml.patent.serice.patent.service.beans.*;
import xml.patent.serice.patent.service.dto.request.PreviousPatentDTO;
import xml.patent.serice.patent.service.dto.request.TitleDTO;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FullPatentDTO {
    private String patentId;
    private LocalDate applicationDate;
    private LocalDate acknowladgedDateOfSubmission;

    private String recipientName;
    private String recipientStreet;
    private String recipientNumber;
    private String recipientPostNumber;
    private String recipientCity;
    private String recipientCountry;

    private List<TitleDTO> titles;

    private String submitterName;
    private String submitterSurname;
    private String submitterCitizenship;
    private String submitterBusinessName;
    private String submitterStreet;
    private String submitterNumber;
    private String submitterPostNumber;
    private String submitterCity;
    private String submitterCountry;
    private String submitterPhone;
    private String submitterFax;
    private String submitterEmail;
    private boolean submitterIsTheInventor;
    private boolean submitterPerson;

    private String commissionerName;
    private String commissionerSurname;
    private String commissionerCitizenship;
    private String commissionerBusinessName;
    private String commissionerStreet;
    private String commissionerNumber;
    private String commissionerPostNumber;
    private String commissionerCity;
    private String commissionerCountry;
    private String commissionerPhone;
    private String commissionerFax;
    private String commissionerEmail;
    private boolean commissionerForRepresentation;
    private boolean commissionerForReceivingLetters;
    private boolean commissionercommonRepresentative;
    private boolean commissionerPerson;

    private String inventorName;
    private String inventorSurname;
    private String inventorCitizenship;
    private String inventorBusinessName;
    private String inventorStreet;
    private String inventorNumber;
    private String inventorPostNumber;
    private String inventorCity;
    private String inventorCountry;
    private String inventorPhone;
    private String inventorFax;
    private String inventorEmail;
    private boolean wantToBeListed;
    private boolean inventorsPerson;

    private String deliveryStreet;
    private String deliveryNumber;
    private String deliveryPostNumber;
    private String deliveryCity;
    private String deliveryCountry;
    private boolean emailNotification;
    private boolean letterNotification;

    private boolean additionalPatent;
    private boolean separatedPatent;
    private String previousPatentId;
    private LocalDate submissionDate;
    private String country;

    private List<PreviousPatentDTO> priorityRights;

    private boolean hasSolution;
    private SolutionDTO solution;

    public void addTitle(TitleDTO dto) {
        if (titles == null)
            titles = new ArrayList<>();
        titles.add(dto);
    }

    public void addPriorityPatent(PreviousPatentDTO dto) {
        if (priorityRights == null)
            priorityRights = new ArrayList<>();
        priorityRights.add(dto);
    }

    public void setSubmitterData(Submitter submitter) {
        submitterCity = submitter.getGlobalEntity().getAddress().getCity();
        submitterCountry = submitter.getGlobalEntity().getAddress().getCountry();
        submitterNumber = submitter.getGlobalEntity().getAddress().getNumber();
        submitterStreet = submitter.getGlobalEntity().getAddress().getStreet();
        submitterPostNumber = submitter.getGlobalEntity().getAddress().getPostNumber();
        submitterEmail = submitter.getGlobalEntity().getContact().getEmail();
        submitterFax = submitter.getGlobalEntity().getContact().getFaxNumber();
        submitterPhone = submitter.getGlobalEntity().getContact().getPhoneNumber();
        submitterIsTheInventor = submitter.getSubmitterTheInventor() == Checkbox.DA;
        if (submitter.getGlobalEntity() instanceof Person){
            submitterPerson = true;
            Person p = (Person)submitter.getGlobalEntity();
            submitterName = p.getName().getText();
            submitterSurname = p.getSurname().getText();
            submitterCitizenship = p.getCitizenship();
        }else{
            submitterPerson = false;
            LegalEntity le = (LegalEntity)submitter.getGlobalEntity();
            submitterBusinessName = le.getBusinessName().getText();
        }
    }

    public void setCommissionerData(Commissioner commissioner) {
        commissionerCity = commissioner.getGlobalEntity().getAddress().getCity();
        commissionerCountry = commissioner.getGlobalEntity().getAddress().getCountry();
        commissionerNumber = commissioner.getGlobalEntity().getAddress().getNumber();
        commissionerStreet = commissioner.getGlobalEntity().getAddress().getStreet();
        commissionerPostNumber = commissioner.getGlobalEntity().getAddress().getPostNumber();
        commissionerEmail = commissioner.getGlobalEntity().getContact().getEmail();
        commissionerFax = commissioner.getGlobalEntity().getContact().getFaxNumber();
        commissionerPhone = commissioner.getGlobalEntity().getContact().getPhoneNumber();
        if (commissioner.getGlobalEntity() instanceof Person){
            commissionerPerson = true;
            Person p = (Person)commissioner.getGlobalEntity();
            commissionerName = p.getName().getText();
            commissionerSurname = p.getSurname().getText();
            commissionerCitizenship = p.getCitizenship();
        }else{
            commissionerPerson = false;
            LegalEntity le = (LegalEntity)commissioner.getGlobalEntity();
            commissionerBusinessName = le.getBusinessName().getText();
        }
        commissionerForReceivingLetters = commissioner.getCommissionerForReceivingLetters() == Checkbox.DA;
        commissionerForRepresentation = commissioner.getCommissionerForRepresentation() == Checkbox.DA;
        commissionercommonRepresentative = commissioner.getCommonRepresentative() == Checkbox.DA;
    }

    public void setInventorData(Inventor inventor) {
        inventorCity = inventor.getGlobalEntity().getAddress().getCity();
        inventorCountry = inventor.getGlobalEntity().getAddress().getCountry();
        inventorNumber = inventor.getGlobalEntity().getAddress().getNumber();
        inventorStreet = inventor.getGlobalEntity().getAddress().getStreet();
        inventorPostNumber = inventor.getGlobalEntity().getAddress().getPostNumber();
        inventorEmail = inventor.getGlobalEntity().getContact().getEmail();
        inventorFax = inventor.getGlobalEntity().getContact().getFaxNumber();
        inventorPhone = inventor.getGlobalEntity().getContact().getPhoneNumber();
        if (inventor.getGlobalEntity() instanceof Person){
            inventorsPerson = true;
            Person p = (Person)inventor.getGlobalEntity();
            inventorName = p.getName().getText();
            inventorSurname = p.getSurname().getText();
            inventorCitizenship = p.getCitizenship();
        }else{
            inventorsPerson = false;
            LegalEntity le = (LegalEntity)inventor.getGlobalEntity();
            inventorBusinessName = le.getBusinessName().getText();
        }
    }

}
