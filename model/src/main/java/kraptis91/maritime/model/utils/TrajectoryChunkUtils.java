package kraptis91.maritime.model.utils;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 27/12/2020. */
public class TrajectoryChunkUtils {

  public static final int NUMBER_OF_CHUNKS = 50;

  public static int calcChunkCapacity(int mmsiCounter) {
    return mmsiCounter / NUMBER_OF_CHUNKS;
  }
}
