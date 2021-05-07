package race;

import java.time.LocalDate;
import java.util.*;

public class Main {

    private static ArrayList<CyclingStage> data;

    private static UserInfoGetter userInfoGetter;

    enum Options {
        ADD_STAGE {
            @Override
            public String toString() {
                return "Add stage";
            }
        },
        SHOW_WINNERS {
            @Override
            public String toString() {
                return "Show winners";
            }
        },
        TOP_WINNERS {
            @Override
            public String toString() {
                return "Top winner";
            }
        },
        EXIT {
            @Override
            public String toString() {
                return "Exit";
            }
        }
    }

    private static void initialize() {
        userInfoGetter = new UserInfoGetter();
        data = FileUtils.loadStats();
    }

    private static boolean dataContainsDateAlready(LocalDate date) {
        boolean containsTheDate = false;
        for ( CyclingStage c : data ) {
            if ( c.getDate().equals(date) ) {
                containsTheDate = true;
                break;
            }
        }
        return containsTheDate;
    }

    private static CyclingStage createStageFromUser() {
        LocalDate date = userInfoGetter.getUserDate("Introduce the date of the cycling stage: ", "yyyy-MM-dd");
        if ( dataContainsDateAlready(date) ) {
            return null;
        }
        System.out.print("Introduce the Km: ");
        float km = Float.parseFloat(userInfoGetter.getUserString("Introduce the km: "));
        String winner = userInfoGetter.getUserString("Introduce the name of the winner: ");
        return new CyclingStage(date, km, winner);
    }

    private static void addStage() {
        CyclingStage stage = createStageFromUser();
        if ( stage != null ) {
            data.add(stage);
            data.sort(Comparator.comparing(CyclingStage::getDate));
            FileUtils.saveStats(data);
        }
    }

    private static ArrayList<String> getWinners() {
        ArrayList<String> winnerNames = new ArrayList<>();
        for ( CyclingStage c : data ) {
            if ( !winnerNames.contains(c.getWinner()) ) {
                winnerNames.add(c.getWinner());
            }
        }
        return winnerNames;
    }

    private static void showWinners() {
        int count = 1;
        for ( String winner : getWinners() ) {
            System.out.println(count + ". " + winner);
            count++;
        }
    }

    private static String getTopWinner() {
        data.sort(Comparator.comparing(CyclingStage::getWinner));
        HashMap<String, Integer> topWinners = new HashMap<>();
        for ( CyclingStage c : data ) {
            if ( !topWinners.containsKey(c.getWinner()) ) {
                topWinners.put(c.getWinner(), 0);
            } else {
                topWinners.replace(c.getWinner(), topWinners.get(c.getWinner()) + 1);
            }
        }
        int maxVictories = 0;
        for ( Map.Entry<String, Integer> e : topWinners.entrySet() ) {
            if ( maxVictories < e.getValue() ) {
                maxVictories = e.getValue();
            }
        }
        for ( Map.Entry<String, Integer> entry : topWinners.entrySet()) {
            if (Objects.equals(maxVictories, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static void showTopWinner() {
        System.out.println("Top winner is: " + getTopWinner());
    }

    private static boolean manageOption(Options opt) {
        switch ( opt ) {
            case EXIT: default:
                System.out.println("Good bye!");
                return false;
            case ADD_STAGE:
                addStage();
                return true;
            case SHOW_WINNERS:
                showWinners();
                return true;
            case TOP_WINNERS:
                showTopWinner();
                return true;
        }
    }

    public static void run() {
        initialize();
        boolean keepRunning;
        do {
            Options opt = userInfoGetter.getUserOptionFromEnum("Options", "Choose une option: ", Options.values());
            keepRunning = manageOption(opt);
        } while ( keepRunning );
    }

    public static void main(String[] args) {
        run();
    }

}
