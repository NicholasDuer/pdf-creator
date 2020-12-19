import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class InstructionFileReaderTests {

  @Test
  public void translatesTextInputIntoWriteInstruction() {
    final List<Instruction> REQUIRED_TRANSLATION = new ArrayList<>() {{
      add(Instruction.withText("“Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor\n"));
      add(Instruction.withText("incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud\n"));
      add(Instruction.withText("exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure\n"));
      add(Instruction.withText("dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\n"));
      add(Instruction.withText("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit\n"));
      add(Instruction.withText("anim id est laborum.\n"));
    }};

    String filePath = new File("").getAbsolutePath().concat("\\src\\test\\test-input\\file-reader-text.txt");
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

    String filePath = new File("").getAbsolutePath().concat("\\src\\test\\test-input\\file-reader-commands.txt");
    InstructionFileReader fileReader = new InstructionFileReader(filePath);

    List<Instruction> translatedInstructions = fileReader.readInstructions();
    assertEquals(REQUIRED_TRANSLATION, translatedInstructions);
  }

}
