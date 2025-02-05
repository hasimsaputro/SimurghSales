package com.sales.service.bank;

import com.sales.dto.bank.BankDetailDTO;
import com.sales.dto.bank.BankFormDTO;
import com.sales.dto.bank.BankIndexDTO;
import com.sales.dto.bank.BankIndexOptionDTO;
import com.sales.entity.Bank;
import com.sales.repository.BankRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class BankServiceImplementation implements BankService {
    private final BankRepository repository;
    private final int rowInPage = 4;

    public BankServiceImplementation(BankRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if(filter.isEmpty()){
            page = repository.getTotalPages();
        } else {
            switch (filter){
                case "id":
                    page = repository.getTotalPagesById(search);
                    break;
                case "namaBank":
                    page = repository.getTotalpagesByName(search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    page = repository.getTotalpagesByStatus(Boolean.parseBoolean(search));
                    break;
            }
        }
        return (int) Math.ceil(page / rowInPage);
    }

    @Override
    public List<BankIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Bank> bankList = new LinkedList<>();
        if (filter.isEmpty()) {
            bankList = repository.getAllBank(pageable);
        } else {
            switch (filter) {
                case "id":
                    bankList = repository.getBankById(pageable, search);
                    break;
                case "namaBank":
                    bankList = repository.getBankByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    bankList = repository.getBankByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<BankIndexDTO> bankIndexDTOS = new LinkedList<>();
        for (Bank bank : bankList) {
            BankIndexDTO bankIndexDTO = new BankIndexDTO();
            bankIndexDTO.setKodeBank(bank.getId());
            bankIndexDTO.setNamaBank(bank.getNamaBank());
            String statusBank = !bank.getStatus() ? "Tidak Aktif" : "Aktif";
            bankIndexDTO.setStatus(statusBank);

            bankIndexDTOS.add(bankIndexDTO);
        }
        return bankIndexDTOS;
    }

    @Override
    public BankFormDTO getBankById(Integer id) {
        BankFormDTO bankFormDTO = new BankFormDTO();
        if (id != null) {
            try {
                Bank bank = repository.findById(id).orElseThrow();
                bankFormDTO.setId(bank.getId());
                bankFormDTO.setNamaBank(bank.getNamaBank());
                bankFormDTO.setStatus(bank.getStatus());
                return bankFormDTO;
            } catch (Exception ignored) { }
        }
        return bankFormDTO;
    }

    @Override
    public BankDetailDTO getDetailBankById(Integer id) {
        BankDetailDTO bankDetailDTO = new BankDetailDTO();
        if (id != null) {
            try {
                Bank bank = repository.findById(id).orElseThrow();
                bankDetailDTO.setKodeBank(String.valueOf(bank.getId()));
                bankDetailDTO.setNamaBank(bank.getNamaBank());
                String statusBank = !bank.getStatus() ? "Tidak Aktif" : "Aktif";
                bankDetailDTO.setStatus(statusBank);
            } catch (Exception ignored) { }
        }
        return bankDetailDTO;
    }

    @Override
    public void save(BankFormDTO bankFormDTO) {
        Bank bank = new Bank();
        bank.setId(bankFormDTO.getId());
        bank.setNamaBank(bankFormDTO.getNamaBank());
        bank.setStatus(bankFormDTO.getStatus());
        repository.save(bank);
    }

    @Override
    public void delete(int id) {
        try {
            Bank bank = repository.findById(id).orElseThrow();
            bank.setDeleteDate(LocalDate.now());
            repository.save(bank);
        } catch (Exception ignored) { }
    }

    @Override
    public List<BankIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new BankIndexOptionDTO("Kode Bank", "id"),
                new BankIndexOptionDTO("Nama Bank", "namaBank"),
                new BankIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<BankIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()) {
            switch (filter) {
                case "id":
                    searchItems = repository.getBankItemsById();
                    break;
                case "namaBank":
                    searchItems = repository.getBankItemsByName();
                    break;
                case "status":
                    searchItems = repository.getBankItemsByStatus();
                    break;
            }
        }
        List<BankIndexOptionDTO> bankIndexOptionDTOS = new LinkedList<>();
        for (String searchItem : searchItems) {
            BankIndexOptionDTO bankIndexOptionDTO = new BankIndexOptionDTO(searchItem, searchItem);
            bankIndexOptionDTOS.add(bankIndexOptionDTO);
        }
        return bankIndexOptionDTOS;
    }
}
