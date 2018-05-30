/**
 * The Class TennisMatchesContainer, used as a container for all TennisMatches.
 * @author Jeffery Ceja
 */

public class TennisMatchesContainer implements TennisMatchesContainerInterface{

    private TennisMatch[] matches;
    private int numberOfMatches;

    /**
     * Constructor to create a TennisMatchesContainer with a size of 10 holding 0 matches.
     */
    TennisMatchesContainer() {
        matches = new TennisMatch[10];
        numberOfMatches = 0;
    }

    /**
     * Method used to add a person to the list of matches in the last position
     *
     * if the numberOfMatches is equal to the length of the array increase the size of the array by 2
     *
     * now that we are sure the index fits, assign the match to matches[numberOfMatches]
     * increment the numberOfMatches
     *
     * @param match The TennisMatch to add
     * @throws TennisDatabaseRuntimeException Exception thrown if there is an attempt to access outside of the array
     */
    @Override
    public void insertMatch(TennisMatch match) throws TennisDatabaseRuntimeException {
        if (numberOfMatches == matches.length) {
            TennisMatch[] temporaryMatchHolder = new TennisMatch[matches.length * 2];
            for (int index = 0; index < matches.length; index++)
                temporaryMatchHolder[index] = matches[index];
            matches = temporaryMatchHolder;
        }
        matches[numberOfMatches] = match;
        numberOfMatches++;

    }

    /**
     * call for the sorting of the matches array by date
     * iterate through the array and print the match information
     */
    @Override
    public void printAllMatches() {
        if (numberOfMatches == 0) {
            System.out.println("No matches to print.");
        } else {
            sortDate(matches);
            for (int index = 0; index < numberOfMatches; index++) {
                matches[index].print();
            }
        }
    }

    /**
     * iterate until passes is equal to the length of the array
     *      assign minimum index to passes
     *      iterate through and try to find a match with an earlier date
     *          if there is a match with an earlier date, assign the index to minimum index
     *      use the swap method to call for the swap of minimumIndex and passes in the array
     * @param arrayToSort TennisMatch array to sort
     */
    private void sortDate(TennisMatch [] arrayToSort)
    {
        for(int passes = 0; passes < numberOfMatches; passes++)
        {
            int minimumIndex = passes;
            for(int index = passes+1; index < numberOfMatches;index++)
            {
                if(arrayToSort[index].compareTo(arrayToSort[minimumIndex]) != 0)
                {
                    minimumIndex = index;
                }
            }
            swap(arrayToSort,minimumIndex,passes);
        }
    }

    /**
     * swapper equals array[firstIndex]
     * array[firstIndex] equals array[secondIndex]
     * array[secondIndex] equals swapper
     * @param array TennisMatch array to swap placement
     * @param firstIndex Index of first value to swap
     * @param secondIndex Index of second value to swap
     */
    private void swap(TennisMatch [] array, int firstIndex, int secondIndex)
    {
        TennisMatch swapper = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = swapper;
    }
}
