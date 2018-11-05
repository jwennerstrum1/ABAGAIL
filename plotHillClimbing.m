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
    plot(iterations,trainingError);
    figure(2);
    hold on
    plot(iterations, testError);
end
figure(1)
xlabel('Iterations');
ylabel('Error');
legend(restart);

figure(2)
xlabel('Iterations');
ylabel('Error');
legend(restart);

% 
% num = xlsread(files{1});
% iterations = num(:,1);
% trainingError = num(:, 2);
% testError = num(:,3);
% p1 = plot(iterations, trainingError, 'b', iterations, testError, 'r');





