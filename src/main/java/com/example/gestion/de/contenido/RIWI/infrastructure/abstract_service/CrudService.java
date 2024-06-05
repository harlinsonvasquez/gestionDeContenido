package com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service;

import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import org.springframework.data.domain.Page;

public interface CrudService<RQ,RS,ID> {
    public RS create(RQ request);
    public RS get(ID id);
    public RS update(RQ request, ID id);
    public RS patch(RQ request, ID id);
    public Page<RS> getAll(int page, int size, SortType sort);
}
