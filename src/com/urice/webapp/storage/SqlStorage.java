package com.urice.webapp.storage;

import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.exception.StorageException;
import com.urice.webapp.model.ContactType;
import com.urice.webapp.model.Resume;
import com.urice.webapp.sql.ExceptionUtil;
import com.urice.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
            fillContact(resume, connection);
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
                fillContact(resume, connection);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        ContactType contactType = ContactType.valueOf(rs.getString("type"));
                        resume.setContact(contactType, value);
                    } while (rs.next());
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
        return sqlHelper.execute(
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "ORDER BY full_name, uuid", ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> list = getContact(rs);
                    return list;
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

    private void fillContact(Resume resume, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) " +
                        "VALUES (?,?,?) " +
                        "ON CONFLICT (resume_uuid,type) " +
                        "DO UPDATE SET value = excluded.value")) {
            resume.getContactMap().forEach((key, value) -> {
                try {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, key.name());
                    ps.setString(3, value);
                    ps.addBatch();
                } catch (SQLException e) {
                    throw ExceptionUtil.convertException(e);
                }
            });
            ps.executeBatch();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private List<Resume> getContact(ResultSet rs) throws SQLException {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        Resume resume;
        while (rs.next()) {
            String uuid = rs.getString("uuid");
            String fullName = rs.getString("full_name");
            resume = resumes.get(uuid);
            if (resume == null) {
                resume = new Resume(uuid, fullName);
                resumes.put(uuid, resume);
            }
            resume.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
        }
        return new ArrayList<>(resumes.values());
    }
}

