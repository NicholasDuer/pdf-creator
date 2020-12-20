import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import java.io.IOException;

public class ITextPDFWriter implements PDFWriter {

  public static void main(String[] args) {
    ITextPDFWriter writer = new ITextPDFWriter();
    writer.createDocument("my.pdf");
    writer.writeText("nicholas duer");
    writer.executeCommand(Command.withCommandType(CommandType.LARGE));
    writer.writeText(" hello");
    writer.executeCommand(Command.withCommandType(CommandType.REGULAR));
    writer.writeText(" hi there");
    writer.closeDocument();
  }

  private boolean documentIsOpen;
  private Document document;

  private final static float INDENT_UNIT =
      getDefaultFont().getWidth("WWWW") / (float) 1000;
  private final static float LARGE_FONT_SIZE = 20;
  private final static float REGULAR_FONT_SIZE = 12;

  private Paragraph currentParagraph;
  private int currentIndent;
  private PdfFont currentFont;
  private float currentFontSize;

  public ITextPDFWriter() {
    documentIsOpen = false;
    currentParagraph = new Paragraph();
    currentFont = getDefaultFont();
    currentIndent = 0;
    currentFontSize = REGULAR_FONT_SIZE;
  }


  private static PdfFont getDefaultFont() {
    try {
      return PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static PdfFont getBoldFont() {
    try {
      return PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static PdfFont getItalicFont() {
    try {
      return PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean createDocument(String pdfName) {
    if (documentIsOpen) {
      System.out.println("PDFWriter is already writing to an open document");
      return false;
    } else {
      try {
        document = new Document(new PdfDocument(new PdfWriter(pdfName)));
      } catch (IOException e) {
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
    Text formattedText = new Text(text);
    formattedText.setFont(currentFont);
    formattedText.setFontSize(currentFontSize);
    currentParagraph.add(formattedText);
  }

  private void addParagraph() {
    document.add(currentParagraph);
    currentParagraph = new Paragraph();
  }

  @Override
  public void executeCommand(Command command) {
    switch (command.getCommandType()) {
      case LARGE:
        currentFontSize = LARGE_FONT_SIZE;
        break;
      case BOLD:
        currentFont = getBoldFont();
        break;
      case FILL:
        currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
        break;
      case NOFILL:
        currentParagraph.setTextAlignment(TextAlignment.LEFT);
        break;
      case REGULAR:
        currentFontSize = REGULAR_FONT_SIZE;
        currentFont = getDefaultFont();
        break;
      case INDENT:
        currentIndent = Math.max(0, currentIndent + command.getIndentAmount());
        break;
      case ITALIC:
        currentFont = getItalicFont();
        break;
      case PARAGRAPH:
        addParagraph();
        currentParagraph = new Paragraph();
      default:
        throw new PDFException(
            "Invalid command given to pdf writer : " + command.getCommandType()
                .toString());
    }
  }
}
