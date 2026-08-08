package com.horizon.bank.transaction.transfer.service;

import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.transfer.dto.GetAllTransferTransactionsDto;
import com.horizon.bank.transaction.transfer.entity.TransferEntity;
import com.horizon.bank.transaction.transfer.repository.TransferRepository;
import com.horizon.bank.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

  @Mock private TransferRepository transferRepository;
  @Mock private UserRepository userRepository;
  @Mock private AccountRepository accountRepository;

  @Test
  void getAllTransferTransactions_shouldMergeAndSortByCreatedAtDescending() {
    TransferService service =
        new TransferService(new ResponseStructure(), userRepository, accountRepository, transferRepository);

    GetAllTransferTransactionsDto requestDto = new GetAllTransferTransactionsDto();
    requestDto.setAccountNumber("ACC-001");

    TransferEntity olderTransaction = new TransferEntity();
    olderTransaction.setId("txn-1");
    olderTransaction.setCreatedAt(100L);

    TransferEntity newerTransaction = new TransferEntity();
    newerTransaction.setId("txn-2");
    newerTransaction.setCreatedAt(200L);

    when(transferRepository.findByPayeeAccountAccountNumber("ACC-001"))
        .thenReturn(List.of(olderTransaction));
    when(transferRepository.findByPayerAccountAccountNumber("ACC-001"))
        .thenReturn(List.of(newerTransaction));

    ResponseStructure response = service.getAllTransferTransactions(requestDto, new ResponseStructure());

    assertEquals(200, response.getStatusCode());
    assertEquals(false, response.getError());
    assertInstanceOf(List.class, response.getData());

    List<TransferEntity> transactions = (List<TransferEntity>) response.getData();
    assertEquals(2, transactions.size());
    assertEquals("txn-2", transactions.get(0).getId());
    assertEquals("txn-1", transactions.get(1).getId());
  }
}
