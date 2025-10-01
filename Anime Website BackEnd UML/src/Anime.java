import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa Anime reprezentuje animację będącą adaptacją light novel i mangi
 * Dziedziczy po klasie Manga a implementuje interfejs ILight_Novel
 * Obsługuje relacje z pracownikami, studiem, komentarzami, kategoriami oraz informacje o emisji
 * Dodatkowo umożliwia rozróżnienie czy obiekt to film czy serial(XOR)
 */

public class Anime extends Manga implements ILight_Novel{
    private int id;
    private final List<Category> categories = new ArrayList<>();
    private EmissionInformation emissionInformation;
    private String targetGroup;
    private static int minNumberOfEpisodes = 12;

    private List<Comment> comments = new ArrayList<>();

    private List<Contract> workersList = new ArrayList<>();

    private Studio studio;

    private Light_Novel lightNovel;

    private Film film;
    private Series series;
    private filmXORseries filmxorseries;

    /**
     * Ustawia anime jako film
     * @param film do połączenia
     * @throws IllegalStateException jeśli wcześniej był ustawiony serial
     */
    public void setFilm(Film film) {
        if (filmxorseries != null) {
            throw new IllegalStateException("Series already set");
        }
        this.film = film;
        filmxorseries = filmXORseries.film;
    }

    /**
     * Ustawia anime jako serial
     * @param series do połączenia
     * @throws IllegalStateException jeśli wcześniej był ustawiony film
     */
    public void setSeries(Series series) {
        if (filmxorseries != null) {
            throw new IllegalStateException("Film already set");
        }
        this.series = series;
        filmxorseries = filmXORseries.series;
    }

    /**
     * Zwraca typ adaptacji
     * @return film lub series
     */
    public String getFilmXORseries() {
        return filmxorseries.toString();
    }

    /**
     * Ustawia referencje do studia jeśli nie była jeszcze przypisana
     * @param studio do przypisania
     */
    public void setStudio(Studio studio) {
        if (this.studio == null) {
            this.studio = studio;
            studio.addAnime(this);
        }
    }

    /**
     * Usuwa referencje do studia jeśli było ustawione
     * @param studio do usunięcia z przypisania
     */
    public void removeStudio(Studio studio){
        if (this.studio != null){
            this.studio = null;
            studio.removeAnime(this);
        }
    }

    /**
     * Dodaje pracownika do listy pracowników. Anime i Worker łączy klasa Contract
     * @param worker do dodania
     * @throws Exception jeśli to anime jest już przypisane z tym pracownikiem
     */
    public void addWorker(Worker worker) throws Exception{
        for (Contract contract : workersList){                                //BAG jest bez tego
            if (contract.getWorker().equals(worker)){
                throw new Exception("This anime is already connected with this worker");
            }
        }
        new Contract(this, worker);
    }

    /**
     * Dodaje kontrakt do listy
     * @param contract do dodania
     */
    protected void addContract(Contract contract){
        workersList.add(contract);
    }

    /**
     * Usuwa wskazanego pracownika. Jednak najpierw wyciąga do oddzielnej listy aby nie zmieniać listy po której iterujemy
     * @param worker do usunięcia z listy
     */
    public void removeWorker(Worker worker){
        List<Contract> toRemove = new ArrayList<>();

        for (Contract contract : workersList){
            if (contract.getWorker().equals(worker)){
                toRemove.add(contract);
            }
        }

        for (Contract contract : toRemove){
            contract.removeFromExtent();
        }
    }

    /**
     * Usuwa kontrakt z listy kontraktów
     * @param contract do usunięcia
     */
    protected void removeContract(Contract contract){
        workersList.remove(contract);
    }

    /**
     * Dodaje komentarz do listy komentarzy dla anime, jednak tylko jeśli nie było już go
     * @param comment do dodania
     */
    public void addComment(Comment comment){
        if(!comments.contains(comment)){
            comments.add(comment);
            comment.setAnime(this);
        }
    }

    /**
     * Usuwa komentarz z puli wszystkich
     * @param comment do usunięcia
     */
    public void removeComment(Comment comment){
        if(comments.remove(comment)){
            comment.removeAnime(this);
        }
    }

    /**
     * Zwraca liczbe kategorii jako atrybut pochodny od rozmiaru listy kategorii
     * @return liczbe kategorii
     */
    public int getNumberOfCategories(){
        return categories.size();
    }

