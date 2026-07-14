import java.io.*;
import java.util.*;
public class PSL_Management {
    static ArrayList <String> teams = new ArrayList<>();
    static ArrayList <Integer> played = new ArrayList<>();
    static ArrayList <Integer> wins = new ArrayList<>();
    static ArrayList <Integer> losses = new ArrayList<>();
    static ArrayList <Integer> NR = new ArrayList<>();
    static ArrayList <Integer> points = new ArrayList<>();
    static String header = "";
    static Scanner input = new Scanner(System.in);

    public static void main (String[] args){
        try {
            File file = new File("psl.txt");

            if (!file.exists()) {

                FileOutputStream psl = new FileOutputStream("psl.txt");
                PrintStream ps = new PrintStream(psl);
                ps.printf("%-6s%-8s%-6s%-6s%-6s%-7s%n", "Team", "Played", "Won", "Lost", "NR", "Points");
                ps.printf("%-6s%-8d%-6d%-6d%-6d%-7d%n", "PZ", 0, 0, 0, 0, 0);
                ps.printf("%-6s%-8d%-6d%-6d%-6d%-7d%n", "LQ", 0, 0, 0, 0, 0);
                ps.printf("%-6s%-8d%-6d%-6d%-6d%-7d%n", "MS", 0, 0, 0, 0, 0);
                ps.printf("%-6s%-8d%-6d%-6d%-6d%-7d%n", "IU", 0, 0, 0, 0, 0);
                ps.printf("%-6s%-8d%-6d%-6d%-6d%-7d%n", "QG", 0, 0, 0, 0, 0);
                ps.printf("%-6s%-8d%-6d%-6d%-6d%-7d%n", "KK", 0, 0, 0, 0, 0);

                ps.close();
            }
            Scanner reader = new Scanner(file);
            if (reader.hasNextLine())
                header = reader.nextLine();

            while (reader.hasNextLine()){

                String line = reader.nextLine();
                String [] parts = line.split("\\s+");
                teams.add(parts[0]);
                played.add(Integer.parseInt(parts[1]));
                wins.add(Integer.parseInt(parts[2]));
                losses.add(Integer.parseInt(parts[3]));
                NR.add(Integer.parseInt(parts[4]));
                points.add(Integer.parseInt(parts[5]));

            }
            reader.close();
            sort_Table();
            menu();
        }catch (IOException e){
            System.out.println("Error loading file");
        }
    }

