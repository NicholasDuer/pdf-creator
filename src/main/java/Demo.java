import java.io.File;

public class Demo {

  private final static String DEFAULT_PATH = new File("").getAbsolutePath()
      .concat("\\src\\main\\demo-input.txt");
  private final static String DEFAULT_PDF_NAME = "demo.pdf";

  public static void main(String[] args) {
    String filePath = DEFAULT_PATH;
    String pdfName = DEFAULT_PDF_NAME;

    if (args.length >= 1) {
      filePath = args[0];
    }

    if (args.length >= 2) {
      pdfName = args[1];
    }

    InstructionReader fileReader = new InstructionFileReader(filePath);
    PDFWriter pdfWriter = new ITextPDFWriter();

    PDFCreator pdfCreator = new PDFCreator(fileReader, pdfWriter);
    pdfCreator.createPDF(pdfName);
  }

}
