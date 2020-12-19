public class Command {

  private final CommandType commandType;
  private final Integer indentAmount;

  private Command(CommandType commandType, Integer intdentAmount) {
    this.commandType = commandType;
    this.indentAmount = intdentAmount;
  }

  public static Command withCommandType(CommandType commandType) {
    if (commandType.hasNoParams()) {
      return new Command(commandType, null);
    } else {
      throw new IllegalArgumentException(
          "Specified command requires extra parameters, use a suitable factory method");
    }
  }

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
