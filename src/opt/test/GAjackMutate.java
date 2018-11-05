package opt.test;

import func.nn.feedfwd.FeedForwardNetwork;
import func.nn.feedfwd.FeedForwardNeuralNetworkFactory;
import opt.OptimizationAlgorithm;
import opt.example.NeuralNetworkOptimizationProblem;
import opt.ga.StandardGeneticAlgorithm;
import shared.DataSet;
import shared.ErrorMeasure;
import shared.Instance;
import shared.SumOfSquaresError;
import shared.filt.RandomOrderFilter;
import shared.filt.TestTrainSplitFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implementation of randomized hill climbing, simulated annealing, and genetic algorithm to
 * find optimal weights to a neural network that is classifying abalone as having either fewer
 * or more than 15 rings.
 *
 * @author Hannah Lau
 * @version 1.0
 */
public class GAjackMutate {
    private static Instance[] instances = initializeInstances();

    private static Integer[] mutate = new Integer[]{0, 25, 50, 75, 100};

    private static int inputLayer = 37, outputLayer = 1, trainingIterations = 200;
    private static FeedForwardNeuralNetworkFactory factory = new FeedForwardNeuralNetworkFactory();

    private static ErrorMeasure measure = new SumOfSquaresError();

    private static DataSet set = new DataSet(instances);

    //private static FeedForwardNetwork networks[] = new FeedForwardNetwork[100];
    private static FeedForwardNetwork networks[] = new FeedForwardNetwork[mutate.length];
    //private static NeuralNetworkOptimizationProblem[] nnop = new NeuralNetworkOptimizationProblem[100];
    private static NeuralNetworkOptimizationProblem[] nnop = new NeuralNetworkOptimizationProblem[mutate.length];

    //private static OptimizationAlgorithm[] oa = new OptimizationAlgorithm[100];
    private static OptimizationAlgorithm[] oa = new OptimizationAlgorithm[mutate.length];
    //private static String[] oaNames = new String[100];
    //private static String[] oaNames = new String[3];
    private static String[] oaNames = {"0", "25", "50", "75", "100"};
    private static String results = "";
    private static List<List<Double>> oaResultsTrain = new ArrayList<>();
    private static List<List<Double>> oaResultsTest = new ArrayList<>();
    // private static List<Double> oaResultsTrain = new ArrayList<>();
    // private static List<Double> oaResultsTest = new ArrayList<>();

    private static DecimalFormat df = new DecimalFormat("0.000");

    public static void main(String[] args) {
        /*for(int i = 0; i < oa.length; i++) {
            networks[i] = factory.createClassificationNetwork(
                new int[] {inputLayer, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, outputLayer});
            nnop[i] = new NeuralNetworkOptimizationProblem(set, networks[i], measure);
        }*/

        /*int m = 0;
        for (double i = 0.01; i <= 1; i += 0.01, m++) {
            oa[m] = new SimulatedAnnealing(1E11, i, nnop[m]);
            oaNames[m] = String.valueOf(i);
        }*/

        for (int i = 0; i < trainingIterations; i++) {
        // for (int i = 0; i < mutate.length; i++) {
            oaResultsTrain.add(new ArrayList<Double>());
            oaResultsTest.add(new ArrayList<Double>());
        }

        //oa[0] = new RandomizedHillClimbing(nnop[0]);
        //oa[1] = new SimulatedAnnealing(1E11, .95, nnop[1]);
        //oa[2] = new StandardGeneticAlgorithm(50, 100, 10, nnop[2]);

        for (int k = 0; k < mutate.length; k++) {
            new RandomOrderFilter().filter(set);
            TestTrainSplitFilter ttsf = new TestTrainSplitFilter(70);
            ttsf.filter(set);
            DataSet train = ttsf.getTrainingSet();
            DataSet test = ttsf.getTestingSet();

            for(int i = 0; i < oa.length; i++) {
                networks[i] = factory.createClassificationNetwork(
                        new int[] {inputLayer, 37, 37, 37, outputLayer});
                nnop[i] = new NeuralNetworkOptimizationProblem(train, networks[i], measure);
            }

            //oa[0] = new RandomizedHillClimbing(nnop[0]);
            //oa[1] = new SimulatedAnnealing(1E11, .35, nnop[1]);
            oa[k] = new StandardGeneticAlgorithm(100, 50, mutate[k], nnop[k]);

            // for (int i = 2; i < 3; i++) {
                double start = System.nanoTime(), end, trainingTime, testingTime, correct = 0, incorrect = 0;
                train(oa[k], networks[k], oaNames[k], train, test); //trainer.train();
                end = System.nanoTime();
                trainingTime = end - start;
                trainingTime /= Math.pow(10, 9);

                Instance optimalInstance = oa[k].getOptimal();
                networks[k].setWeights(optimalInstance.getData());

                double predicted, actual;
                start = System.nanoTime();
                for (int j = 0; j < instances.length; j++) {
                    networks[k].setInputValues(instances[j].getData());
                    networks[k].run();

                    predicted = Double.parseDouble(instances[j].getLabel().toString());
                    actual = Double.parseDouble(networks[k].getOutputValues().toString());

                    double trash = Math.abs(predicted - actual) < 0.5 ? correct++ : incorrect++;
                }
                end = System.nanoTime();
                testingTime = end - start;
                testingTime /= Math.pow(10, 9);

                results += "\nResults for " + oaNames[k] + ": \nCorrectly classified " + correct + " instances." +
                        "\nIncorrectly classified " + incorrect + " instances.\nPercent correctly classified: "
                        + df.format(correct / (correct + incorrect) * 100) + "%\nTraining time: " + df.format(trainingTime)
                        + " seconds\nTesting time: " + df.format(testingTime) + " seconds\n";
            // }

            //printing to the different csv files:
            try{
                    Integer mutateValue = mutate[k];
                    FileWriter writer = new FileWriter(new File("GAjack_Results_Mutate" + Integer.toString(mutateValue) + ".csv"));
                    String header = "Iteration, Train Error, Test Error\n";
                    writer.append(header);
                    for (int ii=0; ii<oaResultsTrain.size(); ii++){
                            double first = oaResultsTrain.get(ii).get(k);
                            double second = oaResultsTest.get(ii).get(k);
                            // double trainSum = 0;
                            // double testSum = 0;
                            //
                            // for (int j = 0; j < oaResultsTrain.get(ii).size(); j++){
                            //         trainSum += oaResultsTrain.get(ii).get(j);
                            // }
                            //
                            // for (int j = 0; j< oaResultsTest.get(ii).size(); j++){
                            //         testSum += oaResultsTest.get(ii).get(j);
                            // }
                            //
                            // double first = trainSum / (double) oaResultsTrain.get(ii).size();
                            // double second = testSum / (double) oaResultsTest.get(ii).size();

                            String data = ii + ", " + first + ", " + second + "\n";
                            writer.append(data);
                    }
                    writer.close();
            } catch (Exception e){
                    System.out.println("error writing to file");
            }

        }

        System.out.println("\nLinear separator\n");

        for (int i = 0; i < oaResultsTrain.size(); i++) {
            double trainSum = 0;
            double testSum = 0;

            for (int j = 0; j < oaResultsTrain.get(i).size(); j++) {
                trainSum += oaResultsTrain.get(i).get(j);
            }

            for (int j = 0; j < oaResultsTest.get(i).size(); j++) {
                testSum += oaResultsTest.get(i).get(j);
            }

            double first = trainSum / (double) oaResultsTrain.get(i).size();
            double second = testSum / (double) oaResultsTest.get(i).size();
            System.out.println(df.format(first) + " " + df.format(second));
        }

        //System.out.println(results);
    }

