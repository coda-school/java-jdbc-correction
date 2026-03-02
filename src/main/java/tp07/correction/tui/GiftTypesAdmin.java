package tp07.correction.tui;

import tp07.correction.GiftType;
import tp07.correction.GiftTypes;
import tp07.correction.shared.CouldNotAddGiftTypeException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

import static java.util.stream.Collectors.joining;
import static tp07.correction.tui.GiftTypesAdmin.MenuChoice.HELP;
import static tp07.correction.tui.GiftTypesAdmin.MenuChoice.QUIT;

// Maven

// Optional
// Stream
// JDBC
// Autocloseable + try with resources
// Polymorphisme
// L de SOLID
// D de SOLID
public class GiftTypesAdmin {

    private final GiftTypes giftTypes;

    public GiftTypesAdmin(GiftTypes giftTypes) {
        this.giftTypes = giftTypes;
    }

    public void interactiveMenu() {

        Scanner scanner = new Scanner(System.in);
        MenuChoice menuChoice = HELP;

        while (!QUIT.equals(menuChoice)) {
            switch (menuChoice) {
                case LIST -> list();
                case ADD -> add(scanner);
                case DELETE -> delete(scanner);
                case UPDATE -> update(scanner);
                case INITIALIZE -> initializeSample();
                case null, default -> printHelp();
            }
            System.out.print("\n> ");
            menuChoice = MenuChoice.fromSymbol(scanner.next());
        }
    }

    private static void printHelp() {
        System.out.println(MenuChoice.formatMenu());
    }

    private void initializeSample() {
        Collection<GiftType> initialData = giftTypes.seedInitialData();
        System.out.println(initialData.stream().map(GiftTypesAdmin::formatGiftType).collect(joining("\n")));
    }

    private void update(Scanner scanner) {
        System.out.println("Modifier un type de cadeau");
        String allNamesSorted = giftTypes.allNames().stream()
                .sorted()
                .collect(joining(", "));
        System.out.println("Tous les noms de cadeaux : " + allNamesSorted);
        System.out.print("Nom : ");
        var name = scanner.next();
        Optional<GiftType> optionalGiftType = giftTypes.findByName(name);
        if (optionalGiftType.isEmpty()) {
            System.out.println("Le type de cadeau n'existe pas");
        } else {
            GiftType existingGiftType = optionalGiftType.get();
            System.out.println(formatGiftType(existingGiftType) + "\n");
            GiftType giftType = promptGiftDetails(scanner, name);
            giftTypes.update(giftType);
            System.out.println("Type de cadeau modifié : " + formatGiftType(giftType));
        }
    }

    private static GiftType promptGiftDetails(Scanner scanner, String name) {
        System.out.print("Logo : ");
        var logo = scanner.next();
        System.out.print("Libellé : ");
        String label = scanner.next();
        label += scanner.nextLine();
        return new GiftType(name, logo, label);
    }

    private void delete(Scanner scanner) {
        System.out.println("Supprimer un type de cadeau");
        String allNamesSorted = giftTypes.allNames().stream()
                .sorted()
                .collect(joining(", "));
        System.out.println("Tous les noms de cadeaux : " + allNamesSorted + "\n");
        System.out.println("Saisissez le nom du type de cadeau à supprimer");
        var name = scanner.next();
        giftTypes.deleteByName(name);
        System.out.println("Le cadeau a été supprimé");
    }

    private void add(Scanner scanner) {
        System.out.println("Ajouter un type de cadeau\n");
        System.out.print("Nom : ");
        var name = scanner.next();
        GiftType giftType = promptGiftDetails(scanner, name);
        try {
            giftTypes.add(giftType);
            System.out.println("\nType de cadeau ajouté : " + formatGiftType(giftType));
        } catch (
                CouldNotAddGiftTypeException e) {
            System.err.println("Le type de cadeau n'a pas pu être ajouté !");
        }

    }

    private void list() {
        String collect = giftTypes.findAll()
                .stream().map(GiftTypesAdmin::formatGiftType)
                .collect(joining("\n"));
        if (collect.isEmpty()) {
            System.out.println("Aucun type de cadeaux pour le moment");
        } else {
            System.out.println(collect);
        }
    }

    private static String formatGiftType(GiftType giftType) {
        return padRight(giftType.name(), 20) + giftType.logo() + " " + giftType.label();
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public enum MenuChoice {
        HELP("h", "cette aide"),
        LIST("l", "lister les types de cadeau"),
        ADD("a", "ajouter un type de cadeau"),
        DELETE("s", "supprimer un type de cadeau"),
        UPDATE("m", "modifier un type de cadeau"),
        INITIALIZE("i", "(ré)initialiser des types de cadeau standard"),
        QUIT("q", "quitter");

        public final String symbol;
        public final String description;

        MenuChoice(String symbol, String description) {
            this.symbol = symbol;
            this.description = description;
        }

        public static MenuChoice fromSymbol(String entry) {
            return Arrays.stream(values())
                    .filter(menuChoice -> menuChoice.symbol.equalsIgnoreCase(entry))
                    .findFirst()
                    .orElse(HELP);
        }

        public static String formatMenu() {
            return Arrays.stream(values())
                    .map((MenuChoice menuChoice) -> "   " + menuChoice.symbol + " : " + menuChoice.description)
                    .collect(joining("\n"));
        }
    }
}



