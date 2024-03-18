package ru.gb.lesson1.hw;

import ru.gb.lesson1.Streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Homework {

  /**
   * Реалзиовать методы, описанные ниже:
   */

  /**
   * Вывести на консоль отсортированные (по алфавиту) имена персонов
   */
  public void printNamesOrdered(List<Streams.Person> persons) {
    persons.stream()
      .map(Streams.Person::getName)
      .sorted()
      .forEach(System.out::println);
  }

  /**
   * В каждом департаменте найти самого взрослого сотрудника.
   * Вывести на консоль мапипнг department -> personName
   * Map<Department, Person>
   */
  public Map<Streams.Department, Streams.Person> printDepartmentOldestPerson(List<Streams.Person> persons) {
    return persons.stream()
      .collect(Collectors.toMap(
        Streams.Person::getDepartment,
        Function.identity(),
        BinaryOperator.maxBy(Comparator.comparing(Streams.Person::getAge))
      ));
  }

  /**
   * Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
   */
  public List<Streams.Person> findFirstPersons(List<Streams.Person> persons) {
    return persons.stream()
      .filter(it -> it.getAge() < 30)
      .filter(it -> it.getSalary() > 50_000)
      .limit(10)
      .toList();
  }

  /**
   * Найти депаратмент, чья суммарная зарплата всех сотрудников максимальна
   */
  public Optional<Streams.Department> findTopDepartment(List<Streams.Person> persons) {
    return persons.stream()
      .collect(Collectors.groupingBy(Streams.Person::getDepartment, Collectors.summingDouble(Streams.Person::getSalary)))
      .entrySet().stream()
      .sorted(Map.Entry.<Streams.Department, Double>comparingByValue().reversed())
      .map(Map.Entry::getKey)
      .findFirst();

  }

}
