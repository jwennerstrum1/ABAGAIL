%% plot Generic Algorithm Mate
clear
close all
mateVals = [25,  50, 75, 100];

baseFileName = 'GAjack_Results_Mate';

% files = {};
legendVals = {};
for i = 1:length(mateVals)
    file = [baseFileName, num2str(mateVals(i)), '.csv'];
    str = sprintf('Mate Number = %d', mateVals(i));
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
title('Train Error vs. Iterations for Varying Mate Values', 'Interpreter', 'latex', 'FontSize', 14);

figure(2)
xlabel('Iterations',  'Interpreter', 'latex', 'FontSize', 14);
ylabel('Error',  'Interpreter', 'latex', 'FontSize', 14);
legend(legendVals, 'FontSize', 14, 'Interpreter', 'latex');
title('Test Error vs. Iterations for Varying Mate Values', 'Interpreter', 'latex', 'FontSize', 14);



