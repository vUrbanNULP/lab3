package battle;

import java.util.ArrayList;
import java.util.List;
import droids.Droid;
import arenas.Arena;
import log.LogFile;

public class Battle {
    private List<Droid> playerTeam;
    private List<Droid> enemyTeam;
    private Arena arena;
    private int strategy;
    private List<Droid> turnOrder;
    int droidIndex;
    int targetIndex;
    private LogFile log;

    public Battle(List<Droid> playerTeam, List<Droid> enemyTeam, Arena arena, Integer strategy) {
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
        this.arena = arena;
        this.strategy = strategy != null ? strategy : -1;
        this.turnOrder = new ArrayList<>();
        this.log = new LogFile();
    }

    public void start() {
        log.write("\nБитва розпочинається на арені " + arena.getName() + "!");
        System.out.println("\nБитва розпочинається на арені " + arena.getName() + "!");
        applyArenaArmorModifiers();

        displayTeamList(playerTeam, "\nКоманда гравця:");
        displayTeamList(enemyTeam, "\nКоманда противника:");

        determineTurnOrder();

        int round = 0;
        while (!isTeamDefeated(playerTeam) && !isTeamDefeated(enemyTeam)) {
            round++;
            log.write("\nРаунд " + round);
            System.out.println("\nРаунд " + round);

            for (Droid droid : turnOrder) {
                if (droid.getHealth() > 0) {
                    executeTurn(droid);
                }
            }

            if (arena.getEarthquakeActivationInterval() > 0 && round % arena.getEarthquakeActivationInterval() == 0) {
                applyArenaEarthquakeDamage();
            }
        }

        if (isTeamDefeated(playerTeam)) {
            log.write("\nКоманда гравця програла.");
            System.out.println("\nКоманда гравця програла.");
        } else {
            log.write("\nКоманда гравця перемогла!");
            System.out.println("\nКоманда гравця перемогла!");
        }
        log.close();
        for (Droid droid : playerTeam) {
            droid.log.close();
        }
        for (Droid droid : enemyTeam) {
            droid.log.close();
        }
        log.askForSaving();
    }

    private void displayTeamList(List<Droid> team, String teamName) {
        log.write(teamName);
        System.out.println(teamName);
        String[] droidTypes = new String[team.size()];
        int[] droidCounts = new int[team.size()];

        int index = 0;
        for (Droid droid : team) {
            String droidType = droid.getName();
            boolean found = false;

            for (int i = 0; i < index; i++) {
                if (droidTypes[i].equals(droidType)) {
                    droidCounts[i]++;
                    found = true;
                    break;
                }
            }

            if (!found) {
                droidTypes[index] = droidType;
                droidCounts[index] = 1;
                index++;
            }
        }

        for (int i = 0; i < index; i++) {
            String droidType = droidTypes[i];
            int count = droidCounts[i];
            log.write("Тип дроїда: " + droidType + ", Кількість: " + count);
            System.out.println("Тип дроїда: " + droidType + ", Кількість: " + count);
        }
    }

    private boolean isTeamDefeated(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.getHealth() > 0) {
                return false;
            }
        }
        return true;
    }

    private void determineTurnOrder() {
        turnOrder.addAll(playerTeam);
        turnOrder.addAll(enemyTeam);

        for (int i = 0; i < turnOrder.size(); i++) {
            for (int j = i + 1; j < turnOrder.size(); j++) {
                if (turnOrder.get(i).getSpeed() < turnOrder.get(j).getSpeed()) {
                    Droid temp = turnOrder.get(i);
                    turnOrder.set(i, turnOrder.get(j));
                    turnOrder.set(j, temp);
                }
            }
        }

        for (int i = 0; i < turnOrder.size(); i++) {
            turnOrder.get(i).setNumberInTeam(turnOrder.indexOf(turnOrder.get(i)) + 1);
        }

        log.write("\nЧерга ходів дроїдів:");
        System.out.println("\nЧерга ходів дроїдів:");
        for (Droid droid : turnOrder) {
            String teamName = playerTeam.contains(droid) ? "гравця" : "противника";
            log.write("#" + (droid.getNumberInTeam()) + " " + teamName + " " + droid.getName() + " зі швидкістю " + droid.getSpeed());
            System.out.println("#" + (droid.getNumberInTeam()) + " " + teamName + " " + droid.getName() + " зі швидкістю " + droid.getSpeed());
        }
    }

    private void executeTurn(Droid droid) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        droidIndex = turnOrder.indexOf(droid) + 1;
        String teamName = playerTeam.contains(droid) ? "гравця" : "противника";
        log.write("\nХід команди " + teamName + ". Дроїд #" + droidIndex + " - " + droid.getName() + ".");
        System.out.println("\nХід команди " + teamName + ". Дроїд #" + droidIndex + " - " + droid.getName() + ".");

        Droid target = chooseTarget(droid);
        targetIndex = turnOrder.indexOf(target) + 1;
        if (target != null) {
            target.setTempHealth(target.getHealth());
            log.write("#" + droidIndex + " " + droid.getName() + " атакує "
                    + "#" + targetIndex + " " + target.getName() + ".");
            System.out.println("#" + droidIndex + " " + droid.getName() + " атакує "
                    + "#" + targetIndex + " " + target.getName() + ".");

            droid.attack(target);

            log.write("#" + target.getNumberInTeam() + " " + target.getName() + " отримав " +
                    (target.getTempHealth() - target.getHealth()) + " шкоди за цей хід. Залишилось: "
                    + target.getHealth() + " здоров'я.");
            System.out.println("#" + target.getNumberInTeam() + " " + target.getName() + " отримав " +
                    (target.getTempHealth() - target.getHealth()) + " шкоди за цей хід. Залишилось: "
                    + target.getHealth() + " здоров'я.");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (target.getHealth() <= 0) {
            log.write("#" + target.getNumberInTeam() + " " + target.getName() + " помер.");
            System.out.println("#" + target.getNumberInTeam() + " " + target.getName() + " помер.");
        }
    }

