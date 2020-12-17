package ru.itis.experiments;

import java.io.IOException;
import java.util.*;

/**
 * Create a Shannon-Fano code from a list of element occurrences
 */
public class ShannonFano {

    /**
     * Call methods to build the list, create an empty code table, and
     * then start recursively populating the code table
     */
    public <T> Map<T, List<Boolean>> getCodeTable(Counts<T> counts) {

        List<T> list = buildList(counts);
        Map<T, List<Boolean>> codeTable = buildEmptyCodeTable(counts);

        populateCodeTable(codeTable, list, counts);

        return codeTable;
    }

    /**
     * Sort our input probability list descending
     *
     * @param counts Element counts
     * @return Elements sorted by probability
     */
    private static <T> List<T> buildList(final Counts<T> counts) {

        Set<T> countsKeys = counts.getElements();

        List<T> list =
                new ArrayList<T>(countsKeys.size());
        for (T t : countsKeys)
            list.add(t);

        Collections.sort(list, (o1, o2) -> -counts.getCount(o1).compareTo(counts.getCount(o2)));

        return list;
    }

    /**
     * Build an empty code table, i.e. zero length bit vector for
     * each element
     *
     * @param counts Element counts
     * @return The empty bit vector code table
     */
    private static <T> Map<T, List<Boolean>> buildEmptyCodeTable(
            Counts<T> counts) {

        Set<T> countsKeys = counts.getElements();

        Map<T, List<Boolean>> codeTable =
                new HashMap<>(countsKeys.size());
        for (T t : countsKeys)
            codeTable.put(t, new ArrayList<>());

        return codeTable;
    }

    /**
     * Recursively populate the code table.
     * <p>Split in two parts with (mostly) equal probabilities. For one part
     * add zeros for the other ones to the bit vectors. Then split those parts
     * again until they contain only one element.
     *
     * @param codeTable The code table filled so far (in place)
     * @param list      Part of the list to process
     * @param counts    Element counts
     */
    private static <T> void populateCodeTable(Map<T, List<Boolean>> codeTable,
                                              List<T> list, Counts<T> counts) {

        if (list.size() <= 1) return;

        int sum = 0;
        int fullSum = 0;
        for (T t : list)
            fullSum += counts.getCount(t);

        float bestdiff = 5;
        int i = 0;
        out:
        while (i < list.size()) {
            float prediff = bestdiff;
            sum += counts.getCount(list.get(i));
            bestdiff = Math.abs((float) sum / fullSum - 0.5F);
            if (prediff < bestdiff) break out;
            i++;
        }

        for (int j = 0; j < list.size(); j++) {
            if (j < i)
                codeTable.get(list.get(j)).add(Boolean.FALSE);
            else
                codeTable.get(list.get(j)).add(Boolean.TRUE);
        }

        populateCodeTable(codeTable, list.subList(0, i), counts);
        populateCodeTable(codeTable, list.subList(i, list.size()), counts);
    }

    /**
     * Tests building a code table
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Введите текст:");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        Map<Character, Integer> countsMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (countsMap.containsKey(c)) {
                countsMap.put(c, countsMap.get(c) + 1);
            } else {
                countsMap.put(c, 1);
            }
        }

        Counts<Character> counts = new Counts<>(countsMap);

        Map<Character, List<Boolean>> table =
                new ShannonFano().getCodeTable(counts);

        StringBuffer sb = new StringBuffer();
        sb.append("Code table:\n");
        for (Character c : countsMap.keySet()) {
            sb.append(" " + c + " -> ");
            for (Boolean b : table.get(c))
                sb.append(b == Boolean.FALSE ? '0' : '1');
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

}
/**
 * Utility method to keep track of element counts
 * @param <T>
 */
class Counts<T> {

    /**
     * Units for information
     * <p>You'd usually use bits.
     */
    public static enum Unit {
        BITS(2),BANS(10),NATS(Math.E);
        /**
         * The base of the logarithm computed for ln-conversion
         */
        private double logdivisor;
        /**
         * Specify a unit
         * @param logbase Base of the logarithm
         */
        private Unit(double logbase) {
            logdivisor = Math.log(logbase);
        }
        /**
         * Get the information given a probability
         * @param probability A probability [0;1]
         * @return Information in the unit of choice
         */
        public double calculate(double probability) {
            return - Math.log(probability)/logdivisor;
        }
        public static double convert(double input, Unit from, Unit to) {
            return input*(from.logdivisor/to.logdivisor);
        }
    }

    /**
     * Holds the element counts
     */
    private Map<T,Integer> map;

    /**
     * Holds all the different elements
     */
    private Set<T> elements = null;

