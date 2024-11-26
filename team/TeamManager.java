package team;

import droids.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeamManager {
    private List<Droid> team;
    private int strategy;

    public TeamManager() {
        team = new ArrayList<>();
    }

    public List<Droid> createTeam(int maxDroids) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть дроїдів для цієї команди у форматі 'Ім'я_дроїда Кількість'. Максимум " + maxDroids + " дроїдів.");
        while (team.size() < maxDroids) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Неправильний формат. Спробуйте ще раз.");
                continue;
            }

            String droidName = parts[0];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[1]);
                if (quantity < 1 || quantity > (maxDroids - team.size())) {
                    System.out.println("Неправильна кількість дроїдів. Введіть число від 1 до " + (maxDroids - team.size()) + ".");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Кількість дроїдів повинна бути числом. Спробуйте ще раз.");
                continue;
            }

            for (int i = 0; i < quantity; i++) {
                Droid droid = createDroid(droidName);
                if (droid != null) {
                    team.add(droid);
                } else {
                    System.out.println("Невідомий тип дроїда: " + droidName);
                }
            }
        }
        return team;
    }

    private Droid createDroid(String droidName) {
        switch (droidName.toLowerCase()) {
            case "warrior":
                return new Warrior();
            case "mosquito":
                return new Mosquito();
            case "musketeer":
                return new Musketeer();
            case "mechanic":
                return new Mechanic(this);
            default:
                return null;
        }
    }

    public List<Droid> getTeam() {
        return team;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public int getStrategy() {
        return strategy;
    }
}
