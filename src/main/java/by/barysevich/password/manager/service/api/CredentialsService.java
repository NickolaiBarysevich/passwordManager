package by.barysevich.password.manager.service.api;

import by.barysevich.password.manager.rest.dto.CreateCredentialsRequest;
import by.barysevich.password.manager.rest.dto.CredentialsResponse;
import by.barysevich.password.manager.rest.dto.ShortCredentialsResponse;
import java.util.List;

public interface CredentialsService {

    List<ShortCredentialsResponse> getUserShortCredentials();

    void createCredentials(CreateCredentialsRequest request);

    int updateCredentials(String id, CreateCredentialsRequest request);

    int deleteCredentials(String id);

    CredentialsResponse getCredentialsById(String id);
}
