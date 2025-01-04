package com.muzaffar.customer;

import com.muzaffar.exception.DuplicateResourceException;
import com.muzaffar.exception.RequestValidationException;
import com.muzaffar.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Created on 06/11/24.
 */

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest= new CustomerService(customerDao);
    }


    @Test
    void getAllCustomers() {
        // Given

        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();

    }

    @Test
    void canGetCustomerById() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.getCustomerById(id);

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void wilThrowWhenGetCustomerReturnsEmptyOptional() {
        // Given
        long id = 10;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [" + id + "] not found");
    }



    @Test
    void addCustomer() {
        // Given
        String email = "alex@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                email,
                19,
                0
        );


        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        // Given
        String email = "alex@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(true);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                email,
                19,
                0
        );


        // When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                        .hasMessage("email already taken");


        // Then
        verify(customerDao, never()).insertCustomer(any());

    }

    @Test
    void deleteCustomerById() {
        // Given
        long id = 10;
        when(customerDao.existsWithId(id)).thenReturn(true);

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerDao).deleteById(id);
    }

    @Test
    void willThrowWhenIdNotExistsWhileDeletingCustomerById() {
        // Given
        long id = 10;
        when(customerDao.existsWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Customer with id [" + id + "] not found");

        // Then
        verify(customerDao, never()).deleteById(id);
    }



    @Test
    void editCustomerById() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alex_new@gmail.com";
        var request = new CustomerEditRequest(
                "Alexandro", newEmail, 20, 0);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.editCustomerById(id, request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        var capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getGender()).isEqualTo(request.gender());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        var request = new CustomerEditRequest(
                "Alexandro", null, null, null);


        // When
        underTest.editCustomerById(id, request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        var capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alex_new@gmail.com";
        var request = new CustomerEditRequest(
                null, newEmail, null, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);


        // When
        underTest.editCustomerById(id, request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        var capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        int age = 45;
        var request = new CustomerEditRequest(
                null, null, age, null);


        // When
        underTest.editCustomerById(id, request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        var capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getGender()).isEqualTo(customer.getGender());
        assertThat(capturedCustomer.getAge()).isEqualTo(age);
    }

    @Test
    void canUpdateOnlyCustomerGender() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        int gender = 1;
        var request = new CustomerEditRequest(
                null, null, null, gender);


        // When
        underTest.editCustomerById(id, request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        var capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(gender);
    }


    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alex_new@gmail.com";
        var request = new CustomerEditRequest(
                null, newEmail, null, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(true);


        // When
        assertThatThrownBy(() -> underTest.editCustomerById(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        long id = 10;
        var customer = new Customer(id, "Alex", "alex@gmail.com", 19, 0);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        var request = new CustomerEditRequest(
                customer.getName(), customer.getEmail(), customer.getAge(), customer.getGender());


        // When
        assertThatThrownBy(() -> underTest.editCustomerById(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
}