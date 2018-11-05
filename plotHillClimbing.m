%% plot Hill Climbing RandomRestart
clear
close all

baseFileName = 'RHCjackRR_Results';
% files = {};
restart = {};
for i = 1:15
    file = [baseFileName, num2str(i), '.csv'];
    restart{i} = ['Restart', num2str(i)];
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
legend(restart, 'Interpreter', 'latex', 'FontSize', 10);

figure(2)
xlabel('Iterations', 'Interpreter', 'latex', 'FontSize', 14);
ylabel('Error', 'Interpreter', 'latex', 'FontSize', 14);
legend(restart, 'Interpreter', 'latex', 'FontSize', 10);

% 
% num = xlsread(files{1});
% iterations = num(:,1);
% trainingError = num(:, 2);
% testError = num(:,3);
% p1 = plot(iterations, trainingError, 'b', iterations, testError, 'r');


num = xlsread('RHCjackRR_Results_Accuracies.csv');
RestartNum = num(:,1);
RestartTitle = {};
for i = 1 : length(RestartNum)
    RestartTitle{i} = ['Restart ', num2str(RestartNum(i))];
end
Restarts= categorical(RestartTitle);
Accuracy = num(:,2);
figure(3)
bar(Restarts, Accuracy);
title('Accuracy (%) vs Restart Iteration')



