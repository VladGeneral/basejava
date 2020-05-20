package com.urice.webapp.storage;

import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.exception.StorageException;
import com.urice.webapp.model.ContactType;
import com.urice.webapp.model.Resume;
import com.urice.webapp.sql.ExceptionUtil;
import com.urice.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            fillContact("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume r SET full_name =? WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                fillContact("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", resume, connection);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid =?",
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
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid", ps -> {

            ResultSet rs = ps.executeQuery();
            Map<String,Resume> resumes = new HashMap<>();
            Resume resume;
              while (rs.next()) {
                  String uuid = rs.getString("uuid");
                  resume = new Resume(uuid, rs.getString("full_name"));
              resumes.put(uuid,resume);
                  resume.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));

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

    private void fillContact(String sqlString, Resume resume, Connection connection){
        try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
            resume.getContactMap().forEach((key,value)->{
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

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        resume.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
    }
}

