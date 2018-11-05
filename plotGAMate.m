%% plot Generic Algorithm Mate
clear
close all
mateVals = [1,  7, 10];

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

