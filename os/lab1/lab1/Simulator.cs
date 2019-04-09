using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace lab1
{
    class Simulator
    {
        private const int RANDOM_SEED = 1_000_000;
        private const int PROCESS_COUNT = 50;

        private Random random;

        public int RoundRobinTime { get; private set; }
        public int MinExecTime { get; private set; }
        public int MaxExecTime { get; private set; }
        public int MinGenerationTime { get; private set; }
        public int MaxGenerationTime { get; private set; }

        public Process[] Processes { get; private set; }

        public Simulator(int rrTime, int minExecTime, int maxExecTime, int minGenerationTime, int maxGenerationTime)
        {
            RoundRobinTime = rrTime;
            MinExecTime = minExecTime;
            MaxExecTime = maxExecTime;
            MinGenerationTime = minGenerationTime;
            MaxGenerationTime = maxGenerationTime;

            random = new Random(RANDOM_SEED);
            CreateProcesses();
        }

        private void CreateProcesses()
        {
            Processes = new Process[PROCESS_COUNT];
            int time = 0;
            for (int i = 0; i < PROCESS_COUNT; i++)
            {
                int execTime = random.Next(MinExecTime, MaxExecTime + 1);
                Processes[i] = new Process(time, execTime);
                time += random.Next(MinGenerationTime, MaxGenerationTime + 1);
            }
        }

        private SimulationResult RunFCFS()
        {
            double totalWaitingTime = 0;
            int maxQueueLength = 0;

            var queue = new Queue<Process>();
            int remainingProcesses = PROCESS_COUNT;
            int nextProcessIndex = 0;

            int time = 0;
            while (remainingProcesses > 0)
            {
                //Появление новых процессов
                while (nextProcessIndex < PROCESS_COUNT
                       && Processes[nextProcessIndex].ArrivalTime <= time)
                {
                    Process nextProcess = Processes[nextProcessIndex];
                    queue.Enqueue(nextProcess);
                    nextProcessIndex++;
                }

                //Обработка процесса в очереди
                if (queue.Count > 0)
                {
                    maxQueueLength = Math.Max(maxQueueLength, queue.Count);

                    var p = queue.Dequeue();
                    totalWaitingTime += time - p.ArrivalTime;
                    time += p.RemainingTime;

                    remainingProcesses--;
                }
                else
                {
                    Process nextProcess = Processes[nextProcessIndex];
                    time = nextProcess.ArrivalTime;
                }
            }

            double avgWaitingTime = totalWaitingTime / PROCESS_COUNT;
            return new SimulationResult(avgWaitingTime, maxQueueLength);
        }

        private SimulationResult RunRoundRobin()
        {
            double totalWaitingTime = 0;
            int maxQueueLength = 0;

            var queue = new Queue<Process>();
            int remainingProcesses = PROCESS_COUNT;
            int iterationCount = 0;
            int nextProcessIndex = 0;

            int time = 0;
            while (remainingProcesses > 0)
            {
                //Появление новых процессов
                while (nextProcessIndex < PROCESS_COUNT
                       && Processes[nextProcessIndex].ArrivalTime <= time)
                {
                    Process nextProcess = Processes[nextProcessIndex];
                    queue.Enqueue(nextProcess);
                    nextProcessIndex++;
                }

                //Обработка процесса в очереди
                if (queue.Count > 0)
                {
                    maxQueueLength = Math.Max(maxQueueLength, queue.Count);

                    var p = queue.Dequeue();
                    int deltaT = Math.Min(RoundRobinTime, p.RemainingTime);

                    iterationCount++;
                    totalWaitingTime += time - p.ArrivalTime;
                    time += deltaT;

                    p.Advance(time, deltaT);

                    if (p.IsFinished) remainingProcesses--;
                    else queue.Enqueue(p);
                }
                else
                {
                    Process nextProcess = Processes[nextProcessIndex];
                    time = nextProcess.ArrivalTime;
                }
            }

            Debug.Assert(iterationCount >= PROCESS_COUNT);
            double avgWaitingTime = totalWaitingTime / iterationCount;
            return new SimulationResult(avgWaitingTime, maxQueueLength);
        }

        public SimulationResult[] RunSimulation()
        {
            SimulationResult fcfsResult = RunFCFS();
            SimulationResult rrResult = RunRoundRobin();

            return new[] {fcfsResult, rrResult};
        }
    }
}
