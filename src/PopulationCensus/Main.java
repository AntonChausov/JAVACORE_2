package PopulationCensus;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Collection<Person> persons = createCollect();
        getMinorsCount(persons);
        List<String> namesOfConscripts = getConscripts(persons, scanner);
        List<Person> workers = getWorkers(persons, scanner);
    }

    private static List<Person> getWorkers(Collection<Person> persons, Scanner scanner) {
        List<Person> workers = persons.stream()
                .filter(p -> p.getEducation() == Education.HIGHER)
                .filter(p -> p.getAge() >= 18)
                .filter(p -> (p.getAge() < 60 && p.getSex() == Sex.WOMAN) || (p.getAge() < 65 && p.getSex() == Sex.MAN))
                //.sorted((Person a, Person b) -> b.getFamily().compareTo(a.getFamily())) - позже прочитал про "Comparator.comparing"
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Потенциальных работников: " + workers.size());
        printList(scanner, workers);
        return workers;
    }

    private static List<String> getConscripts(Collection<Person> persons, Scanner scanner) {
        List<String> namesOfConscripts = persons.stream()
                .filter(p -> p.getAge() >= 18)
                .filter(p -> p.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Призывников: " + namesOfConscripts.size());
        printList(scanner, namesOfConscripts);
        return namesOfConscripts;
    }

    private static <T> void printList(Scanner scanner, List<T> list) {
        System.out.println("Вывести? :) y/n");
        while (true){
            String answer = scanner.nextLine();
            if (answer.equals("y")){
                list.stream()
                        .forEach(System.out::println);
                break;
            } else if (answer.equals("n")){
                break;
            }
        }
    }

    private static void getMinorsCount(Collection<Person> persons) {
        long minors = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
        System.out.println("Несовершеннолетних: " + minors);
    }

    private static Collection<Person> createCollect() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        //for (int i = 0; i < 100; i++) { - для тестов
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }
}
