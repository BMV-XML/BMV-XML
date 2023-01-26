package xml.patent.serice.patent.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.patent.serice.patent.service.beans.*;
import xml.patent.serice.patent.service.dto.request.*;
import xml.patent.serice.patent.service.exception.NotValidException;
import xml.patent.serice.patent.service.fuseki.FusekiReader;
import xml.patent.serice.patent.service.fuseki.FusekiWriter;
import xml.patent.serice.patent.service.fuseki.MetadataExtractor;
import xml.patent.serice.patent.service.jaxb.LoaderValidation;
import xml.patent.serice.patent.service.repository.PatentRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatentRequestService {

    @Autowired
    private PatentRepository patentRepository;

    @Autowired
    private LoaderValidation loader;

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private FusekiWriter fusekiWriter;

    @Autowired
    private FusekiReader fusekiReader;

    public String savePatentRequest(PatentRequest patentRequest) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        System.out.println(patentRequest);
        os = this.loader.marshalling(patentRequest, os);
        System.out.println("****************************************************");
        System.out.println(os);
        patentRepository.saveRequest(os, convertToSafe(patentRequest.getPatentId()));
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream = metadataExtractor.extractMetadata(os.toString(), outputStream);
        fusekiWriter.saveRDF(outputStream);
        return "nestooo";
    }

    public ArrayList<String> searchByMetadata(String name) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("naziv", name);

        ArrayList<String> result = fusekiReader.executeQuery(params);
        return  result;
    }

    public void savePatentRequest(PatentRequestDTO patentRequest) throws Exception {
        PatentRequest patentToSave = new PatentRequest();

        patentToSave.setPatentData(createPatentData(patentRequest));
        patentToSave.setRecepient(createRecepient());
        patentToSave.setTitleList(createTitlesList(patentRequest));

        patentToSave.setSubmitter(createSubmitter(patentRequest));
        patentToSave.setCommissioner(createComissioner(patentRequest));
        patentToSave.setInventor(createInventor(patentRequest));
        //people
        patentToSave.setDeliveryData(createDeliveryData(patentRequest));
        patentToSave.setAdditionalPatent(createAdditionalPatent(patentRequest));
        patentToSave.setPriorityRights(createPriorityRights(patentRequest));
        patentToSave.setAbout(RDFConstants.baseURI + convertToSafe(patentToSave.getPatentId()));
        patentToSave.setVocab(RDFConstants.vocabURI);

        this.savePatentRequest(patentToSave);
    }

    private Inventor createInventor(PatentRequestDTO patentRequest) throws NotValidException {
        Inventor inventor = new Inventor();
        if (patentRequest.isSubmitterIsTheInventor())
            return null;
        inventor.setGlobalEntity(createGlobalEntity(patentRequest.getInventor(), RDFConstants.inventorPropertyName));
        if (patentRequest.isInventorWantsToBeListed())
            inventor.setWantToBeListed(Checkbox.DA);
        else
            inventor.setWantToBeListed(Checkbox.NE);
        return inventor;
    }

    private Commissioner createComissioner(PatentRequestDTO patentRequest) throws NotValidException {
        Commissioner commissioner = new Commissioner();
        commissioner.setGlobalEntity(createGlobalEntity(patentRequest.getCommissioner(), RDFConstants.commissionerPropertyName));
        if (patentRequest.isCommissionerForJointRepresentation()){
            commissioner.setCommonRepresentative(Checkbox.DA);
        }else{
            commissioner.setCommonRepresentative(Checkbox.NE);
        }
        if (patentRequest.isCommissionerForLetters()){
            commissioner.setCommissionerForReceivingLetters(Checkbox.DA);
        }else{
            commissioner.setCommissionerForReceivingLetters(Checkbox.NE);
        }
        if (patentRequest.isCommissionerForRepresentation()){
            commissioner.setCommissionerForRepresentation(Checkbox.DA);
        }else{
            commissioner.setCommissionerForRepresentation(Checkbox.NE);
        }
        return commissioner;
    }

    private Submitter createSubmitter(PatentRequestDTO patentRequest) throws NotValidException {
        Submitter submitter = new Submitter();
        submitter.setGlobalEntity(createGlobalEntity(patentRequest.getSubmitter(), RDFConstants.submitterPropertyName));
        if (patentRequest.isSubmitterIsTheInventor())
            submitter.setSubmitterTheInventor(Checkbox.DA);
        else
            submitter.setSubmitterTheInventor(Checkbox.NE);
        return submitter;
    }

    private GlobalEntity createGlobalEntity(EntityDTO entity, String submitterPropertyName) throws NotValidException {
        if (entity.isPerson()){
            Person result = new Person();
            result.setName(createStringPredicate(entity.getName(), submitterPropertyName));
            result.setSurname(createStringPredicate(entity.getSurname(), submitterPropertyName));
            result.setCitizenship(entity.getCitizenship());
            result.setAddress(createAddressFromAddressDTO(entity));
            result.setContact(createContact(entity.getEmail(), entity.getFax(), entity.getPhone()));
            return result;
        }else{
            LegalEntity result = new LegalEntity();
            System.out.println(entity.getBusinessName());
            result.setBusinessName(createStringPredicate(entity.getBusinessName(), submitterPropertyName));
            result.setAddress(createAddressFromAddressDTO(entity));
            result.setContact(createContact(entity.getEmail(), entity.getFax(), entity.getPhone()));
            return result;
        }
    }

    private Predicate createStringPredicate(String name, String property) throws NotValidException {
        Predicate predicate = new Predicate();
        predicate.setDatatype(RDFConstants.stringDatatype);
        predicate.setText(checkIfStartsWithCapitalMoreWithSpace(name, "ime"));
        predicate.setProperty(property);
        return predicate;
    }

    private Contact createContact(String email, String fax, String phone) throws NotValidException {
        Contact contact = new Contact();
        contact.setEmail(checkEmail(email));
        contact.setFaxNumber(fax);
        contact.setPhoneNumber(checkPhoneNumber(phone));
        return contact;
    }

    private List<Patent> createPriorityRights(PatentRequestDTO patentRequest) throws NotValidException {
        List<Patent> patents = new ArrayList<>();
        if (patentRequest.getPriorityPatent() == null)
            return patents;
        for (PreviousPatentDTO p : patentRequest.getPriorityPatent()){
            patents.add(createPatent(p, RDFConstants.siblingRelation));
        }
        return patents;
    }

    private AdditionalPatent createAdditionalPatent(PatentRequestDTO patentRequest) throws NotValidException {
        AdditionalPatent additionalPatent = new AdditionalPatent();
        if (patentRequest.isAdditionalPatent()) {
            additionalPatent.setAdditionalPatent(Checkbox.DA);
            additionalPatent.setSeperatePatent(Checkbox.NE);
            additionalPatent.setPatent(createPatent(patentRequest.getPreviousPatent(), RDFConstants.additionalRelation));
        }else if (patentRequest.isSeparatedPatent()){
            additionalPatent.setAdditionalPatent(Checkbox.DA);
            additionalPatent.setSeperatePatent(Checkbox.NE);
            additionalPatent.setPatent(createPatent(patentRequest.getPreviousPatent(), RDFConstants.childRelation));
        }else {
            additionalPatent.setAdditionalPatent(Checkbox.NE);
            additionalPatent.setSeperatePatent(Checkbox.NE);
        }
        return additionalPatent;
    }

    private Patent createPatent(PreviousPatentDTO previousPatent, String relation) throws NotValidException {
        Patent patent = new Patent();
        patent.setPatentId(checkApplicationNumber(previousPatent.getApplicationNumber()));
        patent.setSubmissionDate(previousPatent.getSubmissionDateLocalDate());
        patent.setCountry(checkIfStartsWithCapitalMoreWithSpace(previousPatent.getCountry(), "mark"));
        patent.setRelation(relation);
        patent.setHref(RDFConstants.baseURI + convertToSafe(previousPatent.getApplicationNumber()));
        return patent;
    }


    private String convertToSafe(String applicationNumber) {
        return applicationNumber.replace("/", "-");
    }

    private DeliveryData createDeliveryData(PatentRequestDTO patentRequest) throws NotValidException {
        DeliveryData deliveryData = new DeliveryData();
        deliveryData.setAddress(createAddressFromAddressDTO(patentRequest.getAddress()));

        if (patentRequest.isNotifyMeViaEmails()){
            deliveryData.setEmailNotification(Checkbox.DA);
        }else{
            deliveryData.setEmailNotification(Checkbox.NE);
        }
        if (patentRequest.isNotifyMeViaLetters()){
            deliveryData.setLetterNotification(Checkbox.DA);
        }else{
            deliveryData.setLetterNotification(Checkbox.NE);
        }

        return deliveryData;
    }

    private Address createAddressFromAddressDTO(AddressDTO addressDto) throws NotValidException {
        Address address = new Address();
        address.setCity(checkIfStartsWithCapitalMoreWithSpace(addressDto.getCity(), "city"));
        address.setCountry(checkIfStartsWithCapitalMoreWithSpace(addressDto.getCountry(), "country"));
        address.setNumber(checkIfHouseNumber(addressDto.getNumber()));
        address.setStreet(checkIfStartsWithCapitalMoreWithSpace(addressDto.getStreet(), "street"));
        address.setPostNumber(checkPostalNumber(addressDto.getPostalNumber()));
        return address;
    }

    private List<Title> createTitlesList(PatentRequestDTO patentRequest) {
        List<Title> titles = new ArrayList<>();
        for (TitleDTO t: patentRequest.getTitles()){
            Title title = new Title();
            title.setLanguage(t.getLanguage());
            title.setTitle(t.getTitle());
            title.setDatatype(RDFConstants.stringDatatype);
            title.setProperty(RDFConstants.titleProperty);
            titles.add(title);
        }
        return titles;
    }

    private Recepient createRecepient() {
        Recepient recepient = new Recepient();
        recepient.setName(RDFConstants.recipientName);
        Address address = new Address();
        address.setCity(RDFConstants.recipientCity);
        address.setCountry(RDFConstants.recipientCountry);
        address.setNumber(RDFConstants.recipientNumber);
        address.setStreet(RDFConstants.recipientStreet);
        address.setPostNumber(RDFConstants.recipientPostalNumber);
        recepient.setAddress(address);
        return recepient;
    }

    private PatentData createPatentData(PatentRequestDTO patentRequest) {
        PatentData pd = new PatentData();

        Predicate patentId = new Predicate();
        patentId.setText(generateId());
        patentId.setDatatype(RDFConstants.stringDatatype);
        patentId.setProperty(RDFConstants.applicationNumberProperty);
        pd.setPatentId(patentId);

        DatePredicate applicationDate = new DatePredicate();
        applicationDate.setProperty(RDFConstants.applicationDateProperty);
        applicationDate.setDatatype(RDFConstants.dateDatatype);
        applicationDate.setDate(LocalDate.now());
        pd.setApplicationDate(applicationDate);

        pd.setAcknowladgeDateOfSubmission(LocalDate.now());

        return pd;
    }

    private String generateId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("P-");
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("/");
        String year = String.valueOf(now.getYear()).substring(2,4);
        sb.append(year);
        return sb.toString();
    }

    private String checkPostalNumber(String postNumber) throws NotValidException {
        Pattern p = Pattern.compile("[0-9]{5}");//. represents single character
        Matcher m = p.matcher(postNumber);//NE RADIIIIIIIIII
        if (!m.matches())
            throw new NotValidException("Phone number is not valid");
        return postNumber;
    }

    private String checkPhoneNumber(String phoneNumber) throws NotValidException {
        Pattern p = Pattern.compile("\\+[0-9]{6,20}");//. represents single character
        Matcher m = p.matcher(phoneNumber);//NE RADIIIIIIIIII
        if (!m.matches())
            throw new NotValidException("Phone number is not valid");
        return phoneNumber;
    }

    private String checkEmail(String email) throws NotValidException {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");//. represents single character
        Matcher m = p.matcher(email);
        if (!m.matches() | email.length()<6 |email.length() >50)
            throw new NotValidException("Email is not valid");
        return email;
    }

