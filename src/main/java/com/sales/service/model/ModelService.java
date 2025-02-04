package com.sales.service.model;

import com.sales.dto.OptionDTO;
import com.sales.dto.model.ModelDetailDTO;
import com.sales.dto.model.ModelFormDTO;
import com.sales.dto.model.ModelIndexDTO;
import com.sales.dto.tipe.TipeJenisDTO;

import java.util.List;

public interface ModelService {
    List<ModelIndexDTO> getAll(String filter, String search, int page);

    int getTotal(String filter, String search);

    List<OptionDTO> getfilterAsItem();

    ModelFormDTO getModelById(String modelId);

    void saveModel(ModelFormDTO dto);

    ModelDetailDTO getModelByIdDetail(String modelId);

    void deleteModel(String modelId);

    List<OptionDTO> getSearchItems(String filter);

    List<OptionDTO> getKategoriItems();

    List<OptionDTO> getMerkItems();

    List<OptionDTO> getTipeItems();

    TipeJenisDTO getTipeJenis(String idTipe);
}
