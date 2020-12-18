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
}
