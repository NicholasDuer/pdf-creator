public interface PDFWriter {

  boolean createDocument(String pdfName);

  void closeDocument();
}
