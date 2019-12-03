/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.thread;

/**
 *
 * @author HP
 */
public class BaseThread extends Thread {

    private static BaseThread instance;
    private final static Object LOCK = new Object();
    private static boolean suspended = false;

    protected BaseThread() {

    }

    public static BaseThread getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BaseThread();
            }
        }
        return instance;
    }

    public static boolean isSuspended() {
        return suspended;
    }

    public static void setSuspended(boolean suspended) {
        BaseThread.suspended = suspended;
    }

    public void suspendThread() {
        setSuspended(true);
    }

    public synchronized void resumeThread() {
        setSuspended(false);
        notifyAll();
    }

    

}