    /**
     * Holds the sum of the element counts
     */
    private Integer sum = null;;

    /**
     * Construct by using a counts map
     * @param map Counts map
     */
    public Counts(Map<T,Integer> map) {
        this.map=Collections.unmodifiableMap(map);
    }

    /**
     * Count a list of input elements into the map
     * @param input Element list
     * @return Map with Element->Number of times in list
     */
    public Counts(List<T> input) {
        Map<T,Integer> counts = new HashMap<T, Integer>();
        for(T t : input) {
            if(counts.get(t)==null)
                counts.put(t, 1);
            else
                counts.put(t, counts.get(t)+1);
        }
        this.map=Collections.unmodifiableMap(counts);
    }

    public static <T> Counts<T> fromProbabilities(Map<T, Float> probabilities) {
        Map<T,Integer> counts = new HashMap<T, Integer>();
        Set<T> countSet =  probabilities.keySet();
        Float maxValue=(float)Integer.MAX_VALUE/countSet.size();
        for(T t : countSet) {
            Float count=probabilities.get(t)*maxValue;
            if(! (count<maxValue) ) { // Can't change to c>m b/c NaN
                count=maxValue;
            }
            counts.put(t, Math.round(count));
        }
        return new Counts<T>(counts);
    }

    /**
     * Get the set of elements for which counts exist
     * @return
     */
    public Set<T> getElements() {
        if(elements==null) {
            elements=map.keySet();
        }
        return elements;
    }

    /**
     * Get the sum of all element counts
     * @return
     */
    public int getSum() {
        if(sum==null) {
            sum=0;
            for(T t : getElements())
                sum+=map.get(t);
        }
        return sum;
    }

    /**
     * Get the count for one element
     * @param t The element
     * @return The number of occurrences
     */
    public Integer getCount(T t) {
        Integer returnValue = map.get(t);
        if(returnValue==null) returnValue=0;
        return returnValue;
    }

    /**
     * Get the probability for one element
     * @param t The element
     * @return The number of occurrences
     */
    public float getProbability(T t) { // p(t)
        return ((float)getCount(t)/getSum());
    }

    /**
     * Get the surprise factor of one certain element according to the
     * probabilities derived from the counts
     * @param t The element
     * @param unit The unit to calculate result in
     * @return The self information in [unit]
     */
    public double getOneSelfInformation(T t, Unit unit) { // I
        return unit.calculate(getProbability(t));
    }

    /**
     * Get the surprise factor if all probabilities were equal for the given
     * elements
     * @param unit The unit to calculate result in
     * @return The self maximum possible information in [unit]
     */
    public double getMaximumSelfInformation(Unit unit) { // H0
        return - unit.calculate(getElements().size());
    }

    /**
     * Get the expected surprise factor according to counted probabilities
     * when reading a stream
     * @param unit The unit to calculate result in
     * @return The mean information per read element in [unit]
     */
    public double getSelfInformation(Unit unit) { // H* = E[I]
        double returnValue=0;
        for(T t : getElements()) {
            Double info=getOneSelfInformation(t,unit);
            if(info.isInfinite()) returnValue+=0; // p(t)=0
            else returnValue+=getProbability(t)*info;
        }
        return returnValue;
    }

    /**
     * For a code table and our probabilities calculate the expected
     * length of one encoded element
     * @param codeTable Mapping to take the lengths from
     * @return The mean number of output symbols for one input symbol
     */
    public <V> double getMeanLength(Map<T,List<V>> codeTable) { // Sm
        double returnValue=0;
        for(T t : getElements()) {
            List<V> vector = codeTable.get(t);
            returnValue+=getProbability(t)*(vector==null?0:vector.size());
        }
        return returnValue;
    }

    /**
     * Return the potential savings when using an optimal encoding
     * @param codeTable The non optimal encoding
     * @param unit The unit to calculate result in
     * @return
     */
    public <V> double getRedundancy(Map<T, List<V>> codeTable, Unit unit) {
        return getMeanLength(codeTable)-getSelfInformation(unit);
    }

    /**
     * Get the compression percentage of the number of bits needed for
     * a minimal block code
     * @param codeTable  The target encoding
     * @param unit The unit to base the block encoding on
     * @return A number x of [0;1] with block*x=target
     */
    public <V> double getCompressionRatio(Map<T, List<V>> codeTable, Unit unit){
        return getMeanLength(codeTable)/getCeiledDesicionContent(unit);
    }

    /**
     * Number of bits needed for a minimal block code
     * @param unit The unit to base the block encoding on
     * @return
     */
    public <V> double getCeiledDesicionContent(Unit unit) {
        return Math.ceil(getMaximumSelfInformation(unit));
    }

}
