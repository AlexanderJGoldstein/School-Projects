import java.util.*;

public class degreeOfSeparationExample {
    private class Person {
        private List<Person> knownPeople = new LinkedList<Person>();

        public Person(){

        }

        public boolean knows(Person p2) {
            // Checks if person A knows Person B
            return this.knownPeople.contains(p2);
        }

        public void meet(Person p2) {
            this.knownPeople.add(p2);
            p2.knownPeople.add(this);
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

    public Person makeNewPerson(){
        return this.new Person();
    }
}
//This checks if the given person has a chain of known people that is at most n people long which contains p2
//This has a tree-like organization, so we go through each branch, checking if the person at the end of each branch knows p2.
//Each level of the tree contains nodes corresponding to the people known by the person on the previous level of the tree