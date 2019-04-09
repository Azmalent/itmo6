namespace lab1
{
    struct Process
    {
        public int ArrivalTime { get; private set; }
        public int RemainingTime { get; private set; }
        public bool IsFinished => RemainingTime <= 0;

        public Process(int arrivalTime, int remainingTime)
        {
            ArrivalTime = arrivalTime;
            RemainingTime = remainingTime;
        }

        public void Advance(int currentTime, int deltaT)
        {
            ArrivalTime = currentTime;
            RemainingTime -= deltaT;
        }

        public string ToString()
        {
            return $"({ArrivalTime}, {RemainingTime})";
        }
    }
}
