package kraptis91.maritime.enums;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public enum MongoDBCollection {
  OCEAN_CONDITIONS("ocean-conditions");

  private final String name;

  MongoDBCollection(String name) {
    this.name = name;
  }

  public String getCollectionName() {
    return name;
  }
}
