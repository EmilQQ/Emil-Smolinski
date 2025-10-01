import java.time.LocalDate;

public class Contract extends ObjectPlus{
    private Anime anime;
    private Worker worker;
    private LocalDate HireDate;

    public Contract(Anime anime, Worker worker){
        setAnime(anime);
        setWorker(worker);
        HireDate = LocalDate.now();

        anime.addContract(this);
        worker.addContract(this);
    }

    public void setAnime(Anime anime) {
        if (anime != null) {
            this.anime = anime;
        }
    }

    public void setWorker(Worker worker) {
        if (worker != null) {
            this.worker = worker;
        }
    }

    @Override
    public void removeFromExtent(){
        this.anime.removeContract(this);
        this.worker.removeContract(this);

        this.anime = null;
        this.worker = null;

        super.removeFromExtent();
    }

    public Anime getAnime() {
        return anime;
    }

    public Worker getWorker() {
        return worker;
    }

    public LocalDate getHireDate() {
        return HireDate;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "anime=" + anime +
                ", worker=" + worker +
                ", HireDate=" + HireDate +
                '}' + '\n';
    }
}
