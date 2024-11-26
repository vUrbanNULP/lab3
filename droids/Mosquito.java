package droids;

public class Mosquito extends Droid {

    public Mosquito() {
        super("Mosquito", 60, 40, 85, 5, 80, "Melee");
    }

    @Override
    public void useSpecialAbility() {
    }

    @Override
    public void attack(Droid opponent) {
        int hitChance = (int) (Math.random() * 100);
        if (hitChance <= getAccuracy()) {
            opponent.setTempHealth(opponent.getHealth());
            opponent.setHealth(opponent.getHealth() - getDamage());
            log.write("#" + (numberInTeam) +  " " + this.getName() +
                    " ігнорує броню " + "#" + (opponent.getNumberInTeam()) + " "
                    + opponent.getName() + " і завдає " + getDamage() + " шкоди здоров'ю.");
            System.out.println("#" + (numberInTeam) +  " " + this.getName() +
                    " ігнорує броню " + "#" + (opponent.getNumberInTeam()) + " "
                    + opponent.getName() + " і завдає " + getDamage() + " шкоди здоров'ю.");
        } else {
            log.write("#" + (numberInTeam) + " " + this.getName() + " промахнувся!");
            System.out.println("#" + (numberInTeam) + " " + this.getName() + " промахнувся!");
        }
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "Спеціальна здібність: Пасивна. Дроїд напряму атакує здоров'я, ігноруючи броню.\n";
    }
}