public enum CommandType {
  LARGE(true),
  PARAGRAPH(true),
  FILL(true),
  NOFILL(true),
  REGULAR(true),
  ITALIC(true),
  BOLD(true),
  INDENT(false);

  private final boolean hasNoParams;

  CommandType(boolean hasNoParams) {
    this.hasNoParams = hasNoParams;
  }

  /**
   *
   * @return True iff the command does not need to come with extra parameters.
   * For example, the indent command requires information about the number of units
   * to indent by.
   */
  boolean hasNoParams() {
    return hasNoParams;
  }
}
