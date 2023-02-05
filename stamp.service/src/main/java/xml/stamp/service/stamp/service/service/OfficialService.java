package xml.stamp.service.stamp.service.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.stamp.service.stamp.service.db.ExistManager;
import xml.stamp.service.stamp.service.dto.FullStampDTO;
import xml.stamp.service.stamp.service.dto.SimpleViewStampDTO;
import xml.stamp.service.stamp.service.dto.request.*;
import xml.stamp.service.stamp.service.fuseki.FusekiReader;
import xml.stamp.service.stamp.service.model.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfficialService {
    @Autowired
    private ExistManager existManager;

    @Autowired
    private FusekiReader fusekiReader;

    @Autowired
    private SolutionService solutionService;


    public List<SimpleViewStampDTO> getListOfStamp() throws Exception {
        List<RequestForStamp> requests = existManager.retrieveCollection();
        List<SimpleViewStampDTO> results = new ArrayList<>();
        for (RequestForStamp requestForStamp : requests) {
            results.add(convertRequestForStampToStampDTO(requestForStamp));
        }
        System.out.println(" Ukupan broj zahteva za prikaz: " + results.size());
        return results;
    }

    private SimpleViewStampDTO convertRequestForStampToStampDTO(RequestForStamp requestForStamp) throws URISyntaxException {
        System.out.println(requestForStamp);
        SimpleViewStampDTO p = new SimpleViewStampDTO();
        boolean has = solutionService.getIfHasSolution(requestForStamp.getStampId());
        System.out.println(has);
        p.setHasSolution(has);
        System.out.println("--------- STAMP -----------");
        System.out.println(p.isHasSolution());
        p.setId(requestForStamp.getStampId());
        p.setApplicationDate(requestForStamp.getStampData().getDateOfApplication().getDate());
        String applicants = "";
        for (int i = 0; i < requestForStamp.getApplicants().size(); i++) {
            applicants += (i + 1) + "." + requestForStamp.getApplicants().get(i).getContact().getEmail() + "\n";
        }
        p.setApplicant(applicants);
        return p;
    }

    public FullStampDTO getStamp(String stampId) throws Exception {
        RequestForStamp request = existManager.retrieve(stampId.replace("/", "-"));
        FullStampDTO stamp = new FullStampDTO();

        List<EntityDTO> applicants = new ArrayList<>();
        System.out.println("ukupno aplikanata: " + request.getApplicants().size());
        for (GlobalEntity global : request.getApplicants()) {
            EntityDTO entityDTO = createEntity(global);
            applicants.add(entityDTO);
        }
        stamp.setId(request.getStampId());
        stamp.setApplicationDate(request.getStampData().getDateOfApplication().getDate());
        boolean has = solutionService.getIfHasSolution(request.getStampId());
        stamp.setHasSolution(has);
        stamp.setApplicants(applicants);
        stamp.setLowyer(createEntity(request.getLawyer()));
        stamp.setCommonRepresentative(createEntity(request.getCommonRepresentative()));
        stamp.setStamp(createStampDTO(request.getStampData()));
        stamp.setRecepient(createRecepientDTO(request.getRecepient()));
        stamp.setAttachmentData(createAttachmentDataDTO(request.getAttachmentData()));
        stamp.setBaseTax(request.getTaxesData().getBaseTax());
        stamp.setClassTax(request.getTaxesData().getClassTax());
        stamp.setGraphicSolutionTax(request.getTaxesData().getGraphicSolutionTax());
        stamp.setTotalTax(request.getTaxesData().getTotalTax());
        System.out.println(stamp);
        return stamp;

    }

    private EntityDTO createEntity(GlobalEntity global) {
        EntityDTO entityDTO = new EntityDTO();
        entityDTO.setFax(global.getContact().getFaxNumber());
        entityDTO.setPhone(global.getContact().getPhoneNumber());
        entityDTO.setEmail(global.getContact().getEmail());
        entityDTO.setCity(global.getAddress().getCity());
        entityDTO.setStreet(global.getAddress().getStreet());
        entityDTO.setNumber(global.getAddress().getNumber());
        entityDTO.setCountry(global.getAddress().getCountry());
        entityDTO.setPostalNumber(global.getAddress().getPostNumber());
        if (global instanceof Person) {
            entityDTO.setName(((Person) global).getName().getText());
            entityDTO.setSurname(((Person) global).getSurname().getText());
            entityDTO.setCitizenship(((Person) global).getCitizenship());
            entityDTO.setPerson(true);
        } else {
            entityDTO.setBusinessName(((LegalEntity) global).getBusinessName().getText());
            entityDTO.setPerson(false);
        }
        return entityDTO;
    }

    private StampDTO createStampDTO(StampData stampData) {
        StampDTO stampDTO = new StampDTO();
        stampDTO.setType(stampData.getType().toString());
        stampDTO.setKind(stampData.getKind().toString());
        stampDTO.setColors(stampData.getColors());
        stampDTO.setDescription(stampData.getDescriptionStamp());
        stampDTO.setGoodsAndServicesClass(stampData.getGoodsAndServicesClass());
        stampDTO.setPriority(stampData.getPriority().getReason());
        stampDTO.setTransliteration(stampData.getTransliterationStamp());
        stampDTO.setTranslation(stampData.getTranslateStamp());
        stampDTO.setImage(stampData.getImagePath());
        return stampDTO;
    }

    private RecepientDTO createRecepientDTO(Recepient recepient) {
        RecepientDTO recepientDTO = new RecepientDTO();
        recepientDTO.setName(recepient.getName());
        return recepientDTO;
    }

    private AttachmentDataDTO createAttachmentDataDTO(AttachmentData data) {
        AttachmentDTO exampleStamp = new AttachmentDTO(data.getExampleStamp().getPath(), data.getExampleStamp().getSubmittedAttachment().toString(), "1", "Primerak znaka");
        AttachmentDTO listOfGoodsAndServices = new AttachmentDTO(data.getListOfGoodsAndServices().getPath(), data.getListOfGoodsAndServices().getSubmittedAttachment().toString(), "2", "Spisak robe i usluga");
        AttachmentDTO authority = new AttachmentDTO(data.getAuthority().getPath(), data.getAuthority().getSubmittedAttachment().toString(), "3", "Punomoćje");
        AttachmentDTO generalAuthorityAddedBefore = new AttachmentDTO(data.getGeneralAuthorityAddedBefore().getPath(), data.getGeneralAuthorityAddedBefore().getSubmittedAttachment().toString(), "4", "Generalno punomoćje ranije priloženo");
        AttachmentDTO authorityWillBeAddedAfter = new AttachmentDTO(data.getAuthorityWillBeAddedAfter().getPath(), data.getAuthorityWillBeAddedAfter().getSubmittedAttachment().toString(), "5", "Punomoćje će biti naknadno dostavljeno");
        AttachmentDTO generalActOnCollectiveStampOrGuaranteeStamp = new AttachmentDTO(data.getGeneralActOnCollectiveStampOrGuaranteeStamp().getPath(), data.getGeneralActOnCollectiveStampOrGuaranteeStamp().getSubmittedAttachment().toString(), "6", "Opšti akt o kolektivnom/žigu garancije");
        AttachmentDTO proofOfRightOfPriority = new AttachmentDTO(data.getProofOfRightOfPriority().getPath(), data.getProofOfRightOfPriority().getSubmittedAttachment().toString(), "6", "Dokaz o pravu prvenstva");
        AttachmentDTO proofOfTaxPayment = new AttachmentDTO(data.getProofOfTaxPayment().getPath(), data.getProofOfTaxPayment().getSubmittedAttachment().toString(), "7", "Dokaz o uplati takse");

        AttachmentDataDTO attachmentDataDTO = new AttachmentDataDTO(exampleStamp, listOfGoodsAndServices, authority, generalAuthorityAddedBefore, authorityWillBeAddedAfter, generalActOnCollectiveStampOrGuaranteeStamp, proofOfRightOfPriority, proofOfTaxPayment);
        return attachmentDataDTO;
    }

    public List<SimpleViewStampDTO> getListOfPatentSearched(List<String> searchBy) throws Exception {
        List<RequestForStamp> requests = existManager.retrieveCollection();
        List<SimpleViewStampDTO> result = new ArrayList<>();
        for (RequestForStamp request : requests) {
            if (request.contains(searchBy)) {
                result.add(convertRequestForStampToStampDTO(request));
            }
        }
        return result;
    }

}
