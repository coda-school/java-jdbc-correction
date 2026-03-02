package school.coda.tp.jdbc.sql;


import school.coda.tp.jdbc.shared.TechnicalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static school.coda.tp.jdbc.shared.TechnicalException.ErrorCode.DATABASE_ERROR;

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

    private static RpgCharacterData toRpgCharacterData(ResultSet resultSet) throws SQLException {
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
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void update(RpgCharacterData rpgCharacterData) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void create(RpgCharacterData rpgCharacterData) {
        throw new UnsupportedOperationException("Not yet implemented");

    }
}
