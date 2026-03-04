package school.coda.tp.rpg.persistance.sqlite;


import school.coda.tp.rpg.persistance.RpgCharacters;
import school.coda.tp.rpg.persistance.model.CharacterJob;
import school.coda.tp.rpg.persistance.model.RpgCharacterData;
import school.coda.tp.rpg.shared.TechnicalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static school.coda.tp.rpg.shared.TechnicalException.ErrorCode.DATABASE_ERROR;

/**
 * {@inheritDoc}
 * <br><p>
 * Implémentation avec une base de données SQlite
 */
public class SqliteRpgCharacters implements RpgCharacters {

    private final Connection connection;

    public SqliteRpgCharacters(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<RpgCharacterData> all() {

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                     SELECT id, name, hp, def, money, atk, heal, job
                     FROM rpg_character
                     """)) {
            List<RpgCharacterData> rpgCharacterData = new ArrayList<>();
            while (resultSet.next()) {
                rpgCharacterData.add(toRpgCharacterData(resultSet));
            }
            return rpgCharacterData;
        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }

    }

    @Override
    public List<RpgCharacterData> allByJob(CharacterJob job) {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT id, name, hp, def, money, atk, heal, job
                FROM rpg_character
                WHERE job = ?
                """);
        ) {

            statement.setString(1, job.name);

            ResultSet resultSet = statement.executeQuery();
            List<RpgCharacterData> rpgCharacterData = new ArrayList<>();
            while (resultSet.next()) {
                rpgCharacterData.add(toRpgCharacterData(resultSet));
            }
            return rpgCharacterData;
        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }
    }

    @Override
    public Optional<RpgCharacterData> find(int id) {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT id, name, hp, def, money, atk, heal, job
                FROM rpg_character
                WHERE id = ?
                """)
        ) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(toRpgCharacterData(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }
    }


    @Override
    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement("""
                DELETE
                FROM rpg_character
                WHERE id = ?
                """)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }
    }


    @Override
    public void update(RpgCharacterData characterData) {
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE rpg_character
                SET name = ?,
                    hp = ?,
                    def = ?,
                    money = ?,
                    atk = ?,
                    heal = ?,
                    job = ?
                WHERE id = ?
                """)
        ) {
            statement.setString(1, characterData.name());
            statement.setInt(2, characterData.hp());
            CharacterJob job = characterData.job();
            statement.setInt(3, characterData.def());
            statement.setInt(4, characterData.money());
            if (characterData.atk() != null) {
                statement.setInt(5, characterData.atk());
            }
            if (characterData.heal() != null) {
                statement.setInt(6, characterData.heal());
            }
            if (job != null) {
                statement.setString(7, job.name);
            }
            statement.setInt(8, characterData.id());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }

    }

    @Override
    public int create(RpgCharacterData characterData) {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO rpg_character
                       (name, hp, def, money, atk, heal, job)
                VALUES (?   , ? , ?  , ?    , ?  , ?   , ?  )
                """)
        ) {
            statement.setString(1, characterData.name());
            statement.setInt(2, characterData.hp());
            CharacterJob job = characterData.job();
            statement.setInt(3, characterData.def());
            statement.setInt(4, characterData.money());
            if (characterData.atk() != null) {
                statement.setInt(5, characterData.atk());
            }
            if (characterData.heal() != null) {
                statement.setInt(6, characterData.heal());
            }
            if (job != null) {
                statement.setString(7, job.name);
            }
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt("last_insert_rowid()");
                } else {
                    throw new TechnicalException("last_insert_rowid() on INSERT is not supported");
                }
            }

        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }

    }


    private RpgCharacterData toRpgCharacterData(ResultSet resultSet) throws SQLException {
        return new RpgCharacterData(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("hp"),
                resultSet.getInt("def"),
                resultSet.getInt("money"),
                resultSet.getInt("atk"),
                resultSet.getInt("heal"),
                CharacterJob.fromName(resultSet.getString("job"))
        );
    }
}
