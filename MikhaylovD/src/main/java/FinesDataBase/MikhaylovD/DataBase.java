package FinesDataBase.MikhaylovD;

import org.springframework.web.client.HttpClientErrorException;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Fine{
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    protected String intruderFio,carNumber, policeFio, id;
    protected int sum;
    protected boolean subpoena = false, paid = false;
    protected Date deadline, paymentDay, time;
    protected Fine (String id, String intruderFio, String carNumber, String policeFio, Integer sum, Date deadline, Date time){
        this.intruderFio = intruderFio;
        this.carNumber = carNumber;
        this.deadline = deadline;
        this.time = time;
        this.policeFio = policeFio;
        this.sum = sum;
        this.id = id;
    }
    protected void pay(){paid=true;}
    protected void send(){subpoena=true;}
    public String toString (){
        return "ID: " + id + "; Нарушитель: " + intruderFio + "; Сотрудник ГАИ: " + policeFio + "; Номер машины: " + carNumber + "; Сумма штрафа: " + sum + "; Срок оплаты: " + dateFormat.format(deadline) + "; Время составления протокола: " + timeFormat.format(time) + (((subpoena) ? "; Есть повестка в суд" : "; Нет повестки в суд") + ((paid)? "; Штраф оплачен" : "; Штраф не оплачен"));
    }
    protected String getFineId(){
        return id;
    }
}
public class DataBase {
    ArrayList<Fine> dataBase = new ArrayList<>();
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public void add (String id, String intruderFio, String carNumber, String policeFio,Integer sum,  String deadline){
        String finalId = id;
        if (dataBase.stream().anyMatch(x->x.getFineId().equals(finalId)) == true) {
            throw new IllegalArgumentException("Данный id уже существует");
        };
        try {
            dataBase.add(new Fine(id, intruderFio,carNumber,policeFio,sum, dateFormat.parse(deadline), new Date()));
            id += 1;
        }catch (ParseException e) {
            throw new IllegalArgumentException("Неправильный формат даты");
        }
    }
    public Fine getId (String id) {
        for (Fine elem : dataBase){
            if (elem.getFineId().equals(id)) return elem;
        }
        throw new IllegalArgumentException("Данный id не существует");
    }
    ArrayList<Integer> a = new ArrayList<>();
    public String getAll(){
        String t = "";
        for (Fine elem : dataBase){
            t += elem.toString() + "\n";
        }
        return t;
    }
    public void change(String id, String intruderFio, String carNumber, String policeFio,Integer sum,  String deadline){
        for (Fine elem : dataBase){
            if (elem.getFineId().equals(id)){
                try {
                    elem.intruderFio = intruderFio;
                    elem.carNumber = carNumber;
                    elem.policeFio = policeFio;
                    elem.sum = sum;
                    elem.deadline = dateFormat.parse(deadline);
                    } catch (ParseException e){
                    throw new IllegalArgumentException("Неправильный формат даты");}
                return;
            }
        }
        throw new IllegalArgumentException("Данный id не существует");
    }
    public void delete(String id){
        for (int i = 0; i < dataBase.size();i++){
            if (dataBase.get(i).getFineId().equals(id)){
                dataBase.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Данный id не существует");
    }
    public void pay(String id){
        for (int i = 0; i < dataBase.size();i++){
            if (dataBase.get(i).getFineId().equals(id)){
                dataBase.get(i).pay();
                return;
            }
        }
        throw new IllegalArgumentException("Данный id не существует");
    }
    public void send(String id){
        for (int i = 0; i < dataBase.size();i++){
            if (dataBase.get(i).getFineId().equals(id)){
                dataBase.get(i).send();
                return;
            }
        }
        throw new IllegalArgumentException("Данный id не существует");
    }
}

