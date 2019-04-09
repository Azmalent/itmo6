using System;

namespace lab1
{
    class Program
    {
        static void Main(string[] args)
        {
            var simulator = new Simulator(4, 6, 12, 4, 10);
            var result = simulator.RunSimulation();

            Console.WriteLine("Среднее время ожидания для FCFS: " + result[0].AverageWaitTime);
            Console.WriteLine("Макс. длина очереди для FCFS:    " + result[0].MaxQueueLength);
            Console.WriteLine("Среднее время ожидания для RR:   " + result[1].AverageWaitTime);
            Console.WriteLine("Макс. длина очереди для RR:      " + result[1].MaxQueueLength);

            bool fcfsMin = result[0].AverageWaitTime < result[1].AverageWaitTime;
            Console.WriteLine($"\nРезультат: {(fcfsMin ? "FCFS" : "RR")} более эффективен.");

            Console.ReadKey();
        }
    }
}
