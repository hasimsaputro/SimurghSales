package com.sales.service.bank;

import com.sales.dto.bank.BankDetailDTO;
import com.sales.dto.bank.BankFormDTO;
import com.sales.dto.bank.BankIndexDTO;
import com.sales.dto.bank.BankIndexOptionDTO;
import com.sales.dto.produk.ProdukDetailDTO;
import com.sales.dto.produk.ProdukFormDTO;
import com.sales.dto.produk.ProdukIndexDTO;
import com.sales.dto.produk.ProdukIndexOptionDTO;

import java.util.List;

public interface BankService {
    int getTotalPages(String filter, String search);
    List<BankIndexDTO> getAll(int page, String filter, String search);
    BankFormDTO getBankById(Integer id);
    BankDetailDTO getDetailBankById(Integer id);
    void save(BankFormDTO bankFormDTO);
    void delete(int id);
    List<BankIndexOptionDTO> getFilterAsItem();
    List<BankIndexOptionDTO> getSearchItems(String filter);

}
