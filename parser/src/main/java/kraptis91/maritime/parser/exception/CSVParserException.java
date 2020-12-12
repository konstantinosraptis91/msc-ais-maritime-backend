package kraptis91.maritime.parser.exception;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public class CSVParserException extends Exception {

  public CSVParserException() {}

  public CSVParserException(String mes) {
    super(mes);
  }

  public CSVParserException(String mes, Throwable t) {
    super(mes, t);
  }

  public CSVParserException(Throwable t) {
    super(t);
  }
}
