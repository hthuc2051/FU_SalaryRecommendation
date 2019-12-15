/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.thread;

import javax.servlet.ServletContext;

/**
 *
 * @author HP
 */
public class CareerBuilderThread extends BaseThread implements Runnable {

    private ServletContext context;

    public CareerBuilderThread() {
    }

    public CareerBuilderThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {

           
        } catch (Exception ex) {
        }
    }
}
