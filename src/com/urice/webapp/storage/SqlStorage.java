package com.urice.webapp.storage;

import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.*;
import com.urice.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
            deleteContacts(resume);
            deleteSections(resume);
            fillContacts(resume, connection);
            fillSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }

            return r;
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
                    "SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return new ArrayList<>();
                } else {
                    do {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        Resume resume = resumes.computeIfAbsent(uuid, f -> resumes.put(uuid, new Resume(uuid, fullName)));
                    } while (rs.next());
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
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
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
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
                if (e.getValue().getClass().isAssignableFrom(TextSection.class)){
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, String.valueOf(e.getValue()));
                    ps.addBatch();
                } else {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ListSection listSection = (ListSection) e.getValue();
                    List<String> list = listSection.getData();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        sb.append(list.get(i));
                        sb.append("\n");
                    }
                    ps.setString(3, sb.toString());
                    ps.addBatch();
                }

            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume resume) throws SQLException {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSections(Resume resume) throws SQLException {
        sqlHelper.execute("DELETE  FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
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
        AbstractSection strings;
        if (value != null) {
            //strings = value;
//            Pattern pattern = Pattern.compile("\n");
//            strings =  Arrays.asList(pattern.split(value));
            resume.setSection(type, value);
        }

    }

}

