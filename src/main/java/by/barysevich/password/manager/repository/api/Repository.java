package by.barysevich.password.manager.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository<E, I> {

    I insert(E entity);

    int update(I id, E entity);

    int delete(I id);

    List<E> query(Specification specification);

    Optional<E> queryForOne(Specification specification);
}
