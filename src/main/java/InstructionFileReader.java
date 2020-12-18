import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InstructionFileReader implements InstructionReader {

  private BufferedReader reader;
  private String line;

  public InstructionFileReader(String instructionFile) {
    try {
      reader = new BufferedReader(new FileReader(instructionFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Instruction> readInstructions() {
    return null;
  }
}
