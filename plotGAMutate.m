%% plot Generic Algorithm Mutate
clear
close all
mutateVals = [25,  50, 75, 100];

baseFileName = 'GAjack_Results_Mutate';

% files = {};
legendVals = {};
for i = 1:length(mutateVals)
    file = [baseFileName, num2str(mutateVals(i)), '.csv'];
    str = sprintf('Mutation value = %d', mutateVals(i));
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
title('Train Error vs. Iterations for Varying Mutate Values', 'Interpreter', 'latex', 'FontSize', 14);

figure(2)
xlabel('Iterations',  'Interpreter', 'latex', 'FontSize', 14);
ylabel('Error',  'Interpreter', 'latex', 'FontSize', 14);
legend(legendVals, 'FontSize', 14, 'Interpreter', 'latex');
title('Test Error vs. Iterations for Varying Mutate Values', 'Interpreter', 'latex', 'FontSize', 14);



