package FinesDataBase.MikhaylovD;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1")
public class Controller {
    ArrayList <String> a = new ArrayList<>();
    @GetMapping
    public String hello (@RequestParam(value = "name", defaultValue = "User") String name){return "Hello  " + name;}
    DataBase db = new DataBase();
    @GetMapping("/fines")
    public String list(){return db.getAll();}
    @GetMapping("/fines/{fineId}")
    public String getId(@PathVariable String fineId){
        try {
            return db.getId(fineId).toString();
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
    @PostMapping("/fines")
    public String add(@RequestParam  String id, String intruderFio, String carNumber, String policeFio, Integer sum, String deadline){
       try {
           db.add(id, intruderFio, carNumber, policeFio, sum, deadline);
       }catch (Exception e) {
           return e.getMessage();
       }
        return db.getId(id).toString();
    }
    @PutMapping("/fines")
    public String update(@RequestParam  String id, String intruderFio, String carNumber, String policeFio, Integer sum, String deadline){
        try {
            db.change(id , intruderFio, carNumber, policeFio, sum, deadline);

        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
        return db.getId(id).toString();
    }
    @DeleteMapping("/fines/{fineId}")
    public String delete(@PathVariable String fineId){
        try {
            db.delete(fineId);
            return "Штраф удален";
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
    @PatchMapping("/fines/{fineId}/pay")
    public String pay(@PathVariable String fineId){
        try {
            db.pay(fineId);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
        return "Штраф успешно оплачен";
    }
    @PatchMapping("/fines/{fineId}/court")
    public String send(@PathVariable String fineId){
        try {
            db.send(fineId);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
        return "Повестка в суд отправлена";
    }


}

