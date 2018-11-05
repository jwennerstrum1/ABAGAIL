%% plot Simulated Annealing Temperature
clear
close all
temps = [1, 1000, 1000000, 10000000, 100000000, 1000000000];

baseFileName = 'SAjack_ResultsTemp';

% files = {};
legendVals = {};
for i = 1:length(temps)
    file = [baseFileName, num2str(temps(i)), 'e3.csv'];
    str = sprintf('Temp = %dE3', temps(i));
    legendVals{i} = str;
    num = xlsread(file);
    iterations = num(2:end,1);
    trainingError = num(2:end,2);
    testError = num(2:end, 3);
    figure(1);
    hold on
    plot(iterations,trainingError);
    figure(2);
    hold on
    plot(iterations, testError);
end
figure(1)
xlabel('Iterations');
ylabel('Error');
legend(legendVals);

figure(2)
xlabel('Iterations');
ylabel('Error');
legend(legendVals);

