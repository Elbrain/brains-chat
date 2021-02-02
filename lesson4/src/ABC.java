
public class ABC {
    boolean aTurn = true;
    boolean bTurn = false;
    boolean cTurn = false;
    final Object monitor = new Object();

    public static void main(String[] args) {
        final ABC print = new ABC();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    print.printA();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    print.printB();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    print.printC();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public void printA() {
        synchronized (monitor) {
            try {
                while (!aTurn) {
                    monitor.wait();
                }
                System.out.print("A");
                aTurn = false;
                bTurn = true;
                monitor.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (monitor) {
            try {
                while (!bTurn) {
                    monitor.wait();
                }
                System.out.print("B");
                bTurn = false;
                cTurn = true;
                monitor.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (monitor) {
            try {
                while (!cTurn) {
                    monitor.wait();
                }
                System.out.print("C");
                cTurn = false;
                aTurn = true;
                monitor.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
