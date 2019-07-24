import java.util.ArrayList;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            }
        }
        System.out.println("All resume are removed");
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                System.out.println("Resume saved");
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume getValue = null;
        for (Resume resume : storage) {
            if (resume != null && resume.uuid.equals(uuid)) {
                getValue = resume;
                break;
            }
        }
        return getValue;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                System.out.println("Resume deleted");
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        List<Resume> activeResume = new ArrayList<>();
        for (Resume resume : storage) {
            if (resume != null) {
                activeResume.add(resume);
            }
        }
        return activeResume.toArray(new Resume[0]);
    }

    int size() {
        int arraySize = 0;
        for (Resume resume : storage) {
            while (resume != null) {
                arraySize++;
            }
        }
        return arraySize;
    }
}
