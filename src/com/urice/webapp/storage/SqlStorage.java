package com.urice.webapp.storage;

import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.*;
import com.urice.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            /*Больше информации
            https://jdbc.postgresql.org/documentation/81/load.html*/
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) " +
                            "VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            fillContacts(resume, connection);
            fillSections(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume r " +
                            "SET full_name =? " +
                            "WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(resume, connection);
            deleteSections(resume, connection);
            fillContacts(resume, connection);
            fillSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume " +
                            "WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM contact " +
                            "WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM section " +
                            "WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute(
                "DELETE FROM resume r " +
                        "WHERE r.uuid =?", ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        /*return sqlHelper.execute(
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "ORDER BY full_name, uuid", ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    if (!rs.next()) {
                        return new ArrayList<>();
                    } else {
                        do {
                            String uuid = rs.getString("uuid");
                            String fullName = rs.getString("full_name");
                            Resume resume = resumes.computeIfAbsent(uuid, f -> new Resume(uuid, fullName));
                            addContact(rs, resume);
                        } while (rs.next());
                        return new ArrayList<>(resumes.values());
                    }
                });*/
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM resume " +
                            "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    resumes.computeIfAbsent(uuid, f -> resumes.put(uuid, new Resume(uuid, fullName)));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute(
                "DELETE FROM resume");
    }

    @Override
    public int size() {
        return sqlHelper.execute(
                "SELECT COUNT(*) FROM resume", ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    private void fillContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) " +
                        "VALUES (?,?,?) " +
                        "ON CONFLICT (resume_uuid,type) " +
                        "DO UPDATE SET value = excluded.value")) {
            for (Map.Entry<ContactType, String> e : resume.getContactMap().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void fillSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) " +
                        "VALUES (?,?,?) " +
                        "ON CONFLICT (resume_uuid,type) " +
                        "DO UPDATE SET value = excluded.value")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSectionMap().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                switch (e.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, String.valueOf(e.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ps.setString(3, String.join("\n", ((ListSection) e.getValue()).getData()));
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE  FROM contact " +
                        "WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE  FROM section " +
                        "WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (value != null) {
            resume.setContact(ContactType.valueOf(type), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        String value = rs.getString("value");
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                resume.setSection(type, new TextSection(value));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.setSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                break;
        }
    }
}

