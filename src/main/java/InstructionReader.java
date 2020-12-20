import java.util.List;

/**
 * Class is an abstraction of the input stream into PDFCreator.
 * The purpose of any implementing class is to provide a method which returns
 * a list of instructions which would then be supplied to the PDFCreator.
 */
public interface InstructionReader {

  List<Instruction> readInstructions();
}
