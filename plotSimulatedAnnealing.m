%% plot Simulated Annealing Cooling Rate
clear
close all
coolingRates = [10, 50, 70, 80, 90, 99];

baseFileName = 'SAjack_Results';

% files = {};
legendVals = {};
for i = 1:length(coolingRates)
    file = [baseFileName, num2str(coolingRates(i)), '.csv'];
    str = sprintf('Cooling Rate = %d', coolingRates(i));
    legendVals{i} = str;
    num = xlsread(file);
    iterations = num(2:end,1);
    trainingError = num(2:end,2);
    testError = num(2:end, 3);
    figure(1);
    hold on
    plot(iterations,trainingError, 'LineWidth', 2);
    figure(2);
    hold on
    plot(iterations, testError, 'LineWidth', 2);
end
figure(1)
xlabel('Iterations', 'Interpreter', 'latex', 'FontSize', 14);
ylabel('Error', 'Interpreter', 'latex', 'FontSize', 14);
legend(legendVals, 'Interpreter', 'latex', 'FontSize', 14);

figure(2)
xlabel('Iterations', 'Interpreter', 'latex', 'FontSize', 14);
ylabel('Error', 'Interpreter', 'latex', 'FontSize', 14);
legend(legendVals, 'Interpreter', 'latex', 'FontSize', 14);

