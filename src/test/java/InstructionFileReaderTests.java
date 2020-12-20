import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class InstructionFileReaderTests {

  @Test
  public void translatesTextInputIntoWriteInstruction() {
    final List<Instruction> REQUIRED_TRANSLATION = new ArrayList<>() {{
      add(Instruction.withText("“Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor"));
      add(Instruction.withText("incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"));
      add(Instruction.withText("exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure"));
      add(Instruction.withText("dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."));
      add(Instruction.withText("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit"));
      add(Instruction.withText("anim id est laborum."));
    }};

    String filePath = new File("").getAbsolutePath().concat("\\src\\test\\resources\\file-reader-text.txt");
    InstructionFileReader fileReader = new InstructionFileReader(filePath);

    List<Instruction> translatedInstructions = fileReader.readInstructions();
    assertEquals(REQUIRED_TRANSLATION, translatedInstructions);
  }

  @Test
  public void translatesCommandInputIntoExecuteCommandInstructions() {
    final List<Instruction> REQUIRED_TRANSLATION = new ArrayList<>() {{
      add(Instruction.withCommand(Command.withCommandType(CommandType.ITALIC)));
      add(Instruction.withCommand(Command.withCommandType(CommandType.REGULAR)));
      add(Instruction.withCommand(Command.indentByAmount(2)));
      add(Instruction.withCommand(Command.indentByAmount(-3)));
    }};

    String filePath = new File("").getAbsolutePath().concat("\\src\\test\\resources\\file-reader-commands.txt");
    InstructionFileReader fileReader = new InstructionFileReader(filePath);

    List<Instruction> translatedInstructions = fileReader.readInstructions();
    assertEquals(REQUIRED_TRANSLATION, translatedInstructions);
  }

  @Test
  public void throwsExceptionIfAnInvalidCommandIsGiven() {
    String filePath = new File("").getAbsolutePath().concat("\\src\\test\\resources\\file-reader-invalid-command.txt");
    InstructionFileReader fileReader = new InstructionFileReader(filePath);

    try {
      fileReader.readInstructions();

      /* Should have thrown an exception */
      fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), containsString("Invalid command specified : baditalic"));
    }
  }

}
