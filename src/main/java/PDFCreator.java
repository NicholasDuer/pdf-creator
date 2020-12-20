import java.util.List;

public class PDFCreator {

  private final InstructionReader reader;
  private final PDFWriter writer;

  /**
   * Constructs a PDFCreator. The PDFCreator will read instructions using the
   * reader object and will format the pdf using the writer object.
   * @param reader Used to create an instruction set from an input stream.
   * @param writer Object which communicates with third party libraries to
   *               format a pdf.
   */
  public PDFCreator(InstructionReader reader, PDFWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  /**
   * Creates a pdf with the given name. The instructions used to create the pdf
   * are the instructions supplied by the reader.
   * @param pdfName Name of the pdf. Name should be of the form <name>.pdf
   */
  public void createPDF(String pdfName) {
    if (!writer.createDocument(pdfName)) {
      throw new PDFException("Failed to create new PDF");
    }

    List<Instruction> instructions = reader.readInstructions();

    for (Instruction instruction : instructions) {
      if (instruction.isCommand()) {
        writer.executeCommand(instruction.getCommand());
      } else {
        writer.writeText(instruction.getText());
      }
    }

    writer.closeDocument();
  }
}
