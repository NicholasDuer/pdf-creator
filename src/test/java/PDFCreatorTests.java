import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

public class PDFCreatorTests {

  public Mockery context = new Mockery();

  InstructionReader reader = context.mock(InstructionReader.class);
  PDFWriter writer = context.mock(PDFWriter.class);

  PDFCreator pdfCreator = new PDFCreator(reader, writer);
  String DUMMY_PDF_NAME = "dummy.pdf";
  String DUMMY_INPUT_TEXT_1 = "Sample sentence 1.";
  Command BOLD_COMMAND = Command.withCommandType(CommandType.BOLD);

  @Test
  public void createsAndClosesPDFDocument() {
    context.checking(new Expectations() {{
      oneOf(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(reader).hasNext();
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

  @Test
  public void queriesReaderUntilStreamHasBeenFullyRead() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      ignoring(writer).executeCommand(BOLD_COMMAND);
      oneOf(reader).hasNext();
      will(returnValue(true));
      oneOf(reader).next(); will(returnValue(Instruction.withCommand(BOLD_COMMAND)));
      oneOf(reader).hasNext();
      will(returnValue(true));
      oneOf(reader).next(); will(returnValue(Instruction.withCommand(BOLD_COMMAND)));
      oneOf(reader).hasNext();
      will(returnValue(false));
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

  @Test
  public void canWriteTextToDocument() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      oneOf(reader).hasNext();
      will(returnValue(true));
      oneOf(reader).next();
      will(returnValue(Instruction.withText(DUMMY_INPUT_TEXT_1)));
      oneOf(writer).writeText(DUMMY_INPUT_TEXT_1);
      will(returnValue(true));
      ignoring(reader).hasNext();
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

  @Test
  public void throwsExceptionUponWriteFailure() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      oneOf(reader).hasNext();
      will(returnValue(true));
      oneOf(reader).next();
      will(returnValue(Instruction.withText(DUMMY_INPUT_TEXT_1)));
      oneOf(writer).writeText(DUMMY_INPUT_TEXT_1);
      will(returnValue(false));
    }});

    try {
      pdfCreator.createPDF(DUMMY_PDF_NAME);

      /* Should have thrown an exception */
      fail();
    } catch (PDFException e) {
      Assert.assertThat(e.getMessage(),
          containsString("Failed to write text to PDF"));
    }
  }

  @Test
  public void correctCommandIsPassedToWriter() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      oneOf(reader).hasNext();
      will(returnValue(true));
      oneOf(reader).next();
      will(returnValue(Instruction.withCommand(BOLD_COMMAND)));
      oneOf(writer).executeCommand(BOLD_COMMAND);
      ignoring(reader).hasNext();
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }


}
