package xml.authorship.service.authorship.service.service;

import org.apache.jena.fuseki.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import xml.authorship.service.authorship.service.beans.*;
import xml.authorship.service.authorship.service.db.ExistManager;
import xml.authorship.service.authorship.service.dto.*;
import xml.authorship.service.authorship.service.fuseki.FusekiReader;
import xml.authorship.service.authorship.service.fuseki.FusekiWriter;
import xml.authorship.service.authorship.service.fuseki.MetadataExtractor;
import xml.authorship.service.authorship.service.jaxb.LoaderValidation;
import xml.authorship.service.authorship.service.repository.AuthorshipRepository;
import xml.authorship.service.authorship.service.util.RDFConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static xml.authorship.service.authorship.service.util.RDFConstants.submitterPropertySurname;
import static xml.authorship.service.authorship.service.util.SparqlUtil.*;

@Service
public class AuthorshipRequestService {

    @Autowired
    private AuthorshipRepository authorshipRepository;

    @Autowired
    private LoaderValidation loader;

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private FusekiWriter fusekiWriter;

    @Autowired
    private FusekiReader fusekiReader;

    @Autowired
    private ExistManager existManager;

    @Value("${main.service.url}")
    private String basicUrl;

    final static String EXAMPLES_PATH = "src/main/resources/static/examples/";

    final static String DESCRIPTIONS_PATH = "src/main/resources/static/descriptions/";

