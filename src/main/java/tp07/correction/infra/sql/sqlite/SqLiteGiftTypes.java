package tp07.correction.infra.sql.sqlite;

import tp07.correction.shared.TechnicalException;
import tp07.correction.shared.CouldNotUpdateGiftTypeException;
import tp07.correction.GiftType;
import tp07.correction.GiftTypes;
import tp07.correction.infra.sql.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/// Implémentation en base de données (SqLite) du pattern repository pour les types de cadeaux
///
/// L'implémentation interne traduit les demandes de l'interface publiques en requêtes SQL.
///
/// Il s'agit de faire le mapping dans les 2 sens entre les objets de java et les données en base de données.
///
/// Dans la vraie vie, on aura probablement moins de code à écrire car on s'appuiera souvent sur des bibliothèques "ORM"
/// sur étagère. Ex. JPA, Spring Data, JOOQ ...
///
/// Mais le principe reste globalement le même.
public class SqLiteGiftTypes implements GiftTypes {

    private final ConnectionManager connectionManager;

    public SqLiteGiftTypes(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Set<GiftType> findAll() {
        Connection con = connectionManager.getConnection();
        try (Statement statement = con.createStatement();
        ) {

            try (ResultSet resultSet = statement.executeQuery(
                    " SELECT name, logo, label " +
                    " FROM gift_type ");) {
                Set<GiftType> allGiftTypes = new HashSet<>();
                while (resultSet.next()) {
                    // read the result set
                    String name = resultSet.getString("name");
                    String logo = resultSet.getString("logo");
                    String label = resultSet.getString("label");
                    allGiftTypes.add(new GiftType(name, logo, label));
                }
                return Collections.unmodifiableSet(allGiftTypes);
            }

        } catch (SQLException e) {
            throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }

    @Override
    public Optional<GiftType> findByName(String name) {
        Connection con = connectionManager.getConnection();
        try (PreparedStatement statement = con.prepareStatement(
                " SELECT name, logo, label " +
                " FROM gift_type " +
                " WHERE name = ? " +
                " LIMIT 1");
        ) {
            statement.setString(1, name);
            try (
                    ResultSet resultSet = statement.executeQuery();) {
                boolean found = resultSet.next();
                if (found) {
                    String nameFromDb = resultSet.getString("name");
                    String logo = resultSet.getString("logo");
                    String label = resultSet.getString("label");
                    return Optional.of(new GiftType(nameFromDb, logo, label));
                }
                return Optional.empty();
            }


        } catch (SQLException e) {
            throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }

    @Override
    public Set<String> allNames() {
        Connection con = connectionManager.getConnection();
        try (Statement statement = con.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(
                    " SELECT name " +
                    " FROM gift_type ")) {

                Set<String> allGiftTypeNames = new HashSet<>();
                while (resultSet.next()) {
                    // read the result set
                    String name = resultSet.getString("name");
                    allGiftTypeNames.add(name);
                }
                return Collections.unmodifiableSet(allGiftTypeNames);
            }
        } catch (SQLException e) {
            throw new TechnicalException( TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }

    @Override
    public void add(GiftType giftType) {
        Connection con = connectionManager.getConnection();
        try (PreparedStatement statement = con.prepareStatement(
                " INSERT INTO gift_type(name, logo, label) " +
                " VALUES (? , ? , ?) ")) {
            statement.setString(1, giftType.name());
            statement.setString(2, giftType.logo());
            statement.setString(3, giftType.label());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }

    @Override
    public void deleteByName(String name) {
        Connection con = connectionManager.getConnection();
        try (PreparedStatement statement = con.prepareStatement(
                " DELETE FROM gift_type " +
                " WHERE name = ? ")) {
            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }

    @Override
    public void update(GiftType giftType) {
        Connection con = connectionManager.getConnection();
        try (PreparedStatement statement = con.prepareStatement(
                " UPDATE gift_type " +
                " SET label = ? " +
                " ,logo = ? " +
                " WHERE name = ? ")) {
            statement.setString(1, giftType.label());
            statement.setString(2, giftType.logo());
            statement.setString(3, giftType.name());
            int recordsUpdated = statement.executeUpdate();
            if (recordsUpdated == 0) {
                throw new CouldNotUpdateGiftTypeException("No gift type exists with name : " + giftType.name());
            }
        } catch (SQLException e) {
            throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }

    @Override
    public Collection<GiftType> seedInitialData() {
        Set<GiftType> initialData = Set.of(
                new GiftType("robot", "🤖", "Robot articulé"),
                new GiftType("teddy", "🧸", "Ourson en peluche"),
                new GiftType("car", "🚗", "Petite voiture"),
                new GiftType("doll", "🪆", "Poupée magique"),
                new GiftType("book", "📚", "Livre enchanté"));

        // Note : insert or replace
        // est une syntaxe spécifique à SQLite qui peut ne pas fonctionner avec d'autres bases données
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(
                "insert or replace into gift_type (name, logo, label) values(? , ? , ?)")) {
            for (GiftType giftType : initialData) {
                statement.setString(1, giftType.name());
                statement.setString(2, giftType.logo());
                statement.setString(3, giftType.label());
                statement.executeUpdate();
            }
            return initialData;
        } catch (SQLException e) {
            throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
        }
    }
}
