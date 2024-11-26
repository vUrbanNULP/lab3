package droids;

public class Musketeer extends Droid {
    private int initialHealth = this.getHealth();
    private boolean deadManRageUsed;

    public Musketeer() {
        super("Musketeer", 45, 35, 95, 8, 65, "Ranged");
        this.deadManRageUsed = false;
    }

    @Override
    public void useSpecialAbility() {
        if (getHealth() <= (initialHealth / 3) && !deadManRageUsed) {
            log.write(getName() + " активує здібність 'Dead Man Rage'. Його шкода збільшена вдвічі до кінця гри.");
            System.out.println(getName() + " активує здібність 'Dead Man Rage'. Його шкода збільшена вдвічі до кінця гри.");
            setDamage(getDamage() * 2);
            deadManRageUsed = true;
        }
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "Спеціальна здібність: Пасивна. Коли здоров'я менше третини, шкод подвоюється до кінця битви.\n";
    }
}