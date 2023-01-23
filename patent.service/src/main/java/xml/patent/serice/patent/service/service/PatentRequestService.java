package xml.patent.serice.patent.service.service;

import org.apache.jena.vocabulary.RDF;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.patent.serice.patent.service.beans.*;
import xml.patent.serice.patent.service.dto.request.*;
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

    private Inventor createInventor(PatentRequestDTO patentRequest) {
        Inventor inventor = new Inventor();
        inventor.setGlobalEntity(createGlobalEntity(patentRequest.getCommissioner(), RDFConstants.inventorPropertyName, RDFConstants.inventorPropertySurname, RDFConstants.inventorPropertyBusinessName));
        inventor.setWantToBeListed(inventor.getWantToBeListed());
        return inventor;
    }

    private Commissioner createComissioner(PatentRequestDTO patentRequest) {
        Commissioner commissioner = new Commissioner();
        commissioner.setGlobalEntity(createGlobalEntity(patentRequest.getCommissioner(), RDFConstants.commissionerPropertyName, RDFConstants.commissionerPropertySurName, RDFConstants.commissionerPropertyBusinessName));
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

    private Submitter createSubmitter(PatentRequestDTO patentRequest) {
        Submitter submitter = new Submitter();
        submitter.setGlobalEntity(createGlobalEntity(patentRequest.getSubmitter(), RDFConstants.submitterPropertyName,
                RDFConstants.submitterPropertySurname, RDFConstants.submitterPropertyBusinessName));
        if (patentRequest.isSubmitterIsTheInventor())
            submitter.setSubmitterTheInventor(Checkbox.DA);
        else
            submitter.setSubmitterTheInventor(Checkbox.NE);
        return submitter;
    }

    private GlobalEntity createGlobalEntity(EntityDTO entity, String submitterPropertyName, String submitterPropertySurname, String submitterPropertyBusinessName) {
        if (entity.isPerson()){
            Person result = new Person();
            result.setName(createStringPredicate(entity.getName(), submitterPropertyName));
            result.setSurname(createStringPredicate(entity.getSurname(), submitterPropertySurname));
            result.setCitizenship(entity.getCitizenship());
            result.setAddress(createAddressFromAddressDTO(entity));
            result.setContact(createContact(entity.getEmail(), entity.getFax(), entity.getPhone()));
            return result;
        }else{
            LegalEntity result = new LegalEntity();
            result.setBusinessName(createStringPredicate(entity.getBusinessName(), submitterPropertyBusinessName));
            result.setAddress(createAddressFromAddressDTO(entity));
            result.setContact(createContact(entity.getEmail(), entity.getFax(), entity.getPhone()));
            return result;
        }
    }

    private Predicate createStringPredicate(String name, String property) {
        Predicate predicate = new Predicate();
        predicate.setDatatype(RDFConstants.stringDatatype);
        predicate.setText(name);
        predicate.setProperty(property);
        return predicate;
    }

    private Contact createContact(String email, String fax, String phone) {
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setFaxNumber(fax);
        contact.setPhoneNumber(phone);
        return contact;
    }

    private List<Patent> createPriorityRights(PatentRequestDTO patentRequest) {
        List<Patent> patents = new ArrayList<>();
        if (patentRequest.getPriorityPatent() == null)
            return patents;
        for (PreviousPatentDTO p : patentRequest.getPriorityPatent()){
            patents.add(createPatent(p, RDFConstants.siblingRelation));
        }
        return patents;
    }

    private AdditionalPatent createAdditionalPatent(PatentRequestDTO patentRequest) {
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

    private Patent createPatent(PreviousPatentDTO previousPatent, String relation) {
        Patent patent = new Patent();
        patent.setPatentId(previousPatent.getApplicationNumber());
        patent.setSubmissionDate(previousPatent.getSubmissionDateLocalDate());
        patent.setCountry(previousPatent.getCountry());
        patent.setRelation(relation);
        patent.setHref(RDFConstants.baseURI + convertToSafe(previousPatent.getApplicationNumber()));
        return patent;
    }

    private String convertToSafe(String applicationNumber) {
        return applicationNumber.replace("/", "-");
    }

    private DeliveryData createDeliveryData(PatentRequestDTO patentRequest) {
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

    private Address createAddressFromAddressDTO(AddressDTO addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setNumber(addressDto.getNumber());
        address.setStreet(addressDto.getStreet());
        address.setPostNumber(addressDto.getPostalNumber());
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

    private String generateDocumentId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("P-");
        /*sb.append(now.getDayOfMonth());
        sb.append(now.getMonth());*/
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("-");
        String year = String.valueOf(now.getYear()).substring(2,4);
        sb.append(year);
        return sb.toString();
    }

    private String generateId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("P-");
        /*sb.append(now.getDayOfMonth());
        sb.append(now.getMonth());*/
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("/");
        String year = String.valueOf(now.getYear()).substring(2,4);
        sb.append(year);
        return sb.toString();
    }

}
