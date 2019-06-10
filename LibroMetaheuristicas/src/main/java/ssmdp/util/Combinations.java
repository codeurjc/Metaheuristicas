package ssmdp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Combinations {

    private List<List<Integer>> totalGroups;
    private Map<Integer, List<List<Integer>>> groupsContainingIndex = new HashMap<Integer, List<List<Integer>>>();

    public Combinations(int numElems) {
        this(numElems, numElems);
    }

    public Combinations(int groupSize, int numElems) {

        if (groupSize > numElems) {
            throw new IllegalArgumentException(
                    "groupSize cannot be greater than numElems");
        }

        totalGroups = generateCombinations(groupSize, numElems);

        for (int i = 0; i < numElems; i++) {
            groupsContainingIndex.put(i,
                    new ArrayList<List<Integer>>());
        }

        for (List<Integer> group : totalGroups) {
            for (Integer i : group) {
                groupsContainingIndex.get(i).add(group);
            }
        }
    }

    private List<List<Integer>> generateCombinations(
            int groupSize, int numElems) {

        List<List<Integer>> totalGroups = new ArrayList<List<Integer>>();

        List<List<Integer>> groups2 = createGroupIterationInt(2,
                numElems);
        totalGroups.addAll(groups2);

        if (groupSize > 2) {

            List<List<Integer>> groups3 = createExpandedGroup(
                    numElems, groups2);
            totalGroups.addAll(groups3);

            if (groupSize > 3) {
                List<List<Integer>> groups4 = createExpandedGroup(
                        numElems, groups3);
                totalGroups.addAll(groups4);

                if (groupSize > 4) {

                    for (int i = 5; i <= groupSize; i++) {
                        totalGroups.add(createGroup(i));
                    }
                }
            }
        }

        return totalGroups;
    }

    private List<Integer> createGroup(int numElems) {
        List<Integer> group = new ArrayList<Integer>();
        for (int i = 0; i < numElems; i++) {
            group.add(i);
        }
        return group;
    }

    private List<List<Integer>> createExpandedGroup(int numElems,
                                                    List<List<Integer>> originalGroups) {
        List<List<Integer>> expandedGroups = new ArrayList<List<Integer>>();
        for (List<Integer> group : originalGroups) {
            expandedGroups.add(new ArrayList<Integer>(group));
        }

        for (Iterator<List<Integer>> iter = expandedGroups
                .iterator(); iter.hasNext();) {
            List<Integer> expandedGroup = iter.next();

            boolean groupSet = false;

            for (int i = 0; i < numElems; i++) {
                if (expandedGroup.contains(i)) {
                    continue;
                }

                expandedGroup.add(i);
                if (existsPreviousGroup(expandedGroups, expandedGroup)) {
                    expandedGroup.remove(expandedGroup.size() - 1);
                } else {
                    groupSet = true;
                    break;
                }
            }

            if (!groupSet) {
                iter.remove();
            }
        }
        return expandedGroups;
    }

    private boolean existsPreviousGroup(
            List<List<Integer>> groups, List<Integer> newGroup) {
        for (List<Integer> group : groups) {
            if (group == newGroup) {
                return false;
            }
            List<Integer> oGroup = new ArrayList<Integer>(group);
            List<Integer> oNewGroup = new ArrayList<Integer>(newGroup);

            Collections.sort(oGroup);
            Collections.sort(oNewGroup);

            if (oGroup.equals(oNewGroup)) {
                return true;
            }
        }
        throw new Error();
    }

    private List<List<Integer>> createGroupIterationInt(
            int numElements, int size) {
        return createGroupIterationInt(numElements, 0, size);
    }

    private List<List<Integer>> createGroupIterationInt(
            int numElements, int init, int size) {

        List<List<Integer>> groups = new ArrayList<List<Integer>>();
        if (numElements == 0) {
            groups.add(new ArrayList<Integer>());
        } else {
            for (int i = init; i < size; i++) {
                List<List<Integer>> tempGroups = createGroupIterationInt(
                        numElements - 1, i + 1, size);
                for (List<Integer> g : tempGroups) {
                    g.add(0, i);
                }
                groups.addAll(tempGroups);
            }
        }
        return groups;
    }

    public Collection<List<Integer>> getGroupsContainingIndexes(
            Collection<Integer> indexes, int size) {
        Set<List<Integer>> groups = new HashSet<List<Integer>>();
        for (Integer i : indexes) {
            for (List<Integer> group : groupsContainingIndex.get(i)) {
                int maxGroupValue = 0;
                for (int gIndex : group) {
                    maxGroupValue = Math.max(gIndex, maxGroupValue);
                }
                if (maxGroupValue < size) {
                    groups.add(group);
                }
            }
        }
        return groups;
    }

    public <T> List<List<T>> getGroupsContainingIndexes(
            List<Integer> indexes, List<T> elems) {
        List<List<T>> returnGroups = new ArrayList<List<T>>();

        Collection<List<Integer>> indexGroups = getGroupsContainingIndexes(
                indexes, elems.size());

        for (List<Integer> indexGroup : indexGroups) {
            List<T> returnGroup = new ArrayList<T>();
            for (int index : indexGroup) {
                returnGroup.add(elems.get(index));
            }
            returnGroups.add(returnGroup);
        }
        return returnGroups;
    }

    public <T> List<List<T>> getGroups(List<T> mdpGraphs) {

        List<List<T>> groups = new ArrayList<List<T>>();

        global: for (List<Integer> groupInt : totalGroups) {
            List<T> group = new ArrayList<T>();
            for (Integer i : groupInt) {
                if (i > mdpGraphs.size() - 1) {
                    continue global;
                } else {
                    group.add(mdpGraphs.get(i));
                }
            }
            groups.add(group);
        }

        return groups;
    }

}
