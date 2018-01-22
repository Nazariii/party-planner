package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.MockitoExtension;
import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.repository.ServiceRepository;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.ServiceService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceImplTest {

    public static final String TEST_EMAIL = "testEmail";
    public static final String USERNAME = "username";
    public static final User TEST_USER = new User(TEST_EMAIL, "pass", USERNAME, "phone");
    public static final long SERVICE_ID = 1L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceService serviceService = new ServiceServiceImpl();


    @Test
    public void findServiceById() throws Exception {
        String testServiceName = "testService";
        Service testService = new Service(testServiceName, TEST_USER);
        when(serviceRepository.findOneById(eq(SERVICE_ID))).thenReturn(Optional.of(testService));
        Service foundService = serviceService.findServiceById(SERVICE_ID);
        assertThat(foundService.getName(), is(testServiceName));
        assertThat(foundService.getUser().getName(), is(USERNAME));
    }

    @Test
    public void add() throws Exception {
        when(serviceRepository.findOneById(eq(SERVICE_ID))).thenReturn(Optional.empty());
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> serviceService.findServiceById(SERVICE_ID));
        assertThat(exception.getMessage(), is("Resource 'Service' with 'id' = '1' was not found"));
    }

    @Test
    @Disabled
    public void add1() throws Exception {

    }

    @Test
    @Disabled
    public void findAll() throws Exception {

    }

    @Test
    @Disabled
    public void delete() throws Exception {

    }
}