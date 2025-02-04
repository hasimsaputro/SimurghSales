package com.sales.dto.identitas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IdentitasIndexDTO {
    private Integer kodeIdentitas;
    private String namaIdentitas;
    private String status;
}
