import java.io.Serializable;
import java.time.LocalDate;

public class EmissionInformation implements Serializable {
    private String status;
    protected LocalDate emissionStart;
    protected LocalDate emissionEnd;
    private String rating;

    public EmissionInformation(String status, LocalDate emissionStart, LocalDate emissionEnd, String rating) {
        setStatus(status);
        setEmissionStart(emissionStart);
        setEmissionEnd(emissionEnd);
        setRating(rating);
    }

    public void stopEmission() {
        status = String.valueOf(StatusAnime.stopped);
    }

    public void resumeEmission() {
        status = String.valueOf(StatusAnime.onGoing);
    }

    public void updateStatusBasedOnDate() {
        if (emissionEnd != null && LocalDate.now().isAfter(emissionEnd)) {
            status = String.valueOf(StatusAnime.finished);
        } else if (emissionStart != null && LocalDate.now().isAfter(emissionStart)) {
            status = String.valueOf(StatusAnime.onGoing);
        } else {
            status = String.valueOf(StatusAnime.planned);
        }
    }


    public void setStatus(String status) {
        if(status == null || status.isEmpty()){
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        if(status.equalsIgnoreCase(String.valueOf(StatusAnime.planned))){
            this.status = String.valueOf(StatusAnime.planned);
        } else if(status.equalsIgnoreCase(String.valueOf(StatusAnime.onGoing))){
            this.status = String.valueOf(StatusAnime.onGoing);
        } else if(status.equalsIgnoreCase(String.valueOf(StatusAnime.finished))){
            this.status = String.valueOf(StatusAnime.finished);
        } else if (status.equalsIgnoreCase(String.valueOf(StatusAnime.stopped))){
            this.status = String.valueOf(StatusAnime.stopped);
        } else {
            throw new IllegalArgumentException("Status must be either planned or onGoing or finished or stopped");
        }

    }

    public String getStatus() {
        return status;
    }

    public void setEmissionStart(LocalDate emissionStart) {
        if(emissionStart == null){
            throw new IllegalArgumentException("EmissionStart cannot be null");
        }
        this.emissionStart = emissionStart;
    }

    public LocalDate getEmissionStart() {
        return emissionStart;
    }

    public void setEmissionEnd(LocalDate emissionEnd) {
        this.emissionEnd = emissionEnd;
    }

    public LocalDate getEmissionEnd() {
        return emissionEnd;
    }

    public void setRating(String rating) {
        if(rating == null || rating.isEmpty()){
            throw new IllegalArgumentException("Rating cannot be null or empty");
        }
        String ratingToFormat = rating.replaceAll("\\D", "");
        int ratingnumber = Integer.parseInt(ratingToFormat);
        if (ratingnumber >= 18){
            rating = "Only for adults. !PORN!";
        }
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", emissionStart=" + emissionStart +
                ", emissionEnd=" + emissionEnd +
                ", rating='" + rating + '\'' +
                '}';
    }
}
