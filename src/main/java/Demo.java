import java.io.File;

public class Demo {

  private final static String DEFAULT_INPUT = "demo-input.txt";
  private final static String DEFAULT_PDF_NAME = "demo.pdf";

  public static void main(String[] args) {
    String inputFile = DEFAULT_INPUT;
    String pdfName = DEFAULT_PDF_NAME;

    if (args.length >= 2) {
      inputFile = args[0];
      pdfName = args[1];
    }

    String filePath = new File("").getAbsolutePath()
        .concat("\\src\\main\\resources\\" + inputFile);

    InstructionReader fileReader = new InstructionFileReader(filePath);
    PDFWriter pdfWriter = new ITextPDFWriter();

    PDFCreator pdfCreator = new PDFCreator(fileReader, pdfWriter);
    pdfCreator.createPDF(pdfName);
  }

}
