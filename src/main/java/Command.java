/**
 * Object to represent a command. Holds any extra parameters a command may need,
 * such as number of units to indent by in an indent command.
 */
public class Command {

  private final CommandType commandType;
  private final Integer indentAmount;

  private Command(CommandType commandType, Integer indentAmount) {
    this.commandType = commandType;
    this.indentAmount = indentAmount;
  }

  /**
   * Factory method used to build a command which does not require any extra
   * parameters. For example, the bold command does not need any extra
   * information, but the indent command does.
   * @param commandType The command to be executed.
   * @return A command object which just acts a wrapper for our CommandType.
   */
  public static Command withCommandType(CommandType commandType) {
    if (commandType.hasNoParams()) {
      return new Command(commandType, null);
    } else {
      throw new IllegalArgumentException(
          "Specified command requires extra parameters, use a suitable factory method");
    }
  }

  /**
   * Factory method used to build an indent command.
   * @param indentAmount A (positive or negative) integer specifying the number
   *                     of units to indent by upon execution of the indent command.
   * @return A command object which represents our indent command.
   */
  public static Command indentByAmount(Integer indentAmount) {
    return new Command(CommandType.INDENT, indentAmount);
  }

  public CommandType getCommandType() {
    return commandType;
  }

  public Integer getIndentAmount() {
    return indentAmount;
  }


  /* Overriden for testing purposes */
  @Override
  public int hashCode() {
    return commandType.ordinal() * 23;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Command)) {
      return false;
    }

    Command thatCommand = (Command) obj;

    if (getCommandType() != thatCommand.getCommandType()) {
      return false;
    }

    if (getCommandType() == CommandType.INDENT) {
      return getIndentAmount().equals(thatCommand.getIndentAmount());
    }

    return true;
  }
}
