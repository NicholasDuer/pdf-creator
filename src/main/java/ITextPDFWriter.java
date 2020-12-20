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

/**
 * Implementation of the PDFWriter interface which communicates with
 * iText7's API to format PDFs. The writer only tracks one document at a time.
 * To edit two different documents using only one PDFWriter class, must close one
 * document before opening another. The object uses TimesNewRoman as its font.
 */
public class ITextPDFWriter implements PDFWriter {

  private boolean documentIsOpen;
  private Document document;

  /* Divided by 254 because it seems that iText7 measures in inches.
   * As a result, each indent unit will indent by approximately the size of
   * the text: WWWW */
  private final static float INDENT_UNIT =
      getDefaultFont().getWidth("WWWW") / (float) 254;

  private final static float LARGE_FONT_SIZE = 23;
  private final static float REGULAR_FONT_SIZE = 13;

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
      /* Have to add the current paragraph to the PDF document as we cannot
       * rely on the user starting a new empty paragraph before closing
       * the document. */
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
        currentParagraph.setMarginLeft(currentIndent * INDENT_UNIT);
        break;
      case ITALIC:
        currentFont = getItalicFont();
        break;
      case PARAGRAPH:
        addParagraph();
        currentParagraph = new Paragraph();
        break;
      default:
        throw new PDFException(
            "Invalid command given to pdf writer : " + command.getCommandType()
                .toString());
    }
  }
}
