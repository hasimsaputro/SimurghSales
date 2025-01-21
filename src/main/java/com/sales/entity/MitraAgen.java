package com.sales.entity;

import jakarta.persistence.OneToMany;

import java.util.List;

public class MitraAgen {

    @OneToMany(mappedBy = "mitraAgenDataLeads")
    private List<DataLeads> dataLeadsList;
}
