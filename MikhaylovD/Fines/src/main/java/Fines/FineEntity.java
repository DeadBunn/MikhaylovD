package Fines;


import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table (name = "fines")
public class FineEntity {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String intruder;
    private String policeOfficer;
    private String carNumber;
    private Boolean paid = false;
    private Boolean subpoena = false;
    private Date deadline;
    private Date paymentDay = null;
    private Date time;
    private Integer sum;
    public FineEntity(String intruder, String policeOfficer, String carNumber, Date deadline, Integer sum) {
        this.intruder = intruder;
        this.policeOfficer = policeOfficer;
        this.carNumber = carNumber;
        this.deadline = deadline;
        this.sum = sum;
        time = new Date();
    }
    public String toString(){
        return "Id: " + id +
                " Нарушитель: " + intruder +
                " Сотрудник ГАИ: " + policeOfficer +
                " Номер машины: " + carNumber +
                ((paid) ? " Оплачен : Дата оплаты: " + dateFormat.format(paymentDay) : " Неоплачен") +
                " Повестка в суд: " + ((subpoena) ? "есть" : "нет") +
                " Срок оплаты: " + dateFormat.format(deadline) +
                " Сумма штрафа: " + sum +
                " Время составления штрафа: " + timeFormat.format(time);
    }
    private FineEntity(){}
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
    public void setSubpoena(Boolean subpoena) {
        this.subpoena = subpoena;
    }

    public Integer getId() {
        return id;
    }
    public void setIntruder(String intruder) {
        this.intruder = intruder;
    }
    public void setPoliceOfficer(String policeOfficer) {
        this.policeOfficer = policeOfficer;
    }
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
