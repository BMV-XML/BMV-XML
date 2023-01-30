package xml.stamp.service.stamp.service.model;

public class RDFConstants {
    public static String stringDatatype = "xs:string";
    public static String applicationNumberProperty = "pred:broj_prijave";
    public static String dateDatatype = "xs:date";
    public static String applicationDateProperty = "pred:datum_prijave";
    public static String baseURI = "http://www.ftn.uns.ac.rs/rdf/stamp/";
    public static String vocabURI = "http://www.ftn.uns.ac.rs/rdf/stamp/predicate";

    public static String applicantPropertyName = "pred:ime_podnosilac_prijave";
    public static String applicantPropertySurname = "pred:prezime_podnosilac_prijave";

    public static String applicantPropertyBusinessName = "pred:poslovno_ime";

    public static String lawyerPropertyName = "pred:ime_punomocnik";
    public static String lawyerPropertySurName = "pred:prezime_punomocnik";
    public static String lawyerPropertyBusinessName = "pred:poslovno_ime_punomocnik";

    public static String commonRepresentativePropertyName = "pred:ime_zajednicki_predstavnik";
    public static String commonRepresentativePropertySurname = "pred:prezime_zajednicki_predstavnik";
    public static String commonRepresentativePropertyBusinessName = "pred:poslovno_ime_zajednicki_predstavnik";
    public static String recipientName = "Zavod za registraciju žigova";
    public static String recipientCity = "Novi Sad";
    public static String recipientCountry = "Srbija";
    public static String recipientNumber = "100";
    public static String recipientStreet = "Bulevar oslobodjenja";
    public static String recipientPostalNumber = "21000";
    public static double baseTax = 1000;
    public static double classTax = 400;
    public static double graphicSolutionTax = 500;
}
