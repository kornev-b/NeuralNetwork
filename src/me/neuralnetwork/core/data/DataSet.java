package me.neuralnetwork.core.data;

import me.neuralnetwork.core.exception.NeuroException;
import me.neuralnetwork.core.exception.VectorSizeMismatchException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a collection of data rows (DataSetRow instances) used
 * for training and testing neural network.
 *
 */
public class DataSet {
    private List<DataSetRow> rows;
    
    /**
     * Size of the input vector in data set rows
     */
    private int inputSize = 0;
    
    /**
     * Size of output vector in data set rows
     */
    private int outputSize = 0;
    
    /**
     * Column names/labels
     */
    private String[] columnNames;
    
    /**
     * Flag which indicates if this data set containes data rows for supervised training
     */
    private boolean isSupervised = false;
    
    /**
     * Label for this training set
     */
    private String label;
    
    /**
     * Full file path including file name
     */
    private transient String filePath;

    public DataSet(int inputSize) {
        this.rows = new ArrayList<>();
        this.inputSize = inputSize;
        this.isSupervised = false;
        this.columnNames = new String[inputSize];
    }

    public DataSet(int inputSize, int outputSize) {
        this.rows = new ArrayList<>();
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.isSupervised = true;
        this.columnNames = new String[inputSize+outputSize];
    }

    public void addRow(DataSetRow row)
            throws VectorSizeMismatchException {
        
        if (row == null) {
                    throw new IllegalArgumentException("Training data row cannot be null!");
        }
        
        // check input vector size if it is predefined
        if ((this.inputSize != 0)
                && (row.getInput().length != this.inputSize)) {
            throw new VectorSizeMismatchException("Input vector size does not match data set input size!");
        }


        if ((this.outputSize != 0)
                && (row.getDesiredOutput().length != this.outputSize)) {
            throw new VectorSizeMismatchException("Output vector size does not match data set output size!");
        }

        // if everything went ok add training row
        this.rows.add(row);
    }

    public void addRow(double[] input) {
        if (input == null)
            throw new IllegalArgumentException("Input for dataset row cannot be null!");

        if (input.length != inputSize)
            throw new NeuroException("Input size for given row is different from the data set size!");
        
        if (isSupervised) 
            throw new NeuroException("Cannot add unsupervised row to supervised data set!");
            
        this.addRow(new DataSetRow(input));
    }

    public void addRow(double[] input, double[] output) {
        this.addRow(new DataSetRow(input, output));
    }

    public void removeRowAt(int idx) {
        this.rows.remove(idx);
    }

    public Iterator<DataSetRow> iterator() {
        return this.rows.iterator();
    }

    public List<DataSetRow> getRows() {
        return this.rows;
    }

    public DataSetRow getRowAt(int idx) {
        return this.rows.get(idx);
    }

    public void clear() {
        this.rows.clear();
    }

    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    public boolean isSupervised() {
        return this.isSupervised;
    }

