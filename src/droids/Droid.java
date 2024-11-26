package droids;
import log.LogFile;

public abstract class Droid {
    private String name;
    private int health;
    private int temphealth;
    private int damage;
    private int damageToTake;
    private int accuracy;
    private int armor;
    private int speed;
    private String attackType;
    private int armorStrength;
    private int armorHits;
    protected int numberInTeam;
    public LogFile log;

    public Droid(String name, int health, int damage, int accuracy, int armor, int speed, String attackType) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.accuracy = accuracy;
        this.armor = armor;
        this.speed = speed;
        this.attackType = attackType;
        this.armorStrength = armor / 10;
        this.armorHits = 0;
        this.log = new LogFile();
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getTempHealth() { return temphealth; }
    public int getDamage() { return damage; }
    public int getAccuracy() { return accuracy; }
    public int getArmor() { return armor; }
    public int getSpeed() { return speed; }
    public String getAttackType() { return attackType; }
    public int getArmorStrength() { return armorStrength; }
    public int getNumberInTeam() { return numberInTeam; }

    public void setName(String name) { this.name = name; }
    public void setHealth(int health) { this.health = health; }
    public void setTempHealth(int temphealth) { this.temphealth = temphealth; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setAccuracy(int accuracy) { this.accuracy = accuracy; }
    public void setArmor(int armor) {
        this.armor = armor;
        this.armorStrength = armor / 10;
        this.armorHits = 0;
    }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setAttackType(String attackType) { this.attackType = attackType; }
    public void setNumberInTeam(int numberInTeam) { this.numberInTeam = numberInTeam; }

    public void takeDamage(int damageAmount) {
        damageToTake = damageAmount - armor;
        if (damageToTake <= 0) {
            log.write("У" + " #" + numberInTeam + " Броня заблокувала шкоду!");
            System.out.println("У" + " #" + numberInTeam + " Броня заблокувала шкоду!");
        } else {
            health -= damageToTake;
        }
        if (armor > 0) {
            armorHits++;
            if (armorHits >= armorStrength) {
                armor = 0;
                log.write("#" + numberInTeam + " " + name + " втратив всю броню!");
                System.out.println("#" + numberInTeam + " " + name + " втратив всю броню!");
            }
        }
    }

    public void attack(Droid opponent) {
        useSpecialAbility();
        int hitChance = (int) (Math.random() * 100);
        if (hitChance <= accuracy) {
            opponent.takeDamage(damage);
        } else {
            log.write("#" + numberInTeam + " " + name + " промахнувся!");
            System.out.println("#" + numberInTeam + " " + name + " промахнувся!");
        }
    }

    public abstract void useSpecialAbility();

    @Override
    public String toString() {
        return "Ім'я: " + name +
                ", Здоров'я: " + health +
                ", Шкода: " + damage +
                ", Точність: " + accuracy +
                ", Броня: " + armor +
                ", Швидкість:" + speed +
                ", Тип атаки: " + attackType +
                ", Міцність броні: " + armorStrength;
    }
}

