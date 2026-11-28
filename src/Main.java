import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<Player>();

        try (Scanner file = new Scanner(new File("data/players.csv"))) {
            if (file.hasNextLine()) {
                file.nextLine();
            }

            while (file.hasNextLine()) {
                String line = file.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.out.println("Invalid player row: " + line);
                    return;
                }

                String season = parts[0].trim();
                String name = parts[1].trim();
                String position = parts[2].trim();
                int rating = Integer.parseInt(parts[3].trim());

                Player player = new Player(season, name, position, rating);
                players.add(player);
            }

            ArrayList<String> seasons = new ArrayList<String>();
            for (Player player : players) {
                if (!seasons.contains(player.getSeason())) {
                    seasons.add(player.getSeason());
                }
            }

            if (seasons.isEmpty()) {
                System.out.println("No players were loaded.");
                return;
            }

            Random random = new Random();
            String selectedSeason = seasons.get(random.nextInt(seasons.size()));
            System.out.println("Season spun: " + selectedSeason);
            for (Player player : players) {
                if (player.getSeason().equals(selectedSeason)) {
                    System.out.println(player);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find data/players.csv. Run from the project folder.");
        } catch (NumberFormatException e) {
            System.out.println("A player rating is not a valid whole number.");
        }
    }
}
