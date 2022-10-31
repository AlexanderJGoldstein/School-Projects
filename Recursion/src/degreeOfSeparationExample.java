import java.util.*;

public class degreeOfSeparationExample {
    private class Person {
        private List<Person> knownPeople = new LinkedList<Person>();

        public boolean knows(Person p2) {
            // Checks if person A knows Person B
            return this.knownPeople.contains(p2);
        }

        public void meet(Person p2) {
            this.knownPeople.add(p2);
            p2.subMeet(this);
        }

        private void subMeet(Person p1){
            this.knownPeople.add(p1);
        }
    }

    public static boolean degreeOfSeparation(Set<Person> people, Person p1, Person p2, int n) {
        if (n == 1) // Base case
        {
            return p1.knows(p2);
        } else // Recursive case
        {
            for (Person p : people) {
                if (p1.knows(p) && degreeOfSeparation(people, p, p2, n - 1))
                    return true;
            }
            return false;
        }
    }
}
