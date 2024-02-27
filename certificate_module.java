import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class DatabaseToPDF {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT column1, column2 FROM your_table";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Create PDF document
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                // Write to PDF
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    int row = 1;
                    while (resultSet.next()) {
                        String column1Value = resultSet.getString("roll_number");
                        String column2Value = resultSet.getString("name");
                        String column3Value = resultSet.getString("branch");
                        String column4Value = resultSet.getString("college");

                        // Write data to PDF
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset(50, 700 - row * 20);
                        contentStream.showText("Column 1: " + column1Value + ", Column 2: " + column2Value);
                        contentStream.endText();

                        row++;
                    }
                }

                // Save PDF
                document.save("output.pdf");
                document.close();
                System.out.println("PDF created successfully.");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
