package main;

import arenas.Arena;
import team.TeamManager;
import battle.Battle;
import droids.*;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static TeamManager playerTeamManager;
    private static TeamManager enemyTeamManager;
    private static Arena arena;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getMenuChoice();
            switch (choice) {
                case 1:
                    startBattle(1);
                    break;
                case 2:
                    startBattle(5);
                    break;
                case 3:
                    showDroidInfo();
                    break;
                case 4:
                    System.out.println("Вихід з програми...");
                    return;
                default:
                    System.out.println("Неправильний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n\nГоловне меню.\nВиберіть дію:");
        System.out.println("1. Битва 1 на 1");
        System.out.println("2. Битва 5 на 5");
        System.out.println("3. Довідка про види дроїдів");
        System.out.println("4. Вийти з програми");
    }

    private static int getMenuChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Введіть число.");
        }
        return choice;
    }

    private static void startBattle(int teamSize) {
        playerTeamManager = new TeamManager();
        enemyTeamManager = new TeamManager();

        System.out.println("Виберіть дроїдів для вашої команди:");
        List<Droid> playerTeam = playerTeamManager.createTeam(teamSize);

        if (teamSize > 1) {
            System.out.println("Виберіть стратегію для вашої команди:");
            playerTeamManager.setStrategy(chooseStrategy());
        }

        System.out.println("Виберіть дроїдів для команди противника:");
        List<Droid> enemyTeam = enemyTeamManager.createTeam(teamSize);

        if (teamSize > 1) {
            System.out.println("Виберіть стратегію для команди противника:");
            enemyTeamManager.setStrategy(chooseStrategy());
        }

        arena = chooseArena();

        Battle battle = new Battle(playerTeam, enemyTeam, arena, teamSize > 1 ? playerTeamManager.getStrategy() : null);
        battle.start();

        resetGame();
    }

    private static int chooseStrategy() {
        int strategy = -1;
        while (true) {
            System.out.println("1. Атакувати дроїда з найнижчим здоров'ям");
            System.out.println("2. Атакувати дроїда з найвищим показником шкоди");
            System.out.println("3. Атакувати дроїда виду 'Mechanic'");
            try {
                strategy = Integer.parseInt(scanner.nextLine());
                if (strategy >= 1 && strategy <= 3) {
                    break;
                } else {
                    System.out.println("Неправильний вибір. Спробуйте ще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть число.");
            }
        }
        return strategy;
    }

    private static Arena chooseArena() {
        while (true) {
            System.out.println("Виберіть арену:");
            System.out.println("1. Звичайна арена (без модифікаторів)");
            System.out.println("2. Землетрус (кожних 3 ходи дроїди отримують 10 одиниць шкоди)");
            System.out.println("3. Кислотний дощ (броня всіх дроїдів зменшена на 10 одиниць)");
            System.out.println("4. Ввести модифікатори вручну");

            int arenaChoice = getMenuChoice();
            switch (arenaChoice) {
                case 1:
                    return new Arena("Звичайна", 0, 0, 0);
                case 2:
                    return new Arena("Землетрус", 10, 3, 0);
                case 3:
                    return new Arena("Кислотний дощ", 0, 0, -10);
                case 4:
                    System.out.println("Введіть модифікатор шкоди:");
                    int damageModifier = Integer.parseInt(scanner.nextLine());
                    System.out.println("Введіть періодичність (у ходах) активації шкоди землетрусу:");
                    int stepDamageActivation = Integer.parseInt(scanner.nextLine());
                    System.out.println("Введіть модифікатор броні кислотного дощу:");
                    int armorModifier = Integer.parseInt(scanner.nextLine());
                    return new Arena("Custom", damageModifier, stepDamageActivation, armorModifier);
                default:
                    System.out.println("Неправильний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void showDroidInfo() {
        System.out.println(new Warrior());
        System.out.println(new Mosquito());
        System.out.println(new Musketeer());
        System.out.println(new Mechanic(new TeamManager()));
    }

    private static void resetGame() {
        playerTeamManager = null;
        enemyTeamManager = null;
        arena = null;
    }
}
