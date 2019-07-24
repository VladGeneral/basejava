/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
        System.out.println("All resume are removed");
    }

    void save(Resume resume) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                size++;
                System.out.println("Resume saved");
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (Resume o : storage) {
            if (o != null && o.uuid.equals(uuid)) {
                resume = o;
                break;
            }
        }
        return resume;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                size--;
                System.out.println("Resume deleted");
                break;
            }
        }

        for (int i = 0; i < size; i++) {  //убираю нулевые значения, смещая их вправо(если они есть)
            if (storage[i] == null) {
                storage[i] = storage[i + 1];
                storage[i + 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] activeResum = new Resume[size];
        for (int i = 0; i < size; i++) {
            activeResum[i] = storage[i];
        }
        return activeResum;
    }

    int size() {
        return size;
    }
}
