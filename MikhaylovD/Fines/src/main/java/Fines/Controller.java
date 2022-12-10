package Fines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v2")
public class Controller {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    @Autowired
    private FineEntityRepo fineRepo;
    @GetMapping("/fines")
    public String getALl(@RequestParam int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id"));
        Page<FineEntity> pageFine = fineRepo.findAll(pageable);
        try {
            return pageFine.stream().toList().get(0).toString();
        }catch (NullPointerException e){
            return "Страницы не существует";
        }
    }
    @GetMapping("fines/sort")
    public String getAllSorted(@RequestParam int page){
        Pageable pageable = PageRequest.of(page-1,100,Sort.by("intruder").ascending());
        Page<FineEntity> pageFine = fineRepo.findAll(pageable);
        String result = "";
        for (FineEntity f : pageFine.stream().toList()){
            result += f.toString() + "\n";
        }
        return result;
    }
    @PostMapping("/fines")
    public String add(@RequestParam  String intruderFio, String policeFio, String carNumber, Integer sum, String deadline) {
        Date deadlineDay;
        try {
            deadlineDay = dateFormat.parse(deadline);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        FineEntity fine = new FineEntity(intruderFio, policeFio, carNumber, deadlineDay, sum);
        fineRepo.save(fine);
        return "Запись успешно добавлена";
    }
    @GetMapping("fines/{id}")
    public String getId(@PathVariable Integer id){
        return fineRepo.findById(id).toString();
    }
    @DeleteMapping("fines/{id}")
    public String deleteById(@PathVariable Integer id){
        if (fineRepo.deleteById(id) == 1) return "Запись успешно удалена";
        else return "Введенный id не найден";
    }
    @PatchMapping("fines/{id}/pay")
    public String pay(@PathVariable Integer id){
        if (fineRepo.setPaidById(id, new Date()) == 1) return "Штраф оплачен";
        else return "Введенный id не найден";
    }
    @PatchMapping("fines/{id}/court")
    public String sendToCourt(@PathVariable Integer id){
        if (fineRepo.setSubpoenaById(id) == 1) return "Повестка в суд отправлена";
        else return "Введенный id не найден";
    }
    @PutMapping("fines")
    public String add(@RequestParam  Integer id, String intruderFio, String policeFio, String carNumber, Integer sum, String deadline) {
        Date deadlineDay;
        try {
            deadlineDay = dateFormat.parse(deadline);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        FineEntity fine = fineRepo.findById(id);
        if (fine == null) return "Введенный id не найден";
        fine.setIntruder(intruderFio);
        fine.setPoliceOfficer(policeFio);
        fine.setCarNumber(carNumber);
        fine.setSum(sum);
        fine.setDeadline(deadlineDay);
        fineRepo.save(fine);
        return "Запись успешно обновлена";
    }
}

