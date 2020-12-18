public class Instruction {

  private final Command command;
  private final boolean isCommand;
  private final String text;


  private Instruction(Command command, boolean isCommand, String text) {
    this.command = command;
    this.isCommand = isCommand;
    this.text = text;
  }

  public static Instruction withCommand(Command command) {
    return new Instruction(command, true, null);
  }

  public static Instruction withText(String text) {
    return new Instruction(null,false, text);
  }

  public boolean isCommand() {
    return isCommand;
  }

  public String getText() {
    return text;
  }

  public Command getCommand() {
    return command;
  }
}
