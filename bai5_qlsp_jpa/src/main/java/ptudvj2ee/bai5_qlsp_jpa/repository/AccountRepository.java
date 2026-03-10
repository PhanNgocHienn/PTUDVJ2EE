package ptudvj2ee.bai5_qlsp_jpa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptudvj2ee.bai5_qlsp_jpa.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.login_name = :login_name")
    Optional<Account> findByLoginName(@Param("login_name") String login_name);
}