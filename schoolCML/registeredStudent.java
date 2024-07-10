import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class registeredStudent {
    private String username;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private Date dateOfBirth;
    private Integer school_registration_number;
    private String imageFile;

    public registeredStudent(String username, String firstname, String lastname, String emailAddress, Date dateOfBirth, Integer school_registration_number, String imageFile) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.school_registration_number = school_registration_number;
        this.imageFile = imageFile;
    }
    
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Integer getSchool_registration_number() {
        return school_registration_number;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSchool_registration_number(Integer school_registration_number) {
        this.school_registration_number = school_registration_number;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public void saveStudent() {
        try {
            File file = new File("students.xlsx");
            if (!file.exists()) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Students");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("Username");
                headerRow.createCell(2).setCellValue("First Name");
                headerRow.createCell(3).setCellValue("Last Name");
                headerRow.createCell(4).setCellValue("Email Address");
                headerRow.createCell(5).setCellValue("Date of Birth");
                headerRow.createCell(6).setCellValue("School Registration Number");
                headerRow.createCell(7).setCellValue("Image File");
                FileOutputStream outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                outputStream.close();
            }

            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheet("Students");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);
            newRow.createCell(0).setCellValue(lastRowNum + 1);
            newRow.createCell(1).setCellValue(username);
            newRow.createCell(2).setCellValue(firstname);
            newRow.createCell(3).setCellValue(lastname);
            newRow.createCell(4).setCellValue(emailAddress);
            newRow.createCell(5).setCellValue(dateOfBirth.toString());
            newRow.createCell(6).setCellValue(school_registration_number);
            newRow.createCell(7).setCellValue(imageFile);

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();

            FileWriter writer = new FileWriter("students.txt", true);
            writer.write(username + "," + firstname + "," + lastname + "," + emailAddress + "," + dateOfBirth + "," + school_registration_number + "," + imageFile + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
