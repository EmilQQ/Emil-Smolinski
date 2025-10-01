public class Film extends ObjectPlus{
    private int durationTime;

    public Film(int durationTime) {
        setDurationTime(durationTime);
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }
}
