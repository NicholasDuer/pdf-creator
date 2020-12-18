public class PDFCreator {

  private final InstructionReader reader;
  private final PDFWriter pdfWriter;

  public PDFCreator(InstructionReader reader, PDFWriter pdfWriter) {
    this.reader = reader;
    this.pdfWriter = pdfWriter;
  }

  public void createPDF(String pdfName) {
    if (!pdfWriter.createDocument(pdfName)) {
      throw new PDFException("Failed to create new PDF");
    }
    pdfWriter.closeDocument();
  }
}
