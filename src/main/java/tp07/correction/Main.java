package tp07.correction;

import tp07.correction.infra.inmemory.InMemoryGiftTypes;
import tp07.correction.shared.BusinessException;
import tp07.correction.shared.TechnicalException;
import tp07.correction.infra.sql.ConnectionManager;
import tp07.correction.infra.sql.sqlite.GiftDatabaseSchema;
import tp07.correction.infra.sql.sqlite.SqLiteGiftTypes;
import tp07.correction.tui.GiftTypesAdmin;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--in-memory")) {
            System.out.println("🧠 exécution en mémoire !");
            System.out.println();
            InMemoryGiftTypes giftTypes = new InMemoryGiftTypes();
            if (Arrays.asList(args).contains("--seed-dataset")) {
                giftTypes.seedInitialData();
            }
            GiftTypesAdmin giftTypesAdmin = new GiftTypesAdmin(giftTypes);
            giftTypesAdmin.interactiveMenu();
        } else {
            System.out.println("🌪️ exécution en base de données !");
            // NOTE: Connection and Statement are AutoCloseable.
            //       Don't forget to close them both in order to avoid leaks.
            try (ConnectionManager connectionManager = new ConnectionManager("jdbc:sqlite:gifts.db")) {

                // Database initialization
                GiftDatabaseSchema schema = new GiftDatabaseSchema(connectionManager);
                schema.initDdl();
                GiftTypes giftTypes = new SqLiteGiftTypes(connectionManager);
                if (Arrays.asList(args).contains("--seed-dataset")) {
                    giftTypes.seedInitialData();
                }

                GiftTypesAdmin giftTypesAdmin = new GiftTypesAdmin(giftTypes);
                giftTypesAdmin.interactiveMenu();
            } catch (TechnicalException e) {
                // Not leaking technical inner details to user
                System.err.println("Une erreur technique inattendue est survenue (" + e.getMessage() + ")");
                // We would log it somewhere with a logger for developer debug purpose
            } catch (BusinessException e) {
                System.out.println(e.getMessage());
            }

        }


    }
}