    public int size() {
        return this.rows.size();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
    
    public String getColumnName(int idx) {
        return columnNames[idx];
    }
    
    public void setColumnName(int idx, String columnName) {
        columnNames[idx] = columnName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {  
        StringBuilder sb = new StringBuilder();
        sb.append("Dataset Label: ").append(label).append(System.lineSeparator());
               
        if (columnNames != null) {
            sb.append("Columns: ");
            for(String columnName : columnNames) {
                sb.append(columnName).append(", ");
            }
            sb.delete(sb.length()-2, sb.length()-1);
            sb.append(System.lineSeparator());
        }    
        
        for(DataSetRow row : rows) {
            sb.append(row).append(System.lineSeparator());
        }
        
        return sb.toString();
    }

    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        
        if ((columnNames != null)&& (columnNames.length > 0) ){
            for(String columnName : columnNames) {
                sb.append(columnName).append(", ");
            }
            sb.delete(sb.length()-2, sb.length()-1);
            sb.append(System.lineSeparator());
        }
        
        // promeniti
        for(DataSetRow row : rows) {
            sb.append(row.toCSV()); // nije dobro jer lepi input i desired output; treba bez toga mozda dodati u toCSV
            sb.append(System.lineSeparator());
        }
        
        return sb.toString();        
    }

    public void save(String filePath) {
        this.filePath = filePath;
        this.save();
    }

    /**
     * Saves this training set to file specified in its filePath field
     */
    public void save() {
        ObjectOutputStream out = null;

        try {
            File file = new File(this.filePath);
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.flush();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void saveAsTxt(String filePath, String delimiter) {
        
        if (filePath == null) throw new IllegalArgumentException("File path is null!");
        
        // default delimiter is space if other is not specified
        if ((delimiter == null) || delimiter.equals("")) {
            delimiter = " ";
        }
        

        try (PrintWriter out = new PrintWriter(new FileWriter(new File(filePath)))) {

        int columnCount = inputSize + outputSize;    
        if ((columnNames != null) && (columnNames.length > 0)){
            for(int i = 0; i< columnNames.length; i++) {
                out.print(columnNames[i]);
                if (i < columnCount-1) out.print(delimiter);
            }
            out.println();
        }            
            
            for (DataSetRow row : this.rows) {
                double[] input = row.getInput();
                for (int i = 0; i < input.length; i++) {
                    out.print(input[i]);
                    if (i < columnCount-1) out.print(delimiter);
                }

                if (row.isSupervised()) {
                    double[] output = row.getDesiredOutput();
                    for (int j = 0; j < output.length; j++) {
                        out.print(output[j]);
                        if (inputSize + j < columnCount-1) out.print(delimiter);
                    }
                }
                out.println();
            }

            out.flush();

        } catch (IOException ex) {
           throw new NeuroException("Error saving data set file!", ex);
        } 
    }

    public static DataSet load(String filePath) {
        ObjectInputStream oistream = null;

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("Cannot find file: " + filePath);
                
            }

            oistream = new ObjectInputStream(new FileInputStream(filePath));
            DataSet dataSet = (DataSet) oistream.readObject();
            dataSet.setFilePath(filePath);
            
            return dataSet;

        } catch (IOException ioe) {
            throw new NeuroException("Error reading file!", ioe);
        } catch (ClassNotFoundException ex) {
            throw new NeuroException("Class not found while trying to read DataSet object from the stream!", ex);
        } finally {
            if (oistream != null) {
                try {
                    oistream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates and returns data set from specified csv file
     * 
     * @param filePath path to csv dataset file to import
     * @param inputsCount number of inputs
     * @param outputsCount number of outputs
     * @param delimiter delimiter of values
     * @param loadColumnNames true if csv file contains column names in first line, false otherwise
     * @return instance of dataset with values from specified file
     */
    public static DataSet createFromFile(String filePath, int inputsCount, int outputsCount, String delimiter, boolean loadColumnNames) {
        FileReader fileReader = null;

        if (filePath == null) throw new IllegalArgumentException("File name cannot be null!");
        if (inputsCount <= 0) throw new IllegalArgumentException("Number of inputs cannot be <= 0");
        if (outputsCount < 0) throw new IllegalArgumentException("Number of outputs cannot be < 0");
        if ((delimiter == null) || delimiter.isEmpty()) throw new IllegalArgumentException("Delimiter cannot be null or empty!");
             
        try {
            DataSet dataSet = new DataSet(inputsCount, outputsCount);
            dataSet.setFilePath(filePath);
            fileReader = new FileReader(new File(filePath));
            BufferedReader reader = new BufferedReader(fileReader);

            String line=null;
            
            if (loadColumnNames) {
                // get column names from the first line
                line = reader.readLine();
                String[] colNames = line.split(delimiter); 
                dataSet.setColumnNames(colNames);          
            }
            
            while ((line = reader.readLine()) != null) {                                
                String[] values = line.split(delimiter);                
                
                double[] inputs = new double[inputsCount];
                double[] outputs = new double[outputsCount];
                
                if (values[0].equals("")) {
                    continue; // skip if line was empty
                }
                for (int i = 0; i < inputsCount; i++) {
                    inputs[i] = Double.parseDouble(values[i]);
                }

                for (int i = 0; i < outputsCount; i++) {
                    outputs[i] = Double.parseDouble(values[inputsCount + i]);
                }

                if (outputsCount > 0) {
                    dataSet.addRow(new DataSetRow(inputs, outputs));
                } else {
                    dataSet.addRow(new DataSetRow(inputs));
                }
            }

            return dataSet;

        } catch (FileNotFoundException ex) {
           throw new NeuroException("Could not find data set file!", ex);
        } catch (IOException ex) {
            try {
                fileReader.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            throw new NeuroException("Error reading data set file!", ex);
        } catch (NumberFormatException ex) {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex1) {
                    ex1.printStackTrace();
                }
            }
            ex.printStackTrace();
            throw new NeuroException("Bad number format in data set file!", ex);
        }

    }

    public DataSet[] createTrainingAndTestSubsets(int trainSetPercent, int testSetPercent) {
        DataSet[] trainAndTestSet = new DataSet[2];

        ArrayList<Integer> randoms = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            randoms.add(i);
        }

        Collections.shuffle(randoms);

        // create training set
        trainAndTestSet[0] = new DataSet(inputSize, outputSize);
        int trainingElementsCount = this.size() * trainSetPercent / 100;
        for (int i = 0; i < trainingElementsCount; i++) {
            int idx = randoms.get(i);
            trainAndTestSet[0].addRow(this.rows.get(idx));
        }


        // create test.txt set
        trainAndTestSet[1] = new DataSet(inputSize, outputSize);
        int testElementsCount = this.size() - trainingElementsCount;
        for (int i = 0; i < testElementsCount; i++) {
            int idx = randoms.get(trainingElementsCount + i);
            trainAndTestSet[1].addRow(this.rows.get(idx));
        }

        return trainAndTestSet;
    }

    /**
     * Returns output vector size of training elements in this training set.
     */
    public int getOutputSize() {
        return this.outputSize;
    }

    /**
     * Returns input vector size of training elements in this training set This
     * method is implementation of EngineIndexableSet interface, and it is added
     * to provide compatibility with Encog data sets and FlatNetwork
     */
    public int getInputSize() {
        return this.inputSize;
    }

    public void shuffle() {
        Collections.shuffle(rows);
    }
}