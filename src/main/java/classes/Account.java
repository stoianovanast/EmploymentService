package classes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Account {
    private String PIB;
    private String address;
    private String reasonForDismissal;
    private String speacilty;
    private String phone;
    private Passport passport;
    private String lastJob;

    public String getPIB() {
        return PIB;
    }
    public String getLastJob() {
        return lastJob;
    }

    public String getReasonForDismissal() {
        return reasonForDismissal;
    }
    public String getSpecialty() {
        return speacilty;
    }

    public String getPhone() {
        return phone;
    }
    public Passport getPassport() {
        return passport;
    }
    public boolean isExistance(AllAccount allAccount, Passport passport, String PIB){
        boolean resultOfChecking = allAccount.isAccount(passport);
        if(!allAccount.isAccount(passport)){
            this.passport = passport;
            this.PIB = PIB;
        }
        return resultOfChecking;
    }
    public boolean isAddress( String address, AddressMap addressMap){
        this.address = address;
        return addressMap.isAddress(address);
    }
    public void setDate(AllAccount allAccount, EmployService employService,
                        String lastJob, String reasonForDismissal, String specialty){
        this.lastJob = lastJob;
        this.reasonForDismissal = reasonForDismissal;
        this.speacilty  = specialty;
        allAccount.markPassport(this.passport);
        print(employService);
    }
    public void setDate(AllAccount allAccount, EmployService employService,
                        String lastJob, String reasonForDismissal, String specialty, String phone){
        this.lastJob = lastJob;
        this.reasonForDismissal = reasonForDismissal;
        this.speacilty  = specialty;
        this.phone = phone;
        allAccount.markPassport(this.passport);
        print(employService);
    }
    public void print(EmployService employService) {
        String fileName = "документПроРеєстрацію.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addCenteredParagraph(document, "Документ про реєстрацію");

            String registrationInfo = String.format("Безробітного (%s) взято на облік. Реєстрацію провів головний спеціаліст: %s, %s.",
                    PIB,employService.getPIB_specialis(), employService.getPhone_specialis());
            addParagraph(document, registrationInfo);

            String centerInfo = String.format("Служба зайнятості: центр №%s, %s", employService.getId_center(), employService.getAddress_center());
            addParagraph(document, centerInfo);

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void addCenteredParagraph(Document document, String text) throws DocumentException {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        addParagraph(document, " ");
    }
    private void addParagraph(Document document, String text) throws DocumentException {
        Paragraph paragraph = new Paragraph(text);
        document.add(paragraph);
    }
}
