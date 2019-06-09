using System;
using System.IO.MemoryMappedFiles;
using System.Security.AccessControl;
using System.Threading;

namespace Scout
{
    class ScoutProcess
    {
        private static Mutex mutex;
        private static EventWaitHandle msgEvent;
        private static EventWaitHandle closeEvent;
        private static MemoryMappedFile file;
        private static MemoryMappedViewAccessor fileAccessor;

        static ScoutProcess()
        {
            Console.SetWindowSize(36, 18);

            msgEvent = EventWaitHandle.OpenExisting("OS2_MessageEvent");
            closeEvent = EventWaitHandle.OpenExisting("OS2_CloseEvent");
            mutex = Mutex.OpenExisting("OS2_Mutex", MutexRights.Synchronize);

            file = MemoryMappedFile.OpenExisting("OS2_Message");
            fileAccessor = file.CreateViewAccessor(0, 2, MemoryMappedFileAccess.Write);
        }

        static void Main(string[] args)
        {
            Console.WriteLine(".: точка");
            Console.WriteLine("-: тире");
            Console.WriteLine("q: выход\n");

            ConsoleKey c;
            do
            {
                c = Console.ReadKey(true).Key;
         
                if (c == ConsoleKey.OemPeriod) SendMessage('.');
                else if (c == ConsoleKey.OemMinus) SendMessage('-');
            } while (c != ConsoleKey.Q);
            
            closeEvent.Set();
        }

        static void SendMessage(char c)
        {
            mutex.WaitOne();

            Console.Write(c);
            fileAccessor.Write(0, c);
            msgEvent.Set();

            mutex.ReleaseMutex();
        }
    }
}
