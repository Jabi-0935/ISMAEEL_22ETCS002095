import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.borders.SolidBorder;

public class ResumeBuilder {

    private JFrame frame;
    private JTextField nameField;
    private JTextField jobTitleField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField linkedinField;
    private JTextArea summaryArea;
    private JTextArea educationArea;
    private JTextArea experienceArea;
    private JTextArea skillsArea;
    private JTextArea certificationsArea;
    private JTextArea projectsArea;
    private JTextArea interestsArea;

    public ResumeBuilder() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Programming Paradigms");
        frame.setBounds(100, 100, 600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased padding
        panel.setBackground(java.awt.Color.WHITE); // Set background color
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 2, 10, 15)); // Adjusted row and column gaps

        Font labelFont = new Font("Arial", Font.BOLD, 14); // Font for labels
        Font fieldFont = new Font("Arial", Font.PLAIN, 14); // Font for text fields and areas

        // Labels and TextFields
        addLabelAndField(panel, "Name:", nameField = createTextField(), labelFont, fieldFont);
        addLabelAndField(panel, "Job Title:", jobTitleField = createTextField(), labelFont, fieldFont);
        addLabelAndField(panel, "Email:", emailField = createTextField(), labelFont, fieldFont);
        addLabelAndField(panel, "Phone:", phoneField = createTextField(), labelFont, fieldFont);
        addLabelAndField(panel, "Address:", addressField = createTextField(), labelFont, fieldFont);
        addLabelAndField(panel, "LinkedIn Profile:", linkedinField = createTextField(), labelFont, fieldFont);

        addLabelAndTextArea(panel, "Summary:", summaryArea = createTextArea(), labelFont, fieldFont);
        addLabelAndTextArea(panel, "Education:", educationArea = createTextArea(), labelFont, fieldFont);
        addLabelAndTextArea(panel, "Work Experience:", experienceArea = createTextArea(), labelFont, fieldFont);
        addLabelAndTextArea(panel, "Skills:", skillsArea = createTextArea(), labelFont, fieldFont);
        addLabelAndTextArea(panel, "Certifications:", certificationsArea = createTextArea(), labelFont, fieldFont);
        addLabelAndTextArea(panel, "Projects:", projectsArea = createTextArea(), labelFont, fieldFont);
        addLabelAndTextArea(panel, "Interests:", interestsArea = createTextArea(), labelFont, fieldFont);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Top padding
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Generate PDF button
        JButton generateButton = new JButton("Generate PDF");
        generateButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Larger, bold font
        generateButton.setBackground(java.awt.Color.BLUE); // Blue background
        generateButton.setFocusPainted(false); // Remove focus border
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    generatePDF();
                    JOptionPane.showMessageDialog(frame, "Resume Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Incomplete", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(generateButton);

        // Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
        clearButton.setBackground(java.awt.Color.RED); // Light gray background
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        buttonPanel.add(clearButton);

        frame.setVisible(true);
    }

    private boolean validateInputs() {
        // Validate all fields are filled
        return !nameField.getText().isEmpty() &&
                !jobTitleField.getText().isEmpty() &&
                !emailField.getText().isEmpty() &&
                !phoneField.getText().isEmpty() &&
                !addressField.getText().isEmpty() &&
                !linkedinField.getText().isEmpty() &&
                !summaryArea.getText().isEmpty() &&
                !educationArea.getText().isEmpty() &&
                !experienceArea.getText().isEmpty() &&
                !skillsArea.getText().isEmpty() &&
                !certificationsArea.getText().isEmpty() &&
                !projectsArea.getText().isEmpty() &&
                !interestsArea.getText().isEmpty();
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        return textArea;
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField textField, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label);

        textField.setFont(fieldFont);
        panel.add(textField);
    }

    private void addLabelAndTextArea(JPanel panel, String labelText, JTextArea textArea, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label);

        textArea.setFont(fieldFont);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
    }

    private void clearFields() {
        // Clear all text fields and text areas
        nameField.setText("");
        jobTitleField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        linkedinField.setText("");
        summaryArea.setText("");
        educationArea.setText("");
        experienceArea.setText("");
        skillsArea.setText("");
        certificationsArea.setText("");
        projectsArea.setText("");
        interestsArea.setText("");
    }

    private void generatePDF() {
        String name = nameField.getText();
        String jobTitle = jobTitleField.getText();
        String email = emailField.getText();
        long phone = 0;  // Default value if parsing fails
        try {
            phone = Long.parseLong(phoneField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,
                    "Please enter a valid phone number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;  // Exit the method
        }
        String address = addressField.getText();
        String linkedin = linkedinField.getText();
        String summary = summaryArea.getText();
        String education = educationArea.getText();
        String experience = experienceArea.getText();
        String skills = skillsArea.getText();
        String certifications = certificationsArea.getText();
        String projects = projectsArea.getText();
        String interests = interestsArea.getText();

        // Call the method to generate PDF with retrieved values
        generatePDF(name, jobTitle, email, phone, address, linkedin, summary, education, experience, skills, certifications, projects, interests);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                         | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                ResumeBuilder window = new ResumeBuilder();
                window.show();
            }
        });

    }

    private static void generatePDF(String name, String jobTitle, String email, long phone, String address, String linkedin,
                                    String summary, String education, String experience, String skills, String certifications,
                                    String projects, String interests) {
        String dest = "resume.pdf";
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Theme Color (Blue)
            DeviceRgb themeColor = new DeviceRgb(0, 102, 204);

            // Fonts
            PdfFont bold = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            PdfFont regular = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

            // Name
            Paragraph nameHeader = new Paragraph(name)
                    .setFont(bold)
                    .setFontSize(20)
                    .setFontColor(DeviceRgb.BLUE) // Set text color to white
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(5);
            document.add(nameHeader);

            // Job Title with Line
            Paragraph jobTitleParagraph = new Paragraph(jobTitle)
                    .setFont(bold)
                    .setFontSize(10)
                    .setFontColor(DeviceRgb.BLUE) // Set text color to white
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            // Add a line below the job title
            jobTitleParagraph.setBorderBottom(new SolidBorder(themeColor, 2));
            document.add(jobTitleParagraph);

            // Contact Information Section
            addSection(document, "Contact Information", String.format("Email: %s\nPhone: %d\nAddress: %s", email, phone, address), regular, themeColor);

            // Summary Section
            addSection(document, "Summary", summary, regular, themeColor);

            // Education Section
            addSection(document, "Education", education, regular, themeColor);

            // Work Experience Section
            addSection(document, "Work Experience", experience, regular, themeColor);

            // Skills Section
            addSection(document, "Skills", skills, regular, themeColor);

            // Certifications Section
            addSection(document, "Certifications", certifications, regular, themeColor);

            // Projects Section
            addSection(document, "Projects", projects, regular, themeColor);

            // Interests Section
            addSection(document, "Interests", interests, regular, themeColor);

            // Close the document
            document.close();
            System.out.println("PDF Created Successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addSection(Document document, String title, String content, PdfFont font, Color color) {
        // Section Title
        Paragraph titleParagraph = new Paragraph(title)
                .setFont(font)
                .setFontSize(12)
                .setFontColor(DeviceRgb.WHITE) // Set text color to white
                .setBackgroundColor(new DeviceRgb(0, 102, 204)) // Blue background color
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(3) // Padding inside the title background
                .setMarginBottom(7); // Adjusted margin

        document.add(titleParagraph);

        // Section Content
        Paragraph contentParagraph = new Paragraph(content)
                .setFont(font)
                .setMarginBottom(13); // Adjusted smaller margin here
        document.add(contentParagraph);

        contentParagraph.setBorder(new SolidBorder(color, 1)); // 1pt solid border
        contentParagraph.setPadding(10);
    }
}