    /**
     * Metoda statyczna do wyszukiwania Anime po id
     * @param id jako klasyfikator
     * @return anime do którego id jest przypisany
     */
    public static Anime searchAfterId(int id){
        return ObjectPlus.getExtentFromClass(Anime.class)
                .stream().filter(anime -> anime.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Tworzy nowe anime na podstawie tytulu, autora, id, light novel i informacji o emisji
     * @param title Tytuł
     * @param author Autor
     * @param id Id
     * @param lightNovel Light_Novel
     * @param emissionInformation Informacje o emisji
     */
    public Anime(String title, String author, int id, Light_Novel lightNovel, EmissionInformation emissionInformation){
        super(title, author);
        try{
            setId(id);
            setLightNovel(lightNovel);
            this.lightNovel.setAnime(this);
            setEmissionInformation(emissionInformation);
        } catch (Exception e){
            e.printStackTrace();
            removeFromExtent();
        }
    }

    /**
     * Tworzy anime jak wcześniej tylko że z dodatkowym atrybutem opcjonalnym docelowa grupa wiekowa
     * @param title Tytuł
     * @param author Autor
     * @param id Id
     * @param lightNovel Light_Novel
     * @param emissionInformation Informacje o emisji
     * @param targetGroup Docelowa grupa wiekowa
     */
    public Anime(String title, String author, int id, Light_Novel lightNovel, EmissionInformation emissionInformation, String targetGroup){
        super(title, author);
        try{
            setId(id);
            setLightNovel(lightNovel);
            this.lightNovel.setAnime(this);
            setEmissionInformation(emissionInformation);
            setTargetGroup(targetGroup);
        } catch (Exception e){
            e.printStackTrace();
            removeFromExtent();
        }
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        if(targetGroup == null || targetGroup.isEmpty()){
            throw new IllegalArgumentException("Target group cannot be empty");
        }
        this.targetGroup = targetGroup;
    }

    public List<Contract> getWorkersList() {
        return workersList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmissionInformation getEmissionInformation() {
        return emissionInformation;
    }

    public void setEmissionInformation(EmissionInformation emissionInformation) {
        this.emissionInformation = emissionInformation;
    }

    /**
     * Zwraca kategorie jako liste niemodyfikowalna aby zachować spójność danych
     * @return liste kategorii
     */
    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }


    /**
     * Dodaje kategorie dla danego anime
     * @param category do dodania
     */
    public void addCategory(Category category){
        if(category.getName() == null || category.getName().isEmpty()){
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        categories.add(category);
    }

    /**
     * Usuwa kategorie z anime
     * @param category do usuniecia
     * @throws IllegalArgumentException możliwie jeśli anime nie ma takiej przypisanej kategorii
     */
    public void removeCategory(Category category){
        if(categories.contains(category)){
            categories.remove(category);
        } else {
            throw new IllegalArgumentException("Category not found");
        }
    }

    /**
     * Zwraca stałą liczbę minimalnej ilości odcinków dla anime
     * @return liczbe
     */
    public static int getMinNumberOfEpisodes() {
        return minNumberOfEpisodes;
    }

    /**
     * Ustawia minimalna ilosc odcinków dla anime
     * @param minNumberOfEpisodes min liczba odc
     */
    public static void setMinNumberOfEpisodes(int minNumberOfEpisodes) {
        Anime.minNumberOfEpisodes = minNumberOfEpisodes;
    }

    @Override
    public String toString() {
        emissionInformation.updateStatusBasedOnDate();
        return "Anime{" +
                "title=" + getTitle() +
                ", originalTitle='" + getOriginalTitle() + '\'' +
                ", id=" + id +
                ", categories=" + getCategories() +
                ", emissionInformation=" + emissionInformation +
                ", targetGroup='" + targetGroup + '\'' +
                ", comments=" + comments.stream().map(c -> c.getText()).toList() +
                ", workers=" + workersList.stream().map(c -> c.getWorker()).toList() +
                ", studio=" + studio +
                '}' + "\n";
    }

    @Override
    public int getHowManyDaysPassedSinceRelease() {
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(getReleaseDate(), now);
    }

    @Override
    public String getOriginalTitle() {
        return lightNovel.getOriginalTitle();
    }

    @Override
    public String getOriginalAuthor() {
        return lightNovel.getOriginalAuthor();
    }

    @Override
    public LocalDate getReleaseDate() {
        return emissionInformation.getEmissionStart();
    }

    @Override
    public int getChaptersCount() {
        return lightNovel.getChaptersCount();
    }

    @Override
    public void setOriginalTitle(String originalTitle) {
        lightNovel.setOriginalTitle(originalTitle);
    }

    @Override
    public void setOriginalAuthor(String originalAuthor) {
        lightNovel.setOriginalAuthor(originalAuthor);
    }

    @Override
    public void setReleaseDate(LocalDate releaseDate) {
        emissionInformation.emissionStart = releaseDate;
    }

    @Override
    public void setChaptersCount(int chaptersCount) {
        lightNovel.setChaptersCount(chaptersCount);
    }

    public Light_Novel getLightNovel() {
        return lightNovel;
    }

    public void setLightNovel(Light_Novel lightNovel) {
        this.lightNovel = lightNovel;
    }
}
