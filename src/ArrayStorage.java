import java.util.ArrayList;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    Resume[] activeResum;
    int arraySize;

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

        for (int i = 0; i < storage.length - 1; i++) {  //убираю нулевые значения, смещая их вправо(если они есть)
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
        size(); //вызываю метод, чтобы выбрать ненулевые элементы
        activeResum = new Resume[arraySize];
        for (int i = 0; i < arraySize; i++) {
            activeResum[i] = storage[i];
        }
        return activeResum;
    }

    int size() {
        arraySize = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {   //перебираю значения, пока цикл не дойдет до нулевого элемента
                arraySize++;
            } else {
                break;
            }

        }

        return arraySize;
    }
}
