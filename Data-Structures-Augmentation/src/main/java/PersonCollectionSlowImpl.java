import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersonCollectionSlowImpl implements PersonCollection {

    private List<Person> people;

    public PersonCollectionSlowImpl() {
        this.people = new ArrayList<>();
    }

    @Override
    public boolean add(String email, String name, int age, String town) {
        if (this.people.stream().anyMatch(person -> person.getEmail().equals(email))) {
            return false;
        }

        Person p = new Person(email, name, age, town);
        return this.people.add(p);
    }

    @Override
    public int getCount() {
        return this.people.size();
    }

    @Override
    public boolean delete(String email) {
        return this.people.removeIf(person -> person.getEmail().equals(email));
    }

    @Override
    public Person find(String email) {

//        List<Person> result = this.getPeopleByPredicate(person -> person.getEmail().equals(email));
//        return result.isEmpty() ? null : result.get(0);

        return this.getPeopleByPredicate(person -> person.getEmail().equals(email))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<Person> findAll(String emailDomain) {
//        return this.people.stream()
//                .filter(person -> person.getEmail().endsWith("@" + emailDomain))
//                .sorted(Comparator.comparing(Person::getEmail))
//                .collect(Collectors.toList());

        return this.getPeopleByPredicate(person -> person.getEmail().endsWith("@" + emailDomain))
                .stream()
                .sorted(Comparator.comparing(Person::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Person> findAll(String name, String town) {
//        return this.people.stream()
//                .filter(person -> person.getName().equals(name) && person.getTown().equals(town))
//                .sorted(Comparator.comparing(Person::getEmail))
//                .collect(Collectors.toList());

        List<Person> filtered = this.getPeopleByPredicate(person -> person.getName().equals(name) && person.getTown().equals(town));
        filtered.sort(Comparator.comparing(Person::getEmail));

        return filtered;
    }

    @Override
    public Iterable<Person> findAll(int startAge, int endAge) {
        return this.getPeopleByPredicate(person -> person.getAge() >= startAge
                        && person.getAge() <= endAge)
                .stream()
                .sorted(Comparator.comparing(Person::getAge))
                .sorted(Comparator.comparing(Person::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Person> findAll(int startAge, int endAge, String town) {
        return this.getPeopleByPredicate(person -> person.getAge() >= startAge
                        && person.getAge() <= endAge
                        && person.getTown().equals(town))
                .stream()
                .sorted(Comparator.comparing(Person::getAge))
                .sorted(Comparator.comparing(Person::getEmail))
                .collect(Collectors.toList());
    }

    private List<Person> getPeopleByPredicate(Predicate<Person> predicate) {
        return this.people.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
