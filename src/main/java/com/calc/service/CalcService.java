package com.calc.service;
import indev.test.job.calc.ICalc;
import indev.test.job.calc.ThreadCalc;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class CalcService {
    private static volatile int taskId = 0;
    private static int getTaskId(){
        return taskId++;
    }
    private  enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
    public class Task{
        private  double[] operand;
        private  int id;
        private  Operation op;
        private  double ans;

        public Task(double[] operand, int id, Operation op) {
            this.operand = operand;
            this.id = id;
            this.op = op;
        }

        public double[] getOperand() {
            return operand;
        }

        public int getId() {
            return id;
        }

        public Operation getOp() {
            return op;
        }

        public double getAns() {
            return ans;
        }

        public void setAns(double ans) {
           this.ans = ans;
        }

       Operation getOperation() {
           return op;
       }
    }

    @PostConstruct
    private void initIt() {
        Thread t = new Thread(() ->{
            start();
        });
        t.start();
    }

    private BlockingQueue<Task> tasks = new LinkedBlockingQueue<>();
    private BlockingQueue<Task> answers = new LinkedBlockingQueue <>();

    public BlockingQueue<Task> getAnswers() {
        return answers;
    }

    private void start(){
        final ICalc tc = ThreadCalc.Instance();
        while(true){
            if(tasks.size()>0) {
                try {
                    Task task = tasks.take();
                    switch (task.getOperation()) {
                        case ADD: {
                            task.setAns(tc.Add(task.getOperand()[0], task.getOperand()[1]));
                            System.out.println("add");
                            break;
                        }
                        case DIVIDE: {
                            task.setAns(tc.Divide(task.getOperand()[0], task.getOperand()[1]));
                            System.out.println("DIVIDE");
                            break;
                        }
                        case MULTIPLY: {
                            task.setAns(tc.Multiply(task.getOperand()[0], task.getOperand()[1]));
                            System.out.println("MULTIPLY");
                            break;
                        }
                        case SUBTRACT: {
                            task.setAns(tc.Add(task.getOperand()[0], ((-1) * task.getOperand()[1])));
                            System.out.println("SUBTRACT");
                            break;
                        }
                    }
                    answers.put(task);
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }

        }

    }

   public int add(double a, double b)  {
       int taskId = getTaskId();
       try {
           tasks.put(new Task(new double[]{a,b}, taskId, Operation.ADD ));
       } catch (InterruptedException e) {
           e.printStackTrace();
           taskId = -taskId;
       }
       return taskId;
    }

    public  int subtract(double a, double b)  {
        int taskId = getTaskId();
        try {
            tasks.put(new Task(new double[]{a,b}, taskId, Operation.SUBTRACT));
        } catch (InterruptedException e) {
            e.printStackTrace();
            taskId = -taskId;
        }
        return taskId;
    }

    public int multiply(double a, double b)  {
        int taskId = getTaskId();
        try {
            tasks.put(new Task(new double[]{a,b}, taskId, Operation.MULTIPLY ));
        } catch (InterruptedException e) {
            e.printStackTrace();
            taskId = -taskId;
        }
        return taskId;
    }

    public  int divide(double a, double b)  {
        int taskId = getTaskId();
        try {
            tasks.put(new Task(new double[]{a,b}, taskId, Operation.DIVIDE ));
        } catch (InterruptedException e) {
            e.printStackTrace();
            taskId = -taskId;
        }
        return taskId;
    }
}
