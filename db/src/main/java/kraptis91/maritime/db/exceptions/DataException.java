package kraptis91.maritime.db.exceptions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 27/12/2020.
 */
public class DataException extends Exception {

    public DataException() {
    }

    public DataException(String mes) {
        super(mes);
    }

    public DataException(String mes, Throwable t) {
        super(mes, t);
    }

    public DataException(Throwable t) {
        super(t);
    }
}
