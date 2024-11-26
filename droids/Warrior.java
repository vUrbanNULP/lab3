package droids;

public class Warrior extends Droid {
    private boolean shieldRaised;

    public Warrior() {
        super("Warrior", 100, 25, 90, 20, 20, "Melee");
        this.shieldRaised = false;
    }

    @Override
    public void takeDamage(int damageAmount) {
        super.takeDamage(damageAmount);
        useSpecialAbility();
    }

    @Override
    public void useSpecialAbility() {
        if (getArmor() <= 0 && !shieldRaised) {
            log.write(getName() + " активує здібність 'Raise shields' і відновлює броню.");
            System.out.println(getName() + " активує здібність 'Raise shields' і відновлює броню.");
            setArmor(20);
            shieldRaised = true;
        }
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "Спеціальна здібність: Один раз за бій може повністю відновити собі броню після її знищення.\n";
    }
}
