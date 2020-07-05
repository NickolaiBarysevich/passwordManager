package by.barysevich.password.manager.provider;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ParametersStoreProvider {

    private final AWSSimpleSystemsManagement ssm;

    public ParametersStoreProvider(AWSSimpleSystemsManagement ssm) {
        this.ssm = ssm;
    }

    public String getParameter(String parameter) {
        final GetParameterRequest getParameterRequest = new GetParameterRequest().
                withName(parameter)
                .withWithDecryption(Boolean.TRUE);
        return ssm.getParameter(getParameterRequest).getParameter().getValue();
    }
}
