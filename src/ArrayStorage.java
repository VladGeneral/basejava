import java.util.Arrays;

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
        Arrays.fill(storage,null);
        size = 0;
        System.out.println("Resume - All removed");
    }

    void save(Resume resume) {
        if (!isResumePresent(resume.uuid)) {
            if (size < storage.length - 1){
                storage[size] = resume;
                size++;
            }
            else {
                System.out.println("Too many resumes in array");
            }
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
        return Arrays.copyOf(storage,size);
    }

    int size() {
        return size;
    }
}
