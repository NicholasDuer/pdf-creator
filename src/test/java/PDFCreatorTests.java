import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

public class PDFCreatorTests {

  public Mockery context = new Mockery();

  InstructionReader reader = new DummyReader();
  PDFWriter writer = context.mock(PDFWriter.class);

  PDFCreator pdfCreator = new PDFCreator(reader, writer);
  String DUMMY_PDF_NAME = "dummy.pdf";

  @Test
  public void createsAndClosesPDFDocument() {
    context.checking(new Expectations() {{
      oneOf(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      oneOf(writer).closeDocument();
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

  @Test
  public void throwsExceptionUponFailureToCreateDocument() {
    context.checking(new Expectations() {{
      oneOf(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(false));
    }});

    try {
      pdfCreator.createPDF(DUMMY_PDF_NAME);

      /* Should have thrown an exception */
      fail();
    } catch (PDFException e) {
      Assert.assertThat(e.getMessage(),
          containsString("Failed to create new PDF"));
    }
  }

  private class DummyReader implements InstructionReader {

  }

}
