package droids;

import team.TeamManager;
import java.util.Random;

public class Mechanic extends Droid {
    private TeamManager teamManager;

    public Mechanic(TeamManager teamManager) {
        super("Mechanic", 55, 30, 80, 15, 50, "Ranged");
        this.teamManager = teamManager;
    }

    @Override
    public void useSpecialAbility() {
        Droid target = getRandomTeammate();
        if (target != null && target.getHealth() > 0) {
            int healAmount = (int) (target.getHealth() * 0.15);
            target.setHealth(target.getHealth() + healAmount);
            System.out.println("#" + numberInTeam + " " + getName() + " використовує здібність 'Repair' і відновлює "
                    + healAmount + " здоров'я дроїду " + "#" + target.getNumberInTeam() + " " + target.getName());
            log.write(numberInTeam + getName() + " використовує здібність 'Repair' і відновлює "
                    + healAmount + " здоров'я дроїду " + "#" + target.getNumberInTeam() + " " + target.getName());
        }
    }

    private Droid getRandomTeammate() {
        Random random = new Random();
        Droid target = null;
        var team = teamManager.getTeam();

        while (!team.isEmpty()) {
            int randomIndex = random.nextInt(team.size());
            target = team.get(randomIndex);
            if (target.getHealth() > 0) {
                return target;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "Спеціальна здібність: кожен хід лікує 15% здоровє'я випадкового союзника.\n";
    }
}