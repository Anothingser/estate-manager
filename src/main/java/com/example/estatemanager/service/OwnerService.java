package com.example.estatemanager.service;

import com.example.estatemanager.domain.Owner;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface OwnerService {
    Page<Owner> SearchList(Map searchMap);

    Boolean DeleteOwner(List<Integer> ids);

    Owner FindById(Integer id);

    boolean AddOwner(Owner owner);

    boolean UpdateOwner(Owner owner);
}
