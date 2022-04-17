package brickset;

import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }


    /**
     * Checks if the repository contains at least one LegoSet with the given packaging type
     *
     * @param packagingType the analyzed packaging type
     * @return true, if the repository contains at least one LegoSet with the given packaging type, otherwise false
     */
    public boolean containsPackagingType(PackagingType packagingType) {
        var repository = new LegoSetRepository();
        return repository.getAll().stream()
                .anyMatch(legoSet -> legoSet.getPackagingType() == packagingType);

    }

    /**
     * Prints all tags
     */
    public void printAllTags() {
        var repository = new LegoSetRepository();
        repository.getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null)
                .flatMap(legoSet -> legoSet.getTags().stream())
                .distinct()
                .forEach(System.out::println);

    }

    /**
     * Return the number of total pieces
     *
     * @return the number of total pieces
     */
    public int getTotalPieces() {
        var repository = new LegoSetRepository();
        return repository.getAll().stream()
                .map(LegoSet::getPieces)
                .reduce(0, Integer::sum);

    }

    /**
     * Returns LegoSet mapping by packaging type
     *
     * @return LegoSet mapping by packaging type
     */
    public Map<PackagingType, List<LegoSet>> getLegoSetByPackagingType() {
        var repository = new LegoSetRepository();
        return repository.getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getPackagingType));
    }


    /**
     * Returns the number of pieces by theme
     *
     * @return The number of pieces by theme
     */
    public Map<String, Integer> getPiecesByTheme() {
        var repository = new LegoSetRepository();
        return repository.getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getTheme, Collectors.summingInt(LegoSet::getPieces)));
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        System.out.println(repository.containsPackagingType(PackagingType.BLISTER_PACK));
        repository.printAllTags();
        System.out.println(repository.getTotalPieces());
        System.out.println(repository.getLegoSetByPackagingType());
        System.out.println(repository.getPiecesByTheme());
    }
}
