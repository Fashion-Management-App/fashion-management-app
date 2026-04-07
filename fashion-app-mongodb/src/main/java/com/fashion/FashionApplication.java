package com.fashion;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class FashionApplication implements CommandLineRunner {

    @Autowired
    FashionRepository fashionRepository;

    public static void main(String[] args) {
        SpringApplication.run(FashionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("================ Fashion Database ================");
            System.out.println("1. Імпорт примірок з CSV-файлу");
            System.out.println("2. Переглянути всі примірки");
            System.out.println("3. Видалити всі примірки");
            System.out.println("4. Вихід");
            System.out.print("Введіть команду (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    importFashionFromCsv();
                    break;
                case 2:
                    viewAllFittings();
                    break;
                case 3:
                    deleteAllFittings();
                    break;
                case 4:
                    System.out.println("Вихід із програми.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Хибний вибір. Спробуйте ще раз.");
            }
        }
    }


    void importFashionFromCsv() {
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("fashion.csv"), "UTF-8")
        )) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); 

            List<Fashion> list = new ArrayList<>();

            for (String[] row : rows) {
                Fashion fashion = new Fashion(
                        row[0],                    // customer
                        row[1],                    // couturier
                        row[2],                    // fitting_date
                        row[3],                    // item_model
                        row[4],                    // sizes
                        row[5],                    // fabrics
                        row[6],                    // customer_level
                        Integer.parseInt(row[7]),  // couturier_experience
                        row[8],                    // atelier_address
                        Long.parseLong(row[9])     // atelier_phone
                );

                list.add(fashion);
            }

            fashionRepository.saveAll(list);

            System.out.println("Імпортовано документів: " + list.size());
        } catch (Exception e) {
            System.out.println("Помилка імпорту CSV!");
            e.printStackTrace();
        }
    }


   void viewAllFittings() {
        List<Fashion> list = fashionRepository.findAll();

        if (list.isEmpty()) {
            System.out.println("База порожня. Примірок не знайдено.");
            return;
        }

        System.out.println("Знайдено " + list.size() + " документів:");
        list.forEach(System.out::println);
    }


    void deleteAllFittings() {
        fashionRepository.deleteAll();
        System.out.println("Усі примірки успішно видалено.");
    }
}
