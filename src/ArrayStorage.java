/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;

    void update(Resume resume) {
        if (isResumePresent(resume.uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].equals(resume)) {
                    storage[i].uuid = resume.uuid;
                }
            }
        }
    }

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
        System.out.println("All resume are removed");
    }

    void save(Resume resume) {
        if (!isResumePresent(resume.uuid)) {
            storage[size] = resume;
            size++;
        }
    }

    Resume get(String uuid) {
        if (isResumePresent(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (isResumePresent(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    System.out.println("Resume deleted");
                    break;
                }
            }
        }
    }

    boolean isResumePresent(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.out.println("Resume found");
                return true;
            }
        }
        System.out.println("Resume not found");
        return false;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
