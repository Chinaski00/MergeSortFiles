import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergeSortFiles {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Недостаточно аргументов. Использование: java MergeSortFiles <режим> <тип данных> <выходной файл> <входные файлы>");
            return;
        }

        String mode = args[0];
        String dataType = args[1];
        String outputFile = args[2];
        List<String> inputFiles = new ArrayList<>();

        for (int i = 3; i < args.length; i++) {
            inputFiles.add(args[i]);
        }

        List<Integer> allData = new ArrayList<>();
        for (String inputFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        if (dataType.equals("-i")) {
                            int num = Integer.parseInt(line.trim());
                            allData.add(num);
                        } else {
                            System.out.println("Неподдерживаемый тип данных: " + dataType);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка при чтении числа: " + line);
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + inputFile);
                return;
            }
        }

        if (mode.equals("-a")) {
            mergeSortAscending(allData);
        } else if (mode.equals("-d")) {
            mergeSortDescending(allData);
        } else {
            System.out.println("Неподдерживаемый режим сортировки: " + mode);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int num : allData) {
                writer.write(String.valueOf(num));
                writer.newLine();
            }
            System.out.println("Сортировка завершена. Результат записан в файл: " + outputFile);
        } catch (IOException e) {
            System.out.println("Ошибка при записи результата в файл: " + outputFile);
        }
    }

    private static void mergeSortAscending(List<Integer> data) {
        if (data.size() <= 1) {
            return;
        }

        int middle = data.size() / 2;

        List<Integer> left = new ArrayList<>(data.subList(0, middle));
        List<Integer> right = new ArrayList<>(data.subList(middle, data.size()));

        mergeSortAscending(left);
        mergeSortAscending(right);

        mergeAscending(data, left, right);
    }

    private static void mergeAscending(List<Integer> data, List<Integer> left, List<Integer> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                data.set(k, left.get(i));
                i++;
            } else {
                data.set(k, right.get(j));
                j++;
            }
            k++;
        }

        while (i < left.size()) {
            data.set(k, left.get(i));
            i++;
            k++;
        }

        while (j < right.size()) {
            data.set(k, right.get(j));
            j++;
            k++;
        }
    }

    private static void mergeSortDescending(List<Integer> data) {
        if (data.size() <= 1) {
            return;
        }

        int middle = data.size() / 2;

        List<Integer> left = new ArrayList<>(data.subList(0, middle));
        List<Integer> right = new ArrayList<>(data.subList(middle, data.size()));

        mergeSortDescending(left);
        mergeSortDescending(right);

        mergeDescending(data, left, right);
    }

    private static void mergeDescending(List<Integer> data, List<Integer> left, List<Integer> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) >= right.get(j)) {
                data.set(k, left.get(i));
                i++;
            } else {
                data.set(k, right.get(j));
                j++;
            }
            k++;
        }

        while (i < left.size()) {
            data.set(k, left.get(i));
            i++;
            k++;
        }

        while (j < right.size()) {
            data.set(k, right.get(j));
            j++;
            k++;
        }
    }
}