    public static void menu(){
        while (true){
            try {
                System.out.println("---------- MENU -------------");
                System.out.println("1. Submit result");
                System.out.println("2. Display points table");
                System.out.println("3. Display qualifiers");
                System.out.println("4. Add a new team");
                System.out.println("5. Remove a team");
                System.out.println("6. Search for a team");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                input.nextLine();

                switch (choice){
                    case 1:
                        submit_result();
                        break;
                    case 2:
                        display_status();
                        break;
                    case 3:
                        display_Qualifiers();
                        break;
                    case 4:
                        add_Team();
                        break;
                    case 5:
                        remove_Team();
                        break;
                    case 6:
                        search_Team();
                        break;
                    case 7:
                        System.exit(0);
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Try again");
                input.nextLine();
            }
        }
    }

    public static void submit_result() {
        try {
            String team1  = "";
            int index_Team1 = -1;
            int index_Team2 = -1;
            boolean found_Team1 = false;
            boolean found_Team2 = false;

            while (true) {

                System.out.print("Enter team 1: ");
                team1 = input.nextLine();

                for (int i = 0; i < teams.size(); i++){

                    if (team1.equalsIgnoreCase(teams.get(i))){
                        index_Team1 = i;
                        found_Team1 = true;
                        played.set(i,played.get(i)+1);
                        break;
                    }
                }
                if (found_Team1)
                    break;
                else
                    System.out.println("Team 1 not found. Try again");
            }

            while (true) {

                System.out.print("Enter team 2: ");
                String team2 = input.nextLine();
                if (team2.equals(team1))
                    System.out.println("Both teams cannot be same");
                else {
                    for (int i = 0; i < teams.size(); i++) {

                        if (team2.equalsIgnoreCase(teams.get(i))) {
                            index_Team2 = i;
                            found_Team2 = true;
                            played.set(i, played.get(i) + 1);
                            break;
                        }
                    }
                    if (found_Team2)
                        break;
                    else
                        System.out.println("Team 2 not found. Try again");
                }
            }

            System.out.println("Enter the result:");
            System.out.println("1. Team 1 won");
            System.out.println("2. Team 2 won");
            System.out.println("3. No result/Tie");
            System.out.print("Enter your choice: ");
            int result  = input.nextInt();

            switch (result){
                case 1:
                    wins.set(index_Team1, wins.get(index_Team1)+1);
                    losses.set(index_Team2, losses.get(index_Team2)+1);
                    points.set(index_Team1, points.get(index_Team1)+2);
                    break;
                case 2:
                    wins.set(index_Team2, wins.get(index_Team2)+1);
                    losses.set(index_Team1, losses.get(index_Team1)+1);
                    points.set(index_Team2, points.get(index_Team2)+2);
                    break;
                case 3:
                    NR.set(index_Team1, NR.get(index_Team1)+1);
                    NR.set(index_Team2, NR.get(index_Team2)+1);
                    points.set(index_Team1, points.get(index_Team1)+1);
                    points.set(index_Team2, points.get(index_Team2)+1);
                    break;
                default:
                    System.out.println("Invalid input");
                    return;
            }
            sort_Table();
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Try again");
            input.nextLine();
        }
    }

    public static void display_status() {
        System.out.println("-------------- POINTS TABLE -----------");
        try {
            File file  = new File("d:\\psl.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                System.out.println(reader.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public static void display_Qualifiers() {
        System.out.println("Qualifiers for finals:");
        if (teams.size() >= 2) {
            System.out.println("1. " + teams.get(0));
            System.out.println("2. " + teams.get(1));
        }
    }
        public static void add_Team(){
        System.out.print("Enter team name: ");
        String newTeam = input.nextLine().toUpperCase();
        if (teams.contains(newTeam)){
            System.out.println("Team already exists");
            return;
        }
        teams.add(newTeam);
        played.add(0);
        wins.add(0);
        losses.add(0);
        NR.add(0);
        points.add(0);
        System.out.println("Team added successfully");

        sort_Table();
    }

    public static void remove_Team(){
        System.out.print("Enter team name: ");
        String team = input.nextLine();
        boolean found = false;

        for (int i = teams.size() - 1 ;i >= 0; i--){
            if (team.equalsIgnoreCase(teams.get(i))){
                teams.remove(i);
                played.remove(i);
                wins.remove(i);
                losses.remove(i);
                NR.remove(i);
                points.remove(i);
                found = true;
                break;
            }
        }
        sort_Table();

        if (!found){
            System.out.println("Team not found");
        }
    }

    public static void search_Team(){
        System.out.print("Enter team name: ");
        String team = input.nextLine();
        boolean found  = false;

        for (int i=0; i< teams.size();i++){
            if (team.equalsIgnoreCase(teams.get(i))){
                System.out.println("Team: " + teams.get(i));
                System.out.println("Matches played: " + played.get(i));
                System.out.println("Matches won: " + wins.get(i));
                System.out.println("Matches lost: " + losses.get(i));
                System.out.println("No result/tie: " + NR.get(i));
                System.out.println("Points: " + points.get(i));
                found = true;
                break;
            }
        }
        if (!found)
            System.out.println("Team not found");
    }
    public static void sort_Table(){
        for (int i = 0; i < teams.size() - 1; i++){

            for (int j = i + 1; j < teams.size(); j++){

                if (points.get(i) < points.get(j) ||
                        (points.get(i).equals(points.get(j)) && wins.get(i) < wins.get(j)))
                {
                    int temp_points = points.get(i);
                    points.set(i,points.get(j));
                    points.set(j,temp_points);

                    int temp_played = played.get(i);
                    played.set(i,played.get(j));
                    played.set(j,temp_played);

                    int temp_won = wins.get(i);
                    wins.set(i,wins.get(j));
                    wins.set(j,temp_won);

                    int temp_lost = losses.get(i);
                    losses.set(i,losses.get(j));
                    losses.set(j, temp_lost);

                    int temp_NR = NR.get(i);
                    NR.set(i,NR.get(j));
                    NR.set(j,temp_NR);

                    String temp_team = teams.get(i);
                    teams.set(i,teams.get(j));
                    teams.set(j,temp_team);
                }
            }
        }
        save_result();
    }

    public static void save_result(){
        try {
            File file = new File("psl.txt");
            PrintStream ps = new PrintStream(file);
            ps.printf("%s%n",header);

            for (int i = 0; i < teams.size(); i++){
                ps.printf("%-6s",teams.get(i).toUpperCase());
                ps.printf("%-8d",played.get(i));
                ps.printf("%-6d",wins.get(i));
                ps.printf("%-6d",losses.get(i));
                ps.printf("%-6d",NR.get(i));
                ps.printf("%-7d%n",points.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}