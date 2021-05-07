package race;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CyclingStage {

    private LocalDate date;

    private float km;

    private String winner;

    public CyclingStage(LocalDate date, float km, String winner) {
        this.date = date;
        this.km = km;
        this.winner = winner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " (" + km + ") " + winner;
    }

}
