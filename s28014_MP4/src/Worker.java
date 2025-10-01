import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class Worker extends Person implements Animator, Editor, Director {
    private String mostValuedFeature;
    private List<Contract> animesList = new ArrayList<>();
    private final Map<WorkerSpeciality, Integer> workerSpecialities;

    private List<String> toolsUsed;  //Animator
    private boolean specialEffectsSkills;  //Animator
    private List<String> editingSoftware;  //Editor
    private int directedProjectsCount;  //Director
    private boolean awardWinning;  //Director
    private String visionStatement;  //Director

    public Worker(String name, String surname, String mostValuedFeature, Map<WorkerSpeciality, Integer> workerSpecialities) {
        super(name, surname);
//        if (workerSpecialities == null || workerSpecialities.isEmpty()){
//            throw new NullPointerException("workerSpecialities is null or empty");
//        }
        this.workerSpecialities = workerSpecialities;
        setmostValuedFeature(mostValuedFeature);
    }

    @Override
    public void introduce() {
        System.out.println("Jestem " + getName() + " " + getSurname() + ". Uważam że cecha najbardziej mnie opisująca to " + getmostValuedFeature() + ".");

        for (Map.Entry<WorkerSpeciality, Integer> entry : workerSpecialities.entrySet()) {
            WorkerSpeciality speciality = entry.getKey();
            int years = entry.getValue();
            String title;

            if (years < 2) {
                title = "junior";
            } else if (years > 6) {
                title = "senior";
            } else {
                title = "mid";
            }

            System.out.println("Specjalizacja: " + speciality + " – poziom: " + title +
                    " (" + years + " lat doświadczenia)");

            try {
                switch (speciality) {
                    case Animator -> {
                        System.out.println("  Narzędzia: " + String.join(", ", getToolsUsed()));
                        System.out.println("  Efekty specjalne: " + (hasSpecialEffectsSkills() ? "tak" : "nie"));
                    }
                    case Editor -> {
                        System.out.println("  Oprogramowanie montażowe: " + String.join(", ", getEditingSoftware()));
                    }
                    case Director -> {
                        System.out.println("  Liczba wyreżyserowanych projektów: " + getDirectedProjectsCount());
                        System.out.println("  Nagrody: " + (hasWonAwards() ? "tak" : "nie"));
                        System.out.println("  Wizja: \"" + getVisionStatement() + "\"");
                    }
                }
            } catch (UnsupportedOperationException e) {
                System.out.println("Bład wypisywania specjalizacji!");
            }
        }

    }

    public void addAnime(Anime anime) throws Exception{
        for (Contract contract : animesList){                        //BAG jest bez tego
            if (contract.getAnime().equals(anime)){
                throw new Exception("This worker is already working on this anime");
            }
        }
        new Contract(anime, this);
    }

    protected void addContract(Contract contract){
        animesList.add(contract);
    }

    public void removeAnime(Anime anime){
        for (Contract contract : animesList){
            if (contract.getAnime().equals(anime)){
                contract.removeFromExtent();
            }
        }
    }

    protected void removeContract(Contract contract){
        animesList.remove(contract);
    }

    public List<String> getToolsUsed() {
        if (!workerSpecialities.containsKey(WorkerSpeciality.Animator)){
            throw new UnsupportedOperationException("Worker is not an Animator");
        }
        if (toolsUsed == null || toolsUsed.isEmpty()){
            List<String> list = new ArrayList<>();
            list.add(null);
            return list;
        }
        return toolsUsed;
    }

    public void setToolsUsed(List<String> toolsUsed) {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Animator)){
            throw new UnsupportedOperationException("Worker is not an Animator");
        }
        this.toolsUsed = toolsUsed;
    }

    public boolean hasSpecialEffectsSkills() {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Animator)){
            throw new UnsupportedOperationException("Worker is not an Animator");
        }
        return specialEffectsSkills;
    }

    public void setSpecialEffectsSkills(boolean specialEffectsSkills) {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Animator)){
            throw new UnsupportedOperationException("Worker is not an Animator");
        }
        this.specialEffectsSkills = specialEffectsSkills;
    }

    public List<String> getEditingSoftware() {
        if (!workerSpecialities.containsKey(WorkerSpeciality.Editor)){
            throw new UnsupportedOperationException("Worker is not an Editor");
        }
        if (editingSoftware == null || editingSoftware.isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add(null);
            return list;
        }
        return editingSoftware;
    }

    public void setEditingSoftware(List<String> editingSoftware) {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Editor)){
            throw new UnsupportedOperationException("Worker is not an Editor");
        }
        this.editingSoftware = editingSoftware;
    }

    public int getDirectedProjectsCount() {
        if (!workerSpecialities.containsKey(WorkerSpeciality.Director)){
            throw new UnsupportedOperationException("Worker is not an Director");
        }
        return directedProjectsCount;
    }

    public void setDirectedProjectsCount(int directedProjectsCount) {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Director)){
            throw new UnsupportedOperationException("Worker is not an Director");
        }
        this.directedProjectsCount = directedProjectsCount;
    }

    public boolean hasWonAwards() {
        if (!workerSpecialities.containsKey(WorkerSpeciality.Director)){
            throw new UnsupportedOperationException("Worker is not an Director");
        }
        return awardWinning;
    }

    public void setWonAwards(boolean awardWinning) {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Director)){
            throw new UnsupportedOperationException("Worker is not an Director");
        }
        this.awardWinning = awardWinning;
    }

    public String getVisionStatement() {
        if (!workerSpecialities.containsKey(WorkerSpeciality.Director)){
            throw new UnsupportedOperationException("Worker is not an Director");
        }
        return visionStatement;
    }

    public void setVisionStatement(String visionStatement) {
        if(!workerSpecialities.containsKey(WorkerSpeciality.Director)){
            throw new UnsupportedOperationException("Worker is not an Director");
        }
        this.visionStatement = visionStatement;
    }

    public List<WorkerSpeciality> getWorkerSpecialities() {
        return workerSpecialities.entrySet().stream().map(entry -> entry.getKey()).toList();
    }

    public String getmostValuedFeature() {
        return mostValuedFeature;
    }

    public void setmostValuedFeature(String mostValuedFeature) {
        this.mostValuedFeature = mostValuedFeature.toLowerCase();
    }



    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mostValuedFeature='" + mostValuedFeature + '\'' +
                ", WorkerSpecialities=" + getWorkerSpecialities() +
                ", animesList=" + animesList.stream().map(c -> c.getAnime().getTitle() + "/" + c.getHireDate()).toList() +
                '}';
    }
}
