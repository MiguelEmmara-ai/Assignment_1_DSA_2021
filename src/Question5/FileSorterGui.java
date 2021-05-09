package Question5;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * <h1>FileSorterGui.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class FileSorterGui extends JPanel implements ActionListener {
    private static final ImageIcon logo = new ImageIcon(Objects.requireNonNull(FileSorterGui
            .class
            .getClassLoader()
            .getResource("Document.png")));

    private final Queue<FileSorter> task_queue = new LinkedList<>();

    private final JLabel input_file;
    private final JLabel output_file;
    private final JLabel number_of_files;
    private final JLabel limit_strings_label;
    private final JTextField limit_strings_field;

    private final JLabel quick_sort;
    private final JLabel merge_sort;
    private final JProgressBar quick_progress_bar;
    private final JProgressBar merge_progress_bar;

    private final JButton queue_file;
    private final JButton run_task;

    private final Timer timer;
    private FileSorter current_queue_task;

    public FileSorterGui(String title) {
        FlatDarculaLaf.install();

        timer = new Timer(20, this);
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setBackground(Color.DARK_GRAY);

        // Label and Fields
        input_file = new JLabel("Input File: N/A");
        input_file.setLocation(10, 20);
        input_file.setSize(100, 50);
        this.add(input_file);

        output_file = new JLabel("Output File: N/A");
        output_file.setLocation(10, 60);
        output_file.setSize(100, 50);
        this.add(output_file);

        number_of_files = new JLabel("Number Of Items in Que: " + task_queue.size());
        number_of_files.setLocation(10, 100);
        number_of_files.setSize(200, 50);
        this.add(number_of_files);

        limit_strings_label = new JLabel("Max Strings Limit");
        limit_strings_label.setLocation(10, 140);
        limit_strings_label.setSize(150, 50);
        this.add(limit_strings_label);

        limit_strings_field = new JTextField();
        limit_strings_field.setLocation(10, 180);
        limit_strings_field.setSize(150, 30);
        this.add(limit_strings_field);

        // Progress Bar
        quick_sort = new JLabel("Quick Sort Progress");
        quick_sort.setLocation(10, 220);
        quick_sort.setSize(150, 50);
        this.add(quick_sort);

        quick_progress_bar = new JProgressBar(0, 100);
        quick_progress_bar.setValue(0);
        quick_progress_bar.setStringPainted(false);
        quick_progress_bar.setLocation(10, 260);
        quick_progress_bar.setSize(390, 20);
        this.add(quick_progress_bar);

        merge_sort = new JLabel("Merge Sort Progress");
        merge_sort.setLocation(10, 300);
        merge_sort.setSize(150, 50);
        this.add(merge_sort);

        merge_progress_bar = new JProgressBar(0, 100);
        merge_progress_bar.setValue(0);
        merge_progress_bar.setStringPainted(false);
        merge_progress_bar.setLocation(10, 340);
        merge_progress_bar.setSize(390, 20);
        this.add(merge_progress_bar);

        // Buttons
        queue_file = new JButton("Queue File");
        queue_file.setLocation(10, 400);
        queue_file.setSize(150, 30);
        queue_file.setFocusable(false);
        queue_file.addActionListener(this);
        this.add(queue_file);

        run_task = new JButton("Run");
        run_task.setLocation(250, 400);
        run_task.setSize(150, 30);
        run_task.setFocusable(false);
        run_task.setEnabled(false);
        run_task.addActionListener(this);
        this.add(run_task);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        FileSorterGui fileSorterPanel = new FileSorterGui("Miguel Emmara - 18022146");

        // Get the size of the screen
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        frame.setSize(500, 500);

        // Calculate the frame location
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;

        frame.getContentPane().add(fileSorterPanel);
        frame.setLocation(x, y);
        frame.setIconImage(logo.getImage());
        frame.setTitle("File Sorter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object com = evt.getSource();
        File toSort = null;
        File sorted_file;

        if (com == queue_file) {
            if (limit_strings_field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Max String Limit Cannot Be Empty",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (!limit_strings_field.getText().isEmpty()) {
                try {
                    if (Integer.parseInt(limit_strings_field.getText()) <= 0) {
                        JOptionPane.showMessageDialog(this, "Enter a "
                                        + "number above zero!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Desktop Directory //
                        /*String userDir = System.getProperty("user.home");
                        JFileChooser file_input = new JFileChooser(userDir + "/Desktop");
                        JFileChooser file_output = new JFileChooser(userDir + "/Desktop");*/
                        // Desktop Directory //

                        // Current Directory //
                        JFileChooser file_input = new JFileChooser();
                        JFileChooser file_output = new JFileChooser();
                        file_input.setCurrentDirectory(new File("./src/Question5/Input Test"));
                        file_output.setCurrentDirectory(new File("./src/Question5/Output Test"));
                        // Current Directory //

                        file_input.setDialogTitle("Input File");
                        file_input.setFileSelectionMode(JFileChooser.FILES_ONLY);

                        file_output.setDialogTitle("Output File");
                        file_output.setFileSelectionMode(JFileChooser.FILES_ONLY);

                        int add_file = file_input.showOpenDialog(null);

                        if (add_file == JFileChooser.APPROVE_OPTION)
                            toSort = file_input.getSelectedFile();
                        else
                            JOptionPane.showMessageDialog(null, "The User Cancelled The Operation",
                                    "Notice", JOptionPane.INFORMATION_MESSAGE);

                        if (toSort != null) {
                            file_output.showSaveDialog(null);
                            sorted_file = file_output.getSelectedFile();

                            if (sorted_file != null) {
                                run_task.setEnabled(true);
                                task_queue.offer(new FileSorter(Integer.parseInt(limit_strings_field.getText()), toSort, sorted_file));
                                run_task.setEnabled(true);

                                assert task_queue.peek() != null;
                                input_file.setText(task_queue.peek().getInput_file().getName());
                                assert task_queue.peek() != null;
                                output_file.setText(task_queue.peek().getOutput_file().getName());

                                number_of_files.setText("Number of items in queue: " + task_queue.size());
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please Input Valid Number",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Input Valid Number",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (com == run_task) {
            run_task.setEnabled(false);
            current_queue_task = task_queue.peek();
            run_task.setEnabled(false);

            Thread thread = new Thread(current_queue_task);
            timer.start();
            thread.start();
        }

        if (com == timer) {
            quick_progress_bar.setValue(current_queue_task.getSplitProgress());
            merge_progress_bar.setValue(current_queue_task.getMergeProgress());

            if (current_queue_task.isMergeDone() && current_queue_task.isSplitDone()) {
                assert timer != null;
                timer.stop();
                task_queue.poll();

                JOptionPane.showMessageDialog(this,
                        "(" + current_queue_task.getInput_file().getName() + ") has been sorted!",
                        "File Sorter", JOptionPane.INFORMATION_MESSAGE);

                if (task_queue.size() > 0) {
                    current_queue_task = task_queue.peek();
                    input_file.setText(task_queue.peek().getInput_file().getName());
                    assert task_queue.peek() != null;
                    output_file.setText(task_queue.peek().getOutput_file().getName());
                    run_task.setEnabled(true);
                } else {
                    current_queue_task = null;
                    input_file.setText("Input File: N/A");
                    output_file.setText("Output File: N/A");
                    limit_strings_field.setText("");
                    run_task.setEnabled(false);
                }

                quick_progress_bar.setValue(0);
                merge_progress_bar.setValue(0);
                number_of_files.setText("Number of items in queue: " + task_queue.size());
            }
        }
    }
}