    public String saveAuthorshipRequest(AuthorshipRequest authorshipRequest) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        os = this.loader.marshalling(authorshipRequest, os);
        authorshipRepository.saveRequest(os, authorshipRequest.getAuthorshipId());
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream = metadataExtractor.extractMetadata(os.toString(), outputStream);
        fusekiWriter.saveRDF(outputStream);
        return "nestooo";
    }

    public void saveAuthorshipRequest(AuthorshipRequestDTO request) throws Exception {
        AuthorshipRequest newRequest = new AuthorshipRequest();

        newRequest.setAuthorshipData(createAuthorshipData());
        newRequest.setRecipient(createRecipient());

        newRequest.setSubmitter(createSubmitter(request));
        newRequest.setCommissioner(createCommissioner(request));
        if (!request.isSubmitterIsAuthor())
            newRequest.setAuthors(createAuthor(request));
        newRequest.setAuthorsWork(createAuthorsWork(request.getAuthorsWork()));

        newRequest.setAttachments(createAttachments(request.getAttachments()));

        newRequest.setAbout(RDFConstants.baseURI + newRequest.getAuthorshipId());
        newRequest.setVocab(RDFConstants.vocabURI);

        this.saveAuthorshipRequest(newRequest);
    }

    private Attachments createAttachments(AttachmentsDTO dto) {
        Attachments attachments = new Attachments();
        attachments.setExamplePath(dto.getExamplePath());
        attachments.setDescriptionPath(dto.getDescriptionPath());
        return attachments;
    }

    private AuthorsWork createAuthorsWork(AuthorsWorkDTO authorsWorkDTO) {
        AuthorsWork authorsWork = new AuthorsWork();
        authorsWork.setTitle(createStringPredicate(authorsWorkDTO.getTitle(), RDFConstants.titleProperty));
        authorsWork.setWorkType(convertToWorkType(authorsWorkDTO.getType()));
        authorsWork.setAlternateTitle(authorsWorkDTO.getAlternateTitle());
        authorsWork.setRecordForm(convertToRecordForm(authorsWorkDTO.getForm()));
        authorsWork.setWayOfUsage(authorsWorkDTO.getWayOfUsage());
        if (authorsWorkDTO.isMadeInWorkRelationship())
            authorsWork.setMadeInEmployment(Checkbox.DA);
        else
            authorsWork.setMadeInEmployment(Checkbox.NE);
        if (authorsWorkDTO.isRemade()) {
            RevisedWork revisedWork = new RevisedWork();
            revisedWork.setTitle(authorsWorkDTO.getRemadeTitle());
            revisedWork.setAuthorName(authorsWorkDTO.getName());
            revisedWork.setAuthorSurname(authorsWorkDTO.getSurname());
            authorsWork.setRevisedWork(revisedWork);
        }
        return authorsWork;
    }

    private WorkType convertToWorkType(String type) {
        switch (type) {
            case "Književno djelo":
                return WorkType.KNJIZEVNO_DJELO;
            case "Muzičko djelo":
                return WorkType.MUZICKO_DJELO;
            case "Likovno djelo":
                return WorkType.LIKOVNO_DJELO;
            case "Računarski program":
                return WorkType.RACUNARSKI_PROGRAM;
            default:
                return WorkType.DRUGO;
        }
    }

    private RecordForm convertToRecordForm(String form) {
        switch (form) {
            case "Štampani tekst":
                return RecordForm.STAMPANI_TEKST;
            case "Optički disk":
                return RecordForm.OPTICKI_DISK;
            default:
                return RecordForm.DRUGO;
        }
    }

    private List<Author> createAuthor(AuthorshipRequestDTO request) {
        List<Author> authors = new ArrayList<>();
        if (request.isAnonymusAuthor()) {
            Author author = new Author();
            author.setAnonymousAuthor(Checkbox.DA);
            authors.add(author);
            return authors;
        }
        for (AuthorDTO a : request.getAuthors()) {
            Author author = new Author();
            author.setPseudonym(a.getPseudonym());
            author.setAnonymousAuthor(Checkbox.NE);
            if (a.isAlive()) {
                author.setPerson(createPersonFromAliveAuthorDTO(a));
            }
            else {
                author.setYearOfDeath(a.getYearOfDeath());
                author.setPerson(createPersonFromDeadAuthorDTO(a));
            }
            authors.add(author);
        }
        return authors;
    }

    private Person createPersonFromDeadAuthorDTO(AuthorDTO authorDTO) {
        Person person = new Person();
        person.setName(createStringPredicate(authorDTO.getName(), RDFConstants.authorPropertyName));
        person.setSurname(createStringPredicate(authorDTO.getSurname(), RDFConstants.authorPropertySurname));
        return person;
    }

    private Person createPersonFromAliveAuthorDTO(AuthorDTO authorDTO) {
        Person person = new Person();
        person.setName(createStringPredicate(authorDTO.getName(), RDFConstants.authorPropertyName));
        person.setSurname(createStringPredicate(authorDTO.getSurname(), RDFConstants.authorPropertySurname));
        person.setCitizenship(authorDTO.getCitizenship());
        person.setAddress(createAddressFromAddressDTO(authorDTO));
        return person;
    }

    private Recipient createRecipient() {
        Recipient recipient = new Recipient();
        recipient.setName(RDFConstants.recipientName);
        Address address = new Address();
        address.setCity(RDFConstants.recipientCity);
        address.setCountry(RDFConstants.recipientCountry);
        address.setNumber(RDFConstants.recipientNumber);
        address.setStreet(RDFConstants.recipientStreet);
        address.setPostNumber(RDFConstants.recipientPostalNumber);
        recipient.setAddress(address);
        return recipient;
    }

    private AuthorshipData createAuthorshipData() {
        AuthorshipData data = new AuthorshipData();

        Predicate id = new Predicate();
        id.setText(generateDocumentId());
        id.setDatatype(RDFConstants.stringDatatype);
        id.setProperty(RDFConstants.idProperty);
        data.setID(id);

        DatePredicate applicationDate = new DatePredicate();
        applicationDate.setProperty(RDFConstants.applicationDateProperty);
        applicationDate.setDatatype(RDFConstants.dateDatatype);
        applicationDate.setDate(LocalDate.now());
        data.setApplicationDate(applicationDate);

        return data;
    }

    private String generateDocumentId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("A-");
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("-");
        String year = String.valueOf(now.getYear()).substring(2, 4);
        sb.append(year);
        System.out.println(sb);
        return sb.toString();
    }

    private Commissioner createCommissioner(AuthorshipRequestDTO request) {
        if (request.getCommissioner() == null)
            return null;
        Commissioner commissioner = new Commissioner();
        commissioner.setPerson((Person) createGlobalEntity(request.getCommissioner()));
        return commissioner;
    }

    private Submitter createSubmitter(AuthorshipRequestDTO request) {
        Submitter submitter = new Submitter();
        submitter.setGlobalEntity(createGlobalEntity(request.getSubmitter()));
        if (request.isSubmitterIsAuthor())
            submitter.setSubmitterTheAuthor(Checkbox.DA);
        else
            submitter.setSubmitterTheAuthor(Checkbox.NE);
        return submitter;
    }

    private GlobalEntity createGlobalEntity(EntityDTO entity) {
        if (entity.isPerson()) {
            Person result = new Person();
            result.setName(createStringPredicate(entity.getName(), RDFConstants.submitterPropertyName));
            result.setSurname(createStringPredicate(entity.getSurname(), submitterPropertySurname));
            result.setCitizenship(entity.getCitizenship());
            result.setAddress(createAddressFromAddressDTO(entity));
            result.setContact(createContact(entity.getEmail(), entity.getFax(), entity.getPhone()));
            return result;
        } else {
            LegalEntity result = new LegalEntity();
            System.out.println(entity.getBusinessName());
            result.setBusinessName(createStringPredicate(entity.getBusinessName(), RDFConstants.submitterPropertyName));
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

    private Address createAddressFromAddressDTO(AddressDTO addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setNumber(addressDto.getNumber());
        address.setStreet(addressDto.getStreet());
        address.setPostNumber(addressDto.getPostalNumber());
        return address;
    }

//    public ArrayList<String> searchByMetadata(String name) throws IOException {
//        Map<String, String> params = new HashMap<>();
//        params.put("naziv", name);
//
//        ArrayList<String> result = fusekiReader.executeQuery(params);
//        return result;
//    }

    public String searchMetadataById(String id, String type) {
        String graphUri = "/authorship/metadata";
        if (type.equals(RDF_JSON)) {
            String sparqlCondition = "VALUES ?subject { <" + "http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> }" +
                    " ?subject ?predicate ?object .";
//        String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> ?d ?s .";
            return fusekiReader.getMetadataJson(graphUri, sparqlCondition);
        } else if (type.equals(NTRIPLES)) {
            String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> ?predicate ?object .";
            return fusekiReader.getMetadataRdf(graphUri, sparqlCondition);
        }
        return null;
    }

    public boolean addSolution(NewSolutionDTO dto) throws Exception {
        AuthorshipRequest request = existManager.retrieve(dto.getRequestId());
        dto.setRequestDate(request.getAuthorshipData().getApplicationDate().getDate());
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("*************** add solution ********************");
        ResponseEntity<Boolean> result = restTemplate.postForEntity(uri + "/save", new HttpEntity<>(dto, headers), Boolean.class);
        System.out.println(result.getBody());
        return Boolean.TRUE.equals(result.getBody());
    }

    public SimpleAuthorshipDTO getAuthorshipRequest(String id) throws Exception {
        System.out.println(id);
        AuthorshipRequest request = existManager.retrieve(id);
        SimpleAuthorshipDTO dto = new SimpleAuthorshipDTO();
        dto.setId(request.getAuthorshipId());
        dto.setSubmitter(request.getSubmitter().getGlobalEntity().getContact().getEmail());
        dto.setApplicationDate(request.getAuthorshipData().getApplicationDate().getDate());
        System.out.println("\n\n------------------\n" + request.getAuthorshipData().getApplicationDate().getDate());
        dto.setHasSolution(false); // for now
        return dto;
    }

    public List<SimpleAuthorshipDTO> getAllAuthorshipRequests() throws Exception {
        List<AuthorshipRequest> requests = existManager.retrieveCollection();
        List<SimpleAuthorshipDTO> results = new ArrayList<>();
        for (AuthorshipRequest request : requests) {
            System.out.println(request.toString());
            results.add(convertAuthorshipRequestToDTO(request));
        }
        return results;
    }

    private SimpleAuthorshipDTO convertAuthorshipRequestToDTO(AuthorshipRequest request) throws URISyntaxException {
        SimpleAuthorshipDTO dto = new SimpleAuthorshipDTO();
        dto.setHasSolution(false);      // solutionService.getIfHasSolution(request.getPatentId())
        System.out.println("--------- AUTHORSHIP -----------");
        dto.setId(request.getAuthorshipId());
        dto.setApplicationDate(request.getAuthorshipData().getApplicationDate().getDate());
        dto.setSubmitter(request.getSubmitter().getGlobalEntity().getContact().getEmail());
        return dto;
    }

    public List<SimpleAuthorshipDTO> getListOfAuthorshipSearched(List<String> searchBy) throws Exception {
        List<AuthorshipRequest> requests = existManager.retrieveCollection();
        List<SimpleAuthorshipDTO> result = new ArrayList<>();
        for (AuthorshipRequest request : requests) {
            if (request.contain(searchBy)) {
                result.add(convertAuthorshipRequestToDTO(request));
            }
        }
        return result;
    }

    public List<SimpleAuthorshipDTO> getListOfAuthorshipFiltered(List<FilterDTO> filter) throws Exception {
        HashSet<String> requests = fusekiReader.search(filter);
        ///Stream<String> result = patents.stream().distinct();
        List<SimpleAuthorshipDTO> resultDto = new ArrayList<>();
        for (String elem : requests) {
            resultDto.add(convertAuthorshipRequestToDTO(existManager.retrieve(elem)));
        }
        return resultDto;
    }

    public AttachmentsDTO setExampleFile(MultipartFile[] multiPartFiles) throws IOException {
        String path = addFile(generateFileId(), EXAMPLES_PATH, multiPartFiles, "exampleFile");
        AttachmentsDTO dto = new AttachmentsDTO();
        dto.setExamplePath(path);
        return dto;
    }

    public AttachmentsDTO setDescriptionFile(MultipartFile[] multiPartFiles) throws IOException {
        String path = addFile(generateFileId(), DESCRIPTIONS_PATH, multiPartFiles, "descriptionFile");
        AttachmentsDTO dto = new AttachmentsDTO();
        dto.setDescriptionPath(path);
        return dto;
    }

    public String addFile(String id, String directory, MultipartFile[] multipartFiles, String type) throws IOException {
        if (multipartFiles == null) {
            return "";
        }
        Path path = Paths.get(directory + type + "_" + id);
        return saveFileOnPath(multipartFiles, path);
    }

    private String saveFileOnPath(MultipartFile[] multipartFiles, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        MultipartFile mpf = multipartFiles[0];
        String fileName = mpf.getOriginalFilename();
        Path filePath;
        try (InputStream inputStream = mpf.getInputStream()) {
            filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return filePath.toString();
    }

    private String generateFileId() {
        LocalDateTime now = LocalDateTime.now();
        return String.valueOf(now.getDayOfYear()) +
                now.getHour() +
                now.getMinute() +
                now.getSecond();
    }
}
