package com.calc.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.BlockingQueue;


import com.calc.service.CalcService;



@RestController
@RequestMapping({"/api"})
public class СalculatorController {
    @Autowired
   private CalcService serv;

   private Double getAnswer(int id){
        boolean isEndSearch = false;
        Double ans = null;
        CalcService.Task currTask = null;
        BlockingQueue<CalcService.Task> answers = serv.getAnswers();
        while(!isEndSearch){
          for(CalcService.Task task: answers){
              if(task.getId() == id) {
                  ans = task.getAns();
                  currTask = task;
                  isEndSearch = true;
              }
          }
      }
      answers.remove(currTask);
      return ans;
    }

    @GetMapping(value = {"/add"}, params = { "first", "second" }    )
    double add(@RequestParam("first") double first, @RequestParam("second") double second){
        int id = serv.add(first, second);
        return id >= 0 ? getAnswer(id) : 0;
    }

    @GetMapping(value = {"/subtract"}, params = { "first", "second" }    )
    double subtract(@RequestParam("first") int first, @RequestParam("second") int second){
        int id = serv.subtract(first, second);
        return id >= 0 ? getAnswer(id) : 0;
    }


    @GetMapping(value = {"/multiply"}, params = { "first", "second" }    )
    double multiply(@RequestParam("first") int first, @RequestParam("second") int second){
        int id = serv.multiply(first, second);
        return id >= 0 ? getAnswer(id) : 0;
    }


    @GetMapping(value = {"/divide"}, params = { "first", "second" }    )
    double divide(@RequestParam("first") int first, @RequestParam("second") int second) {
        int id = serv.divide(first, second);
        return id >= 0 ? getAnswer(id) : 0;
    }

}
