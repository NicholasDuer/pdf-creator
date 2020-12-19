import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class ITextPDFWriter implements PDFWriter {

  private boolean documentIsOpen;
  private Document document;

  private Paragraph currentParagraph;
  private Font font;

  public ITextPDFWriter() {
    documentIsOpen = false;
    currentParagraph = new Paragraph();
    font = getDefaultFont();
  }

  private Font getDefaultFont() {
    return new Font(FontFamily.TIMES_ROMAN, 12);
  }

  @Override
  public boolean createDocument(String pdfName) {
    if (documentIsOpen) {
      System.out.println("PDFWriter is already writing to an open document");
      return false;
    } else {
      try {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfName));
        document.open();
      } catch (IOException | DocumentException e) {
        e.printStackTrace();
        return false;
      }
      documentIsOpen = true;
      return true;
    }
  }

  @Override
  public void closeDocument() {
    if (!documentIsOpen) {
      System.out
          .println("PDFWriter is not currently writing to an open document");
    } else {
      addParagraph();
      document.close();
      documentIsOpen = false;
    }
  }

  @Override
  public void writeText(String text) {
    currentParagraph.setFont(font);
    currentParagraph.add(text);
  }

  private void addParagraph() {
    try {
      document.add(currentParagraph);
      currentParagraph = new Paragraph();
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void executeCommand(Command command) {
    switch (command.getCommandType()) {
      case BOLD:
        font = new Font(font.getFamily(), font.getSize(), Font.BOLD);
        break;
      case FILL:
        break;
      case NOFILL:
        break;
      case REGULAR:
        font = getDefaultFont();
      case INDENT:
        break;
      case ITALIC:
        font = new Font(font.getFamily(), font.getSize(), Font.ITALIC);
        break;
      case PARAGRAPH:
        addParagraph();
        currentParagraph = new Paragraph();
    }
  }
}
