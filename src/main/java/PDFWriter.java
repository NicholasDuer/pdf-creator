public interface PDFWriter {

  boolean createDocument(String pdfName);

  void closeDocument();

  void writeText(String text);

  void executeCommand(Command command);
}
