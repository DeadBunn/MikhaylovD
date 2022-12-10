package Fines;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface FineEntityRepo extends JpaRepository<FineEntity,Long> {
    FineEntity findById(Integer id);
    @Transactional
    int deleteById(Integer id);
    @Transactional
    @Modifying
    @Query("update FineEntity f set f.paid = true, paymentDay = ?2 where f.id = ?1")
    int setPaidById(Integer id, Date paymentDay);
    @Transactional
    @Modifying
    @Query("update FineEntity f set f.subpoena = true where f.id = ?1")
    int setSubpoenaById(Integer id);

}
