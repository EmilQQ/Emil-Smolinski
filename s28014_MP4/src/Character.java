public abstract class Character extends ObjectPlus{
    private Race race;
    private String voiceActor;
    private String characterName;
    private String characterSurName;

    public Character(Race race, String voiceActor, String characterName, String characterSurName){
        setRace(race);
        setVoiceActor(voiceActor);
        setCharacterName(characterName);
        setCharacterSurName(characterSurName);
        race.setCharacter(this);
    }

    public Race getRace() {
        return race;
    }

    public String getVoiceActor() {
        return voiceActor;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterSurName() {
        return characterSurName;
    }

    private void setRace(Race race){
        this.race = race;
    }

    public void setVoiceActor(String voiceActor) {
        this.voiceActor = voiceActor;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCharacterSurName(String characterSurName) {
        this.characterSurName = characterSurName;
    }

    @Override
    public String toString() {
        return "Character{" +
                "race=" + race +
                ", voiceActor='" + voiceActor + '\'' +
                ", characterName='" + characterName + '\'' +
                ", characterSurName='" + characterSurName + '\'' +
                '}';
    }
}
