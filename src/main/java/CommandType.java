public enum CommandType {
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

  boolean hasNoParams() {
    return hasNoParams;
  }
}
