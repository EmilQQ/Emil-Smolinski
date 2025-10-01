public class Human extends Race{
    private static int maxAge = 100;
    private int manaLevel;

    public Human(int manaLevel) {
        setManaLevel(manaLevel);
    }

    public static int getMaxAge() {
        return maxAge;
    }

    public int getManaLevel() {
        return manaLevel;
    }

    public static void setMaxAge(int maxAge) {
        Human.maxAge = maxAge;
    }

    public void setManaLevel(int manaLevel) {
        this.manaLevel = manaLevel;
    }

    @Override
    public String toString() {
        return "Human{" +
                "manaLevel=" + manaLevel +
                '}';
    }
}
