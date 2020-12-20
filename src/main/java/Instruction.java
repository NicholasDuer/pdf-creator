/**
 * Represents a single instruction given to the PDFCreator. As described in the
 * README, an instruction is either a command or text to be written.
 */
public class Instruction {

  private final Command command;
  private final boolean isCommand;
  private final String text;


  private Instruction(Command command, boolean isCommand, String text) {
    this.command = command;
    this.isCommand = isCommand;
    this.text = text;
  }

  /**
   * Factory method used to build a command instruction.
   * @param command The command to be executed upon execution of the instruction.
   * @return An instruction object which holds the given command.
   */
  public static Instruction withCommand(Command command) {
    return new Instruction(command, true, null);
  }

  /**
   * Factory method used to build a text instruction.
   * @param text The text to be written to the pdf upon execution of the
   *             instruction.
   * @return An instruction object which holds the given text.
   */
  public static Instruction withText(String text) {
    return new Instruction(null, false, text);
  }

  /**
   *
   * @return True if the instruction represent a command to be carried out, false
   * if the instruction represents some text to be written to the pdf.
   */
  public boolean isCommand() {
    return isCommand;
  }

  public String getText() {
    return text;
  }

  public Command getCommand() {
    return command;
  }

  /* Overriden for testing purposes */
  @Override
  public int hashCode() {
    return (isCommand() ? getCommand().hashCode() : getText().hashCode() * 17 );
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Instruction)) {
      return false;
    }
    Instruction thatInstruction = (Instruction) obj;

    if (!isCommand() == thatInstruction.isCommand()) {
      return false;
    }

    if (isCommand()) {
      return getCommand().equals(thatInstruction.getCommand());
    } else {
      return getText().equals(thatInstruction.getText());
    }
  }
}
