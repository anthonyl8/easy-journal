package model;

// SOURCE: Practical Midterm 1 Practice Question #3

// Comparator that ranks tags by comparing the number of events categorized under each one.
public class TagComparator {

    public TagComparator() {}

    // REQUIRES: m1, m2 != null
    // EFFECTS: returns -1 if t1 has a smaller number of events than t2
    //          returns 0 if t1 has the same number of events as t2
    //          returns +1 if t1 has a greater number of events than t2
    public int compare(Tag t1, Tag t2) {
        int numEventst1 = t1.getNumEvents();
        int numEventst2 = t2.getNumEvents();
        if (numEventst1 < numEventst2) {
            return -1;
        } else if (numEventst1 == numEventst2) {
            return 0;
        } else {
            return 1;
        }
    }

}
