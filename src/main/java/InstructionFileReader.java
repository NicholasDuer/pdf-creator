import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the instruction reader interface. Reads instructions
 * from a text file and translates a plain text instruction into an Instruction
 * Object. Instructions are read line by line.
 */
public class InstructionFileReader implements InstructionReader {

  private BufferedReader reader;

  /**
   * Creates an InstructionFileReader which reads instructions from the given
   * input file.
   * @param instructionFile Path to the text file to be read.
   */
  public InstructionFileReader(String instructionFile) {
    try {
      reader = new BufferedReader(new FileReader(instructionFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Instruction> readInstructions() {
    try {
      List<Instruction> instructions = new LinkedList<>();
      String line = reader.readLine();

      while (line != null) {

        if (line.length() == 0) {
          continue;
        }

        if (line.charAt(0) == '.') {
          /* Command instruction */
          Command command = parseCommand(line.substring(1));

          instructions.add(Instruction.withCommand(command));

        } else {
          /* Text instruction */
          instructions.add(Instruction.withText(line));
        }

        line = reader.readLine();

      }

      reader.close();
      return instructions;

    } catch (IOException e) {

      e.printStackTrace();
      return new ArrayList<>();
    }

  }

  private Command parseCommand(String line) {
    String[] tokens = line.split(" ");

    if (tokens.length == 1) {
      try {
        return Command.withCommandType(CommandType.valueOf(tokens[0].toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid command specified : " + tokens[0]);
      }
    }

    if (tokens.length == 2) {
      if (tokens[0].equals("indent")) {

        try {
          return Command.indentByAmount(Integer.parseInt(tokens[1]));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid argument to indent specified : " + tokens[1]);
        }

      } else {
        throw new IllegalArgumentException("Invalid command specified : " + tokens[0]);
      }
    }

    /* More than 2 tokens in the command */
    throw new IllegalArgumentException("Invalid command specified :" + Arrays
        .toString(tokens));
  }
}
