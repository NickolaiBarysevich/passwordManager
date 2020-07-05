package by.barysevich.password.manager.service.impl;

import by.barysevich.password.manager.exception.ServiceException;
import by.barysevich.password.manager.model.Credentials;
import by.barysevich.password.manager.repository.api.Repository;
import by.barysevich.password.manager.repository.api.SqlConstants;
import by.barysevich.password.manager.repository.specification.FindCredentialsByClientIdSpecification;
import by.barysevich.password.manager.repository.specification.FindCredentialsByIdAndClientIdSpecification;
import by.barysevich.password.manager.rest.dto.CreateCredentialsRequest;
import by.barysevich.password.manager.rest.dto.CredentialsResponse;
import by.barysevich.password.manager.rest.dto.ShortCredentialsResponse;
import by.barysevich.password.manager.security.AesEncoder;
import by.barysevich.password.manager.service.api.CredentialsService;
import by.barysevich.password.manager.service.validator.StringValidator;
import by.barysevich.password.manager.service.validator.Validator;
import by.barysevich.password.manager.util.SecurityUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private Repository<Credentials, String> repository;
    private AesEncoder aesEncoder;

    public CredentialsServiceImpl(@Qualifier("credentialsRepository") Repository<Credentials, String> repository,
                                  AesEncoder aesEncoder) {
        this.repository = repository;
        this.aesEncoder = aesEncoder;
    }

    @Override
    public List<ShortCredentialsResponse> getUserShortCredentials() {
        var clientId = SecurityUtil.getCurrentUserId();
        return toShortCredentialsList(repository.query(new FindCredentialsByClientIdSpecification(clientId)));
    }

    private List<ShortCredentialsResponse> toShortCredentialsList(List<Credentials> credentials) {
        return credentials.stream()
                .map(creds -> ShortCredentialsResponse.builder()
                        .id(creds.getId())
                        .title(creds.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void createCredentials(CreateCredentialsRequest request) {
        credentialsRequestValidator().validate(request);
        repository.insert(toCredentials(request));
    }

    private Credentials toCredentials(CreateCredentialsRequest request) {
        return Credentials.builder()
                .title(request.getTitle())
                .username(Objects.nonNull(request.getUsername()) ? aesEncoder.encrypt(request.getUsername()) : null)
                .password(Objects.nonNull(request.getPassword()) ? aesEncoder.encrypt(request.getPassword()) : null)
                .description(Objects.nonNull(request.getDescription()) ? aesEncoder.encrypt(request.getDescription())
                        : null)
                .clientId(SecurityUtil.getCurrentUserId())
                .build();
    }

    private Validator<CreateCredentialsRequest> credentialsRequestValidator() {
        return credentialsRequestValidator(false);
    }

    private Validator<CreateCredentialsRequest> credentialsRequestValidator(boolean update) {
        return request -> {
            var titleValidator = StringValidator.getInstance(request.getTitle());
            if (!update) {
                titleValidator.notBlank();
            }
            titleValidator
                    .min(3)
                    .max(32)
                    .parameterName(SqlConstants.TITLE)
                    .exceptionMessage("must be not blank and between 3 and 32 digits")
                    .validate();

            StringValidator.getInstance(request.getUsername())
                    .min(3)
                    .max(64)
                    .parameterName(SqlConstants.USERNAME)
                    .exceptionMessage("must be not blank and between 3 and 64 digits")
                    .validate();

            StringValidator.getInstance(request.getPassword())
                    .min(3)
                    .max(256)
                    .parameterName(SqlConstants.PASSWORD)
                    .exceptionMessage("must be not blank and between 3 and 256 digits")
                    .validate();

            StringValidator.getInstance(request.getDescription())
                    .min(5)
                    .max(256)
                    .parameterName(SqlConstants.DESCRIPTION)
                    .exceptionMessage("must be not blank and between 5 and 256 digits")
                    .validate();
        };
    }

    @Override
    @Transactional
    public int updateCredentials(String id, CreateCredentialsRequest request) {
        credentialsRequestValidator(true).validate(request);
        return repository.update(id, toCredentials(request));
    }

    @Override
    public int deleteCredentials(String id) {
        return repository.delete(id);
    }

    @Override
    public CredentialsResponse getCredentialsById(String id) {
        var specification = new FindCredentialsByIdAndClientIdSpecification(id, SecurityUtil.getCurrentUserId());
        return toResponse(repository.queryForOne(specification).orElseThrow(ServiceException::notFound));
    }

    private CredentialsResponse toResponse(Credentials credentials) {
        return CredentialsResponse.builder()
                .id(credentials.getId())
                .title(credentials.getTitle())
                .encodedPassword(Objects.nonNull(credentials.getPassword())
                        ? aesEncoder.decrypt(credentials.getPassword())
                        : null)
                .description(Objects.nonNull(credentials.getDescription())
                        ? aesEncoder.decrypt(credentials.getDescription())
                        : null)
                .username(Objects.nonNull(credentials.getUsername())
                        ? aesEncoder.decrypt(credentials.getUsername())
                        : null)
                .passwordLastUpdateDate(credentials.getPasswordLastUpdateDate())
                .build();
    }

}