/*
    private String checkIfStartsWithCapital(String name, String element) throws NotValidException {
        Pattern p = Pattern.compile("[A-ZŠĆČĐŽ][a-zšđčćž]*");//. represents single character
        Matcher m = p.matcher(name);
        if (!m.matches())
            throw new NotValidException(element + " is not valid");
        return name;
    }
*/

    private String checkIfStartsWithCapitalMoreWithSpace(String name, String element) throws NotValidException {
        System.out.println(name);
        Pattern p = Pattern.compile("[A-ZŠĆČĐŽ][a-zšđčćžA-ZŠĆČĐŽ ]*");//. represents single character
        //Pattern p = Pattern.compile("[A-Z][a-zA-Z]*");//. represents single character
        Matcher m = p.matcher(name);
        if (!m.matches() || name.length() < 2 || name.length() > 50)
            throw new NotValidException(element + " is not valid");
        return name;
    }


    private String checkIfHouseNumber(String name) throws NotValidException {
        System.out.println(name);
        //Pattern p = Pattern.compile("[A-ZŠĆČĐŽ][a-zšđčćžA-ZŠĆČĐŽ ]*}");//. represents single character
        Pattern p = Pattern.compile("[a-zA-Z0-9]*");//. represents single character
        Matcher m = p.matcher(name);
        if (!m.matches() || name.length() < 1 || name.length() > 10)
            throw new NotValidException("House number is not valid");
        return name;
    }

    private String checkApplicationNumber(String applicationNumber) throws NotValidException {
        Pattern p = Pattern.compile("P-[0-9]{4,10}/[0-9]{2}");//. represents single character
        Matcher m = p.matcher(applicationNumber);
        if (!m.matches())
            throw new NotValidException(applicationNumber + " is not valid");
        return applicationNumber;
    }

}
