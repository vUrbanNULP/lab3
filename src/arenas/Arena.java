package arenas;

public class Arena {
    private String name;
    private int earthquakeDamageModifier;
    private int earthquakeActivationInterval;
    private int armorModifier;

    public Arena(String name, int earthquakeDamageModifier, int earthquakeActivationInterval, int armorModifier) {
        this.name = name;
        this.earthquakeDamageModifier = earthquakeDamageModifier;
        this.earthquakeActivationInterval = earthquakeActivationInterval;
        this.armorModifier = armorModifier;
    }

    public String getName() {
        return name;
    }

    public int getEarthquakeDamageModifier() {
        return earthquakeDamageModifier;
    }

    public int getEarthquakeActivationInterval() {
        return earthquakeActivationInterval;
    }

    public int getArmorModifier() {
        return armorModifier;
    }
}