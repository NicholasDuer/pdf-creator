public class PDFCreator {

  private final InstructionReader reader;
  private final PDFWriter writer;

  public PDFCreator(InstructionReader reader, PDFWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  public void createPDF(String pdfName) {
    if (!writer.createDocument(pdfName)) {
      throw new PDFException("Failed to create new PDF");
    }

    while(reader.hasNext()) {
      Instruction instruction = reader.next();

      if (instruction.isCommand()) {
        writer.executeCommand(instruction.getCommand());
      } else {
        if (!writer.writeText(instruction.getText())) {
          throw new PDFException("Failed to write text to PDF");
        }
      }
    }

    writer.closeDocument();
  }
}