    private static void train(OptimizationAlgorithm oa, FeedForwardNetwork network, String oaName, DataSet train, DataSet test) {
        System.out.println("\nError results for " + oaName + "\n---------------------------");
        Instance[] trainInstances = train.getInstances();
        Instance[] testInstances = test.getInstances();

        //double lastError = 0;
        for(int i = 0; i < trainingIterations; i++) {
            oa.train();

            double trainError = 0;
            double testError = 0;
            for(int j = 0; j < trainInstances.length; j++) {
                network.setInputValues(trainInstances[j].getData());
                network.run();

                Instance output = trainInstances[j].getLabel(), example = new Instance(network.getOutputValues());
                example.setLabel(new Instance(Double.parseDouble(network.getOutputValues().toString())));
                trainError += measure.value(output, example);
                //lastError = error;
            }
            trainError /= trainInstances.length;

            for (int j = 0; j < testInstances.length; j++) {
                network.setInputValues(testInstances[j].getData());
                network.run();

                Instance output = testInstances[j].getLabel(), example = new Instance(network.getOutputValues());
                example.setLabel(new Instance(Double.parseDouble(network.getOutputValues().toString())));
                testError += measure.value(output, example);
                //lastError = error;
            }
            testError /= testInstances.length;

            System.out.println("Iteration " + String.format("%04d" ,i) + ": " + df.format(trainError) + " " + df.format(testError));
            oaResultsTrain.get(i).add(trainError);
            oaResultsTest.get(i).add(testError);
        }

        //System.out.println(df.format(Double.parseDouble(oaName)) + " " + lastError);
    }

    private static Instance[] initializeInstances() {

        double[][][] attributes = new double[7729][][];

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/opt/test/googlePlayStoreRefined_Normalized.csv")));

            for(int i = 0; i < attributes.length; i++) {
                Scanner scan = new Scanner(br.readLine());
                scan.useDelimiter(",");

                attributes[i] = new double[2][];
                attributes[i][0] = new double[37]; // 4 attributes
                attributes[i][1] = new double[1];

                for(int j = 0; j < 37; j++)
                    attributes[i][0][j] = Double.parseDouble(scan.next());

                attributes[i][1][0] = Double.parseDouble(scan.next());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Instance[] instances = new Instance[attributes.length];

        for(int i = 0; i < instances.length; i++) {
            instances[i] = new Instance(attributes[i][0]);
            instances[i].setLabel(new Instance(attributes[i][1][0]));
        }

        return instances;
    }
}
