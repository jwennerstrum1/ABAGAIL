%% plot Traveling Salesman
clear
close all

Path = 'src/opt/test/';
FileName = 'FlipFlop.csv';

Algorithms = {'RHC', 'SA', 'GA'};
legendVals = {};

num = xlsread([Path, FileName]);
iteration = num(:,1);
rhcFitness = num(:,2);
saFitness = num(:,3);
gaFitness = num(:,4);

rhcTime = num(:, 5);
saTime = num(:,  6);
gaTime = num(:, 7);

figure(1);
hold on
plot(iteration, rhcFitness, 'LineWidth', 2)
plot(iteration, saFitness, 'LineWidth', 2);
plot(iteration, gaFitness, 'LineWidth', 2);

% saTime = saTime(1:min(find(isnan(saTime)))-1);
% gaTime = gaTime(1:length(saTime));
% rhcTime = rhcTime(1:length(saTime));


figure(2);
hold on
plot(iteration, rhcTime, 'LineWidth', 2)
plot(iteration, saTime, 'LineWidth',2);
plot(iteration, gaFitness, 'LineWidth', 2);


figure(1)
xlabel('Iterations', 'Interpreter', 'latex', 'FontSize', 14);
ylabel('Fintess', 'Interpreter', 'latex', 'FontSize', 14);
legend(Algorithms, 'Interpreter', 'latex', 'FontSize', 14);
title('Fitness value vs. Iterations (1,000)', 'Interpreter', 'latex', 'FontSize', 14);

figure(2)
xlabel('Iterations',  'Interpreter', 'latex', 'FontSize', 14);
ylabel('Error',  'Interpreter', 'latex', 'FontSize', 14);
legend(Algorithms, 'FontSize', 14, 'Interpreter', 'latex');
title('Fitness value vs. Iterations (1,000)', 'Interpreter', 'latex', 'FontSize', 14);



