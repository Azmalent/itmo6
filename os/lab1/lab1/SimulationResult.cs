namespace lab1
{
    struct SimulationResult
    {
        public readonly double AverageWaitTime;
        public readonly int MaxQueueLength;

        public SimulationResult(double waitTime, int queueLength)
        {
            AverageWaitTime = waitTime;
            MaxQueueLength = queueLength;
        }
    }
}
