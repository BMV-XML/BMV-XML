package xml.stamp.service.stamp.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.stamp.service.stamp.service.dto.request.AddressDTO;
import xml.stamp.service.stamp.service.dto.request.EntityDTO;
import xml.stamp.service.stamp.service.dto.request.RequestForStampDTO;
import xml.stamp.service.stamp.service.exceptions.NotValidException;
import xml.stamp.service.stamp.service.fuseki.FusekiReader;
import xml.stamp.service.stamp.service.fuseki.FusekiWriter;
import xml.stamp.service.stamp.service.fuseki.MetadataExtractor;
import xml.stamp.service.stamp.service.jaxb.JaxLoader;
import xml.stamp.service.stamp.service.model.*;
import xml.stamp.service.stamp.service.repository.RequestForStampRepository;

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
public class RequestForStampService {
    @Autowired
    private RequestForStampRepository requestForStampRepository;
    @Autowired
    private MetadataExtractor metadataExtractor;
    @Autowired
    private FusekiWriter fusekiWriter;
    @Autowired
    private FusekiReader fusekiReader;

    @Autowired
    private JaxLoader loader;

    public String saveRequestForStamp(RequestForStamp requestForStamp) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        os = this.loader.marshalling(requestForStamp, os);
        requestForStampRepository.saveRequest(os, requestForStamp.getStampId());
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream = metadataExtractor.extractMetadata(os.toString(), outputStream);
        fusekiWriter.saveRDF(outputStream);
        return "nestooo";
    }

    public RequestForStamp getRequestForStampById(String id) throws Exception {
        return requestForStampRepository.getRequestForStampById(id);
    }

    public String filter() throws Exception {
        return  requestForStampRepository.filter();
    }

   /* public void saveFileFromString(String text) throws Exception {
        RequestForStamp requestForStamp = loader.unmarshalling(text);
        OutputStream outputStream = loader.marshalling(requestForStamp, new ByteArrayOutputStream());
        requestForStampRepository.saveRequest(outputStream);
        OutputStream out = new ByteArrayOutputStream();
        out = metadataExtractor.extractMetadata(text, out);
        fusekiWriter.saveRDF(out);
    }

    */

    public ArrayList<String> searchByMetadata(String name) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("naziv", name);

        ArrayList<String> result = fusekiReader.executeQuery(params);
        return  result;
    }

    public void saveStampRequest(RequestForStampDTO stampRequest) throws Exception {
        RequestForStamp stampToSave = new RequestForStamp();

        stampToSave.setApplicants(createApplicantsData(stampRequest));
        stampToSave.setLawyer(createLawyer(stampRequest));
        stampToSave.setCommonRepresentative(createCommonRepresentative(stampRequest));
        stampToSave.setStampData(createStampData(stampRequest));
        stampToSave.setRecepient(createRecepient());
        stampToSave.setAttachmentData(createAttachmentData(stampRequest));
        stampToSave.setTaxesData(createTaxesData(stampRequest));

        stampToSave.setAbout(RDFConstants.baseURI + convertToSafe(stampToSave.getStampId()));

        this.saveRequestForStamp(stampToSave);
    }

    private String convertToSafe(String applicationNumber) {
        return applicationNumber.replace("/", "-");
    }

    private TaxesData createTaxesData(RequestForStampDTO stampRequest) {
        TaxesData taxesData = new TaxesData();
        taxesData.setBaseTax(RDFConstants.baseTax);
        taxesData.setClassTax(stampRequest.getStamp().getGoodsAndServicesClass().size() * RDFConstants.classTax );
        if(stampRequest.getStamp().getType().equals("grafički znak")){
            taxesData.setGraphicSolutionTax(RDFConstants.graphicSolutionTax);
        }else{
            taxesData.setGraphicSolutionTax(0);
        }
        double total = taxesData.getBaseTax() + taxesData.getClassTax() + taxesData.getGraphicSolutionTax();
        taxesData.setTotalTax(total);
        return taxesData;
    }

    private AttachmentData createAttachmentData(RequestForStampDTO stampRequest) {

        Attachment exampleStamp = new Attachment();
        exampleStamp.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getExampleStamp().getSubmittedAttachment()));
        exampleStamp.setPath(stampRequest.getAttachmentData().getExampleStamp().getPath());

        Attachment listOfGoodsAndServices = new Attachment();
        listOfGoodsAndServices.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getListOfGoodsAndServices().getSubmittedAttachment()));
        listOfGoodsAndServices.setPath(stampRequest.getAttachmentData().getListOfGoodsAndServices().getPath());

        Attachment authority = new Attachment();
        authority.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getAuthority().getSubmittedAttachment()));
        authority.setPath(stampRequest.getAttachmentData().getAuthority().getPath());

        Attachment generalAuthorityAddedBefore = new Attachment();
        generalAuthorityAddedBefore.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getGeneralAuthorityAddedBefore().getSubmittedAttachment()));
        generalAuthorityAddedBefore.setPath(stampRequest.getAttachmentData().getGeneralAuthorityAddedBefore().getPath());

        Attachment authorityWillBeAddedAfter = new Attachment();
        authorityWillBeAddedAfter.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getAuthorityWillBeAddedAfter().getSubmittedAttachment()));
        authorityWillBeAddedAfter.setPath(stampRequest.getAttachmentData().getAuthorityWillBeAddedAfter().getPath());

        Attachment generalActOnCollectiveStampOrGuaranteeStamp = new Attachment();
        generalActOnCollectiveStampOrGuaranteeStamp.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getGeneralActOnCollectiveStampOrGuaranteeStamp().getSubmittedAttachment()));
        generalActOnCollectiveStampOrGuaranteeStamp.setPath(stampRequest.getAttachmentData().getGeneralActOnCollectiveStampOrGuaranteeStamp().getPath());

        Attachment proofOfRightOfPriority = new Attachment();
        proofOfRightOfPriority.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getProofOfRightOfPriority().getSubmittedAttachment()));
        proofOfRightOfPriority.setPath(stampRequest.getAttachmentData().getProofOfRightOfPriority().getPath());

        Attachment proofOfTaxPayment = new Attachment();
        proofOfTaxPayment.setSubmittedAttachment(Checkbox.valueOf(stampRequest.getAttachmentData().getProofOfTaxPayment().getSubmittedAttachment()));
        proofOfTaxPayment.setPath(stampRequest.getAttachmentData().getProofOfTaxPayment().getPath());

        AttachmentData attachmentData = new AttachmentData(exampleStamp, listOfGoodsAndServices, authority, generalAuthorityAddedBefore,
                authorityWillBeAddedAfter, generalActOnCollectiveStampOrGuaranteeStamp, proofOfRightOfPriority, proofOfTaxPayment);

        return attachmentData;
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
    private StampData createStampData(RequestForStampDTO stampRequest) {
        StampData stampData = new StampData();

        Predicate stampId = new Predicate();
        stampId.setText(generateDocumentId());
        stampId.setDatatype(RDFConstants.stringDatatype);
        stampId.setProperty(RDFConstants.applicationNumberProperty);
        stampData.setStampApplicationNumber(stampId.getText());

        DatePredicate applicationDate = new DatePredicate();
        applicationDate.setProperty(RDFConstants.applicationDateProperty);
        applicationDate.setDatatype(RDFConstants.dateDatatype);
        applicationDate.setDate(LocalDate.now());
        stampData.setDateOfApplication(applicationDate);

        stampData.setType(StampType.valueOf(stampRequest.getStamp().getType()));
        stampData.setKind(stampRequest.getStamp().getKind());
        stampData.setDescriptionStamp(stampRequest.getStamp().getDescription());
        stampData.setTranslateStamp(stampRequest.getStamp().getTranslation());
        stampData.setTransliterationStamp(stampRequest.getStamp().getTransliteration());
        stampData.setColors(stampRequest.getStamp().getColors());
        stampData.setImagePath(stampRequest.getStamp().getImage());
        stampData.setGoodsAndServicesClass(stampRequest.getStamp().getGoodsAndServicesClass());
        stampData.setGoodsAndServicesClass(stampRequest.getStamp().getGoodsAndServicesClass());

        Priority priority = new Priority();
        if(stampRequest.getStamp().getPriority().equals("")){
            priority.setCheckbox(Checkbox.NE);
            priority.setReason("");
        }else{
            priority.setCheckbox(Checkbox.DA);
            priority.setReason(stampRequest.getStamp().getPriority());
        }
        stampData.setPriority(priority);
        return stampData;
    }


    private GlobalEntity createCommonRepresentative(RequestForStampDTO stampRequest) throws NotValidException {
        GlobalEntity lowyer = createGlobalEntity(stampRequest.getCommonRepresentative(), RDFConstants.commonRepresentativePropertyName,
                RDFConstants.commonRepresentativePropertySurname, RDFConstants.commonRepresentativePropertyBusinessName);
        return lowyer;
    }

    private GlobalEntity createLawyer(RequestForStampDTO stampRequest) throws NotValidException {
        GlobalEntity lowyer = createGlobalEntity(stampRequest.getLowyer(), RDFConstants.lawyerPropertyName,
                RDFConstants.lawyerPropertySurName, RDFConstants.lawyerPropertyBusinessName);
        return lowyer;
    }


    private List<GlobalEntity> createApplicantsData(RequestForStampDTO stampRequest) throws NotValidException {
        List<GlobalEntity> applicants = new ArrayList<>();

        for(EntityDTO entityDTO :stampRequest.getApplicants()){
            applicants.add(createGlobalEntity(entityDTO, RDFConstants.applicantPropertyName,
                    RDFConstants.applicantPropertySurname, RDFConstants.applicantPropertyBusinessName));
        }
        return applicants;
    }



    private GlobalEntity createGlobalEntity(EntityDTO entity, String submitterPropertyName, String submitterPropertySurname, String submitterPropertyBusinessName) throws NotValidException {
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
            System.out.println(entity.getBusinessName());
            result.setBusinessName(createStringPredicate(entity.getBusinessName(), submitterPropertyBusinessName));
            result.setAddress(createAddressFromAddressDTO(entity));
            result.setContact(createContact(entity.getEmail(), entity.getFax(), entity.getPhone()));
            return result;
        }
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

    private String generateDocumentId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("Ž-");
        /*sb.append(now.getDayOfMonth());
        sb.append(now.getMonth());*/
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("-");
        String year = String.valueOf(now.getYear()).substring(2, 4);
        sb.append(year);
        return sb.toString();
    }

}
