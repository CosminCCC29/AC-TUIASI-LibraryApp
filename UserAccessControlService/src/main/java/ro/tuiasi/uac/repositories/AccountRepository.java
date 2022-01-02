package ro.tuiasi.uac.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuiasi.uac.models.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