private Droid chooseTarget(Droid attacker) {
        List<Droid> enemies = attackerTeam(attacker);
        List<Droid> potentialTargets = new ArrayList<>();

        boolean meleeAlive = false;
        for (Droid droid : enemies) {
            if (droid.getAttackType().equals("melee") && droid.getHealth() > 0) {
                meleeAlive = true;
                break;
            }
        }

        if (meleeAlive && attacker.getAttackType().equals("melee")) {
            potentialTargets.addAll(getMeleeDroids(enemies));
        } else if (!meleeAlive || attacker.getAttackType().equals("ranged")) {
            potentialTargets.addAll(enemies);
        }

        switch (strategy) {
            case 1:
                return getLowestHealthDroid(potentialTargets);
            case 2:
                return getHighestDamageDroid(potentialTargets);
            case 3:
                return getMechanicDroid(potentialTargets);
            default:
                return potentialTargets.isEmpty() ? null : potentialTargets.get(0);
        }
    }

    private List<Droid> attackerTeam(Droid attacker) {
        return playerTeam.contains(attacker) ? enemyTeam : playerTeam;
    }

    private List<Droid> getMeleeDroids(List<Droid> enemies) {
        List<Droid> meleeDroids = new ArrayList<>();
        for (Droid droid : enemies) {
            if (droid.getAttackType().equals("melee") && droid.getHealth() > 0) {
                meleeDroids.add(droid);
            }
        }
        return meleeDroids;
    }

    private Droid getLowestHealthDroid(List<Droid> droids) {
        Droid lowestHealthDroid = null;
        for (Droid droid : droids) {
            if (droid.getHealth() > 0 && (lowestHealthDroid == null || droid.getHealth() < lowestHealthDroid.getHealth())) {
                lowestHealthDroid = droid;
            }
        }
        return lowestHealthDroid;
    }

    private Droid getHighestDamageDroid(List<Droid> droids) {
        Droid highestDamageDroid = null;
        for (Droid droid : droids) {
            if (droid.getHealth() > 0 && (highestDamageDroid == null || droid.getDamage() > highestDamageDroid.getDamage())) {
                highestDamageDroid = droid;
            }
        }
        return highestDamageDroid;
    }

    private Droid getMechanicDroid(List<Droid> droids) {
        for (Droid droid : droids) {
            if (droid.getName().equals("Mechanic") && droid.getHealth() > 0) {
                return droid;
            }
        }
        for (Droid droid : droids) {
            if (droid.getHealth() > 0) {
                return droid;
            }
        }
        return null;
    }

    private void applyArenaArmorModifiers() {
        if (arena.getArmorModifier() != 0) {
            log.write("Застосування модифікатора броні: " + arena.getArmorModifier());
            System.out.println("Застосування модифікатора броні: " + arena.getArmorModifier());
            for (Droid droid : playerTeam) {
                droid.setArmor(droid.getArmor() + arena.getArmorModifier());
            }
            for (Droid droid : enemyTeam) {
                droid.setArmor(droid.getArmor() + arena.getArmorModifier());
            }
        }
    }

    private void applyArenaEarthquakeDamage() {
        log.write("Землетрус! Усі дроїди отримують шкоду.");
        System.out.println("Землетрус! Усі дроїди отримують шкоду.");
        for (Droid droid : playerTeam) {
            droid.takeDamage(arena.getEarthquakeDamageModifier());
        }
        for (Droid droid : enemyTeam) {
            droid.takeDamage(arena.getEarthquakeDamageModifier());
        }
    }
}