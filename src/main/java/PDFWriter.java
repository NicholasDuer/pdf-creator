public interface PDFWriter {

  boolean createDocument(String pdfName);

  void closeDocument();

  boolean writeText(String text);

  void executeCommand(Command command);
}
