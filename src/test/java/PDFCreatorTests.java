import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Assert;
import org.junit.Test;

public class PDFCreatorTests {

  public Mockery context = new Mockery();

  InstructionReader reader = context.mock(InstructionReader.class);
  PDFWriter writer = context.mock(PDFWriter.class);

  PDFCreator pdfCreator = new PDFCreator(reader, writer);

  String DUMMY_PDF_NAME = "dummy.pdf";
  String DUMMY_INPUT_TEXT = "Sample sentence 1.";

  Command BOLD_COMMAND = Command.withCommandType(CommandType.BOLD);
  Command INDENT_COMMAND = Command.indentByAmount(5);

  List<Instruction> DUMMY_TEXT_INSTRUCTIONS = new ArrayList<>() {{
    add(Instruction.withText(DUMMY_INPUT_TEXT));
  }};
  List<Instruction> COMMAND_INSTRUCTIONS = new ArrayList<>() {{
    add(Instruction.withCommand(BOLD_COMMAND));
    add(Instruction.withCommand(INDENT_COMMAND));
  }};

  @Test
  public void createsAndClosesPDFDocument() {
    context.checking(new Expectations() {{
      oneOf(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(reader).readInstructions();
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
  public void queriesReaderForListOfInstructions() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      oneOf(reader).readInstructions();
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

  @Test
  public void canWriteTextToDocument() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      ignoring(reader).readInstructions(); will(returnValue(DUMMY_TEXT_INSTRUCTIONS));
      oneOf(writer).writeText(DUMMY_INPUT_TEXT); will(returnValue(true));
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

  @Test
  public void throwsExceptionUponWriteFailure() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      ignoring(reader).readInstructions(); will(returnValue(DUMMY_TEXT_INSTRUCTIONS));
      oneOf(writer).writeText(DUMMY_INPUT_TEXT); will(returnValue(false));
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
  public void correctCommandsArePassedToWriter() {
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      ignoring(reader).readInstructions(); will(returnValue(COMMAND_INSTRUCTIONS));
      oneOf(writer).executeCommand(BOLD_COMMAND);
      oneOf(writer).executeCommand(INDENT_COMMAND);
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

  @Test
  public void commandsArePassedToWriterInCorrectOrder() {
    final Sequence commandSequence = context.sequence("commandSequence");
    context.checking(new Expectations() {{
      ignoring(writer).createDocument(DUMMY_PDF_NAME);
      will(returnValue(true));
      ignoring(writer).closeDocument();
      ignoring(reader).readInstructions(); will(returnValue(COMMAND_INSTRUCTIONS));
      oneOf(writer).executeCommand(BOLD_COMMAND); inSequence(commandSequence);
      oneOf(writer).executeCommand(INDENT_COMMAND); inSequence(commandSequence);
    }});

    pdfCreator.createPDF(DUMMY_PDF_NAME);
  }

}
