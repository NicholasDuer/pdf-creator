import java.util.List;

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

    List<Instruction> instructions = reader.readInstructions();

    for (Instruction instruction : instructions) {
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
