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
            ArrayList<Player> lineup = new ArrayList<Player>();
            ArrayList<String> openPositions = new ArrayList<String>();
            openPositions.add("PG");
            openPositions.add("SG");
            openPositions.add("SF");
            openPositions.add("PF");
            openPositions.add("C");

            try (Scanner input = new Scanner(System.in)) {
                while (!openPositions.isEmpty()) {
                    if (seasons.isEmpty()) {
                        System.out.println("Not enough unused seasons to finish the lineup.");
                        return;
                    }

                    int seasonIndex = random.nextInt(seasons.size());
                    String selectedSeason = seasons.remove(seasonIndex);
                    ArrayList<Player> roster = new ArrayList<Player>();
                    for (Player player : players) {
                        if (player.getSeason().equals(selectedSeason)
                                && openPositions.contains(player.getPosition())) {
                            roster.add(player);
                        }
                    }
                    if (roster.isEmpty()) {
                        continue;
                    }

                    System.out.println("Season spun: " + selectedSeason);
                    System.out.println("Open positions: " + openPositions);
                    for (int i = 0; i < roster.size(); i++) {
                        System.out.println((i + 1) + ". " + roster.get(i));
                    }

                    int choice = 0;
                    while (choice < 1 || choice > roster.size()) {
                        System.out.print("Choose a player number: ");
                        if (!input.hasNextLine()) {
                            System.out.println("Draft ended before the lineup was complete.");
                            return;
                        }
                        String answer = input.nextLine().trim();
                        try {
                            choice = Integer.parseInt(answer);
                            if (choice < 1 || choice > roster.size()) {
                                System.out.println("Enter a number from 1 to " + roster.size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Enter a whole number.");
                        }
                    }

                    Player selectedPlayer = roster.get(choice - 1);
                    lineup.add(selectedPlayer);
                    openPositions.remove(selectedPlayer.getPosition());
                    System.out.println("Added to lineup: " + selectedPlayer);
                    System.out.println();
                }

                System.out.println("Your completed lineup:");
                for (Player player : lineup) {
                    System.out.println(player);
                }

                int totalRating = 0;
                for (Player player : lineup) {
                    totalRating += player.getRating();
                }
                double teamRating = (double) totalRating / lineup.size();
                System.out.printf("Team rating: %.1f%n", teamRating);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find data/players.csv. Run from the project folder.");
        } catch (NumberFormatException e) {
            System.out.println("A player rating is not a valid whole number.");
        }
    }
}
