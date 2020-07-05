package by.barysevich.password.manager.repository.api;

//todo rework with func style
public interface Specification {

    String sql();

    Object[] parameters();
}
