package kraptis91.maritime.retriever.exception;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public class RetrieverException extends Exception {

  public RetrieverException() {}

  public RetrieverException(String mes) {
    super(mes);
  }

  public RetrieverException(String mes, Throwable t) {
    super(mes, t);
  }

  public RetrieverException(Throwable t) {
    super(t);
  }
}
