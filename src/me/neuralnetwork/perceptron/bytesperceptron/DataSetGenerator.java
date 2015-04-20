package me.neuralnetwork.perceptron.bytesperceptron;

import me.neuralnetwork.core.data.DataSetRow;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Bogdan Kornev
 * on 22.03.2015, 19:05.
 */
public class DataSetGenerator {
    private int count = 200;
    private static int[] probabilityMatrix = {1, 1, 1, 0, 1, 1, 1, 1, 0, 1};
    public static final String DELIMITER = ",";
    public static final String FILENAME = "data.txt";

    public void setCount(int count) {
        this.count = count;
    }

    public void generate() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int[] row = getTrainingSetRow();
            sb.setLength(0);
            for (int aRow : row) {
                sb.append(aRow).append(DELIMITER);
            }
            sb.deleteCharAt(sb.length() - 1);
            writer.print(sb.toString());
            writer.println();
        }
        writeBoundaryRows(writer, sb);
        writer.close();
    }

    private void writeBoundaryRows(PrintWriter writer, StringBuilder sb) {
        sb.setLength(0);
        sb      .append(100).append(DELIMITER).append(100).append(DELIMITER)
                .append(0).append(DELIMITER).append(0).append(DELIMITER)
                .append(0).append(DELIMITER).append(0);
        writer.print(sb.toString());
        sb.setLength(0);
        writer.println();
        sb      .append(0).append(DELIMITER).append(0).append(DELIMITER)
                .append(100).append(DELIMITER).append(100).append(DELIMITER)
                .append(0).append(DELIMITER).append(0);
        writer.print(sb.toString());
        sb.setLength(0);
        writer.println();
        sb      .append(0).append(DELIMITER).append(0).append(DELIMITER)
                .append(101).append(DELIMITER).append(101).append(DELIMITER)
                .append(1).append(DELIMITER).append(0);
        writer.print(sb.toString());
        sb.setLength(0);
        writer.println();
        sb.     append(101).append(DELIMITER).append(101).append(DELIMITER)
                .append(0).append(DELIMITER).append(0).append(DELIMITER)
                .append(0).append(DELIMITER).append(1);
        writer.print(sb.toString());
        sb.setLength(0);
        writer.println();
        sb      .append(100).append(DELIMITER).append(100).append(DELIMITER)
                .append(100).append(DELIMITER).append(100).append(DELIMITER)
                .append(0).append(DELIMITER).append(0);
        writer.print(sb.toString());
        sb.setLength(0);
        writer.println();
        sb      .append(0).append(DELIMITER).append(0).append(DELIMITER)
                .append(0).append(DELIMITER).append(0).append(DELIMITER)
                .append(0).append(DELIMITER).append(0);
        writer.print(sb.toString());
        sb.setLength(0);
        writer.println();
        sb      .append(255).append(DELIMITER).append(255).append(DELIMITER)
                .append(255).append(DELIMITER).append(255).append(DELIMITER)
                .append(0).append(DELIMITER).append(0);
        writer.print(sb.toString());
        sb.setLength(0);
    }

    private int[] getTrainingSetRow() {
        int[] row = new int[6];
        row[0] = getRandomByte();
        if (row[0] < 101) {
            int probability = probabilityMatrix[((int) (Math.random() * 10))];
            if (probability == 1) {
                row[1] = getRandomByte100();
            } else {
                row[1] = getRandomByte();
            }
        } else {
            int probability = probabilityMatrix[((int) (Math.random() * 10))];
            if (probability == 1) {
                row[1] = getRandomByte100x256();
            } else {
                row[1] = getRandomByte();
            }
        }

        if (row[0] < 101) {
            int probability = probabilityMatrix[((int) (Math.random() * 10))];
            if (probability == 1) {
                row[2] = getRandomByte100x256();
            } else {
                row[2] = getRandomByte();
            }
        } else {
            int probability = probabilityMatrix[((int) (Math.random() * 10))];
            if (probability == 1) {
                row[2] = getRandomByte100();
            } else {
                row[2] = getRandomByte();
            }
        }

        if (row[2] < 101) {
            int probability = probabilityMatrix[((int) (Math.random() * 10))];
            if (probability == 1) {
                row[3] = getRandomByte100();
            } else {
                row[3] = getRandomByte();
            }
        } else {
            int probability = probabilityMatrix[((int) (Math.random() * 10))];
            if (probability == 1) {
                row[3] = getRandomByte100x256();
            } else {
                row[3] = getRandomByte();
            }
        }
        return calcOutputs(row);
    }

    private int[] calcOutputs(int[] row) {
        if (row[0] < 101 && row[1] < 101 && row[2] > 100 && row[3] > 100) {
            row[4] = 1;
            row[5] = 0;
            return row;
        } else if (row[0] > 100 && row[1] > 100 && row[2] < 101 && row[3] < 101) {
            row[4] = 0;
            row[5] = 1;
            return row;
        }

        row[4] = 0;
        row[5] = 0;
        return row;
    }

    private int getRandomByte() {
        return (int) (Math.random() * 256);
    }

    private int getRandomByte100() {
        return (int) (Math.random() * 101);
    }

    private int getRandomByte100x256() {
        return (int) (100 + Math.random() * (256 - 100));
    }
}
