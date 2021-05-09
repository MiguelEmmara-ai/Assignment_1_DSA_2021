package Question5;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h1>FileSorter.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class FileSorter implements Runnable {
    private final int string_memory_limit;

    private final File input_file;
    private final File output_file;

    private final Queue<File> files = new LinkedList<>();

    private int splits;
    private int merges;

    private int maxSplits;
    private int maxMerges;

    public FileSorter(int string_memory_limit, File input_file, File output_file) {
        this.string_memory_limit = string_memory_limit;
        this.input_file = input_file;
        this.output_file = output_file;
        setSplits(0);
        setMerges(0);
        setMaxSplits();
        setMaxMerges(maxSplits);
    }

    @Override
    public void run() {
        firstStage();
        mergeStage();
    }

    // ----- Getters and Setters ----- //
    public int getString_memory_limit() {
        return string_memory_limit;
    }

    public File getInput_file() {
        return input_file;
    }

    public File getOutput_file() {
        return output_file;
    }

    public Queue<File> getFiles() {
        return files;
    }

    public int getSplits() {
        return splits;
    }

    public void setSplits(int splits) {
        this.splits = splits;
    }

    public int getMerges() {
        return merges;
    }

    public void setMerges(int merges) {
        this.merges = merges;
    }

    public int getMaxSplits() {
        return maxSplits;
    }

    public void setMaxSplits() {
        try {
            int number_of_lines = 0;
            BufferedReader inputStream = new BufferedReader(new FileReader(getInput_file()));

            while (inputStream.readLine() != null)
                number_of_lines++;
            System.out.println("Lines to read: " + number_of_lines);
            System.out.println("Max String In Memory: " + getString_memory_limit());
            System.out.println();

            if (number_of_lines % getString_memory_limit() > 0)
                this.maxSplits = (number_of_lines / getString_memory_limit()) + 1;
            else
                this.maxSplits = number_of_lines / getString_memory_limit();

            inputStream.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Not Found!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Reading Text From The File!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getMaxMerges() {
        return maxMerges;
    }

    public void setMaxMerges(int maxMerges) {
        this.maxMerges = maxMerges;
    }
    // ----- Getters and Setters ----- //

    public boolean isMergeDone() {
        return getMergeProgress() == 100;
    }

    public boolean isSplitDone() {
        return getSplitProgress() == 100;
    }

    public int getSplitProgress() {
        //multiply by 100
        return (splits / getMaxSplits()) * 100;
    }

    public int getMergeProgress() {
        //multiply by 100
        return (merges / getMaxMerges()) * 100;
    }

    public void firstStage() {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(getInput_file()));
            String line;
            String[] itemsToSort;

            ArrayList<String> items = new ArrayList<>();
            int linesRead = 0;

            while ((line = inputStream.readLine()) != null) {
                items.add(line);
                linesRead++;

                if (linesRead == getString_memory_limit()) {
                    linesRead = 0;
                    this.splits++;
                    itemsToSort = items.toArray(new String[0]);
                    writeToFile(itemsToSort);
                    items = new ArrayList<>();
                }
            }
            inputStream.close();

            itemsToSort = items.toArray(new String[0]);
            if (itemsToSort.length != 0) {
                this.splits++;
                writeToFile(itemsToSort);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Not Found!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Reading Text From The File!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void writeToFile(String[] items) {
        try {
            QuickSortGeneric<String> sorter = new QuickSortGeneric<>();
            sorter.quicksort(items, 0, items.length - 1);

            String line;

            File temp = new File(getSplits() + " split.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp, false));

            for (String item : items) {
                line = item;
                if (item.compareTo(items[items.length - 1]) != 0)
                    bw.append(line).append("\n");
                else
                    bw.append(line);
            }

            getFiles().offer(temp);
            bw.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Not Found!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Reading Text From The File!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mergeStage() {
        String line;

        try {
            setMerges(1);

            while (getFiles().size() > 1) {
                // Retrieves and removes the first file of the lists.
                File file_1 = getFiles().poll();
                File file_2 = getFiles().poll();

                // Create two string arrays, one for each file:
                ArrayList<String> one = new ArrayList<>();
                ArrayList<String> two = new ArrayList<>();

                assert file_1 != null;
                BufferedReader br1 = new BufferedReader(new FileReader(file_1));

                assert file_2 != null;
                BufferedReader br2 = new BufferedReader(new FileReader(file_2));

                while ((line = br1.readLine()) != null)
                    one.add(line);

                while ((line = br2.readLine()) != null)
                    two.add(line);

                // Close the streams and delete the two files:
                br1.close();
                br2.close();

                try {
                    file_1.delete();
                    file_2.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Create temp file
                File temp = new File(getMerges() + " merge.txt");
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp, false));

                // Counter for number of items written
                int firstItemsWritten = 0;
                int secondItemsWritten = 0;

                // write in items to temp file
                for (int i = firstItemsWritten; i < one.size(); i++) {
                    for (int j = secondItemsWritten; j < two.size(); j++) {
                        if (one.get(firstItemsWritten).compareTo(two.get(secondItemsWritten)) <= 0) {
                            line = one.get(firstItemsWritten++);
                            j = two.size(); //exit inner loop
                        } else {
                            line = two.get(secondItemsWritten++);
                        }

                        line += "\n";
                        bw.append(line);
                    }
                }

                // Conditions if one text file has all it's items written to
                // The new file, but other text file doesn't:
                if (firstItemsWritten < one.size()) {
                    for (int i = firstItemsWritten; i < one.size(); i++) {
                        line = one.get(firstItemsWritten++);
                        if (i < one.size() - 1)
                            line += "\n";
                        bw.append(line);
                    }
                } else if (secondItemsWritten < two.size()) {
                    for (int i = secondItemsWritten; i < two.size(); i++) {
                        line = two.get(secondItemsWritten++);
                        if (i < two.size() - 1)
                            line += "\n";
                        bw.append(line);
                    }
                }

                getFiles().offer(temp);
                bw.close();

                this.merges++;
            }

            File last = getFiles().poll();

            // Rename last remaining, sorted file
            try {
                assert last != null;
                last.renameTo(getOutput_file());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                last.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Not Found!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Reading Text From The File!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
