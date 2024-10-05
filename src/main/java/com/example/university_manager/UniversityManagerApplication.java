package com.example.university_manager;

import com.example.university_manager.entity.Rank;
import com.example.university_manager.service.DepartmentService;
import com.example.university_manager.service.LectorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@SpringBootApplication
public class UniversityManagerApplication {

	private DepartmentService departmentService;
	private LectorService lectorService;

	public UniversityManagerApplication(DepartmentService departmentService, LectorService lectorService) {
		this.departmentService = departmentService;
		this.lectorService = lectorService;
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UniversityManagerApplication.class, args);
		UniversityManagerApplication app = context.getBean(UniversityManagerApplication.class);
		app.runConsole();
	}

	private void runConsole() {
		Scanner scanner = new Scanner(System.in);
		String userInput;

		System.out.println("Welcome! Please choose one of the following commands:");
		while (true) {
			printMenu();
			System.out.print("Enter the number of your choice (or 'exit' to quit): ");
			userInput = scanner.nextLine();

			if ("exit".equalsIgnoreCase(userInput)) {
				System.out.println("Exiting...");
				break;
			}

			switch (userInput) {
				case "1":
					handleHeadOfDepartment(scanner);
					break;
				case "2":
					handleGlobalSearch(scanner);
					break;
				case "3":
					handleEmployeeCount(scanner);
					break;
				case "4":
					handleAverageSalary(scanner);
					break;
				case "5":
					handleDepartmentStatistics(scanner);
					break;
				default:
					System.out.println("Invalid choice, please try again.");
			}
		}

		scanner.close();
	}

	private void printMenu() {
		System.out.println("\nAvailable commands:");
		System.out.println("1. Who is head of department? {department_name}");
		System.out.println("2. Global search by {template}");
		System.out.println("3. Show count of employee for {department_name}");
		System.out.println("4. Show the average salary for the department {department_name}");
		System.out.println("5. Show {department_name} statistics");
	}

	private void handleHeadOfDepartment(Scanner scanner) {
		System.out.print("Enter the department name: ");
		String departmentName = scanner.nextLine();

		Function<String, String> headOfDepartmentFunction = departmentService::getHeadOfDepartmentName;
		printResult(headOfDepartmentFunction, departmentName,
				"Head of " + departmentName + " department is %s");
	}

	private void handleGlobalSearch(Scanner scanner) {
		System.out.print("Enter a part of the name to search for: ");
		String namePart = scanner.nextLine();

		Function<String, String> lectorsNamesFunction = lectorService::findLectorsByNamePart;
		printResult(lectorsNamesFunction, namePart, "%s");
	}

	private void handleEmployeeCount(Scanner scanner) {
		System.out.print("Enter the department name: ");
		String departmentName = scanner.nextLine();

		Function<String,String> employeeCountFunction = (depName) -> departmentService.getCountOfLectors(depName).toString();
		printResult(employeeCountFunction, departmentName, "%s");
	}

	private void handleAverageSalary(Scanner scanner) {
		System.out.print("Enter the department name: ");
		String departmentName = scanner.nextLine();

		Function<String,String> averageSalaryFunction = (depName) -> departmentService.getAverageSalary(depName).toString();

		printResult(averageSalaryFunction, departmentName, "The average salary of "+ departmentName +" department is %s");
	}

	private void handleDepartmentStatistics(Scanner scanner) {
		System.out.print("Enter the department name: ");
		String departmentName = scanner.nextLine();
		try {
			Map<Rank, Integer> statistics = departmentService.getStatistic(departmentName);
			for (Map.Entry<Rank, Integer> entry : statistics.entrySet()) {
				System.out.println(entry.getKey().getTitle() + " = " + entry.getValue());
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	void printResult(Function<String,String> some, String depName, String format) {
		try {
			String result = some.apply(depName);
			String formattedString = String.format(format, result);
			System.out.println(formattedString);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
}